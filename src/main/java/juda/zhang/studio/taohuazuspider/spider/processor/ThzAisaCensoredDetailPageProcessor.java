package juda.zhang.studio.taohuazuspider.spider.processor;

import juda.zhang.studio.taohuazuspider.core.model.FilmDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductImgDO;
import juda.zhang.studio.taohuazuspider.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 解析桃花族论坛亚洲有碼原創板块的帖子，下载其中的种子文件以及图片。
 * Created by Juda.Zhang on 2017/6/5.
 */
@Service("thzAisaCensoredDetailPageProcessor")
public class ThzAisaCensoredDetailPageProcessor implements PageProcessor {
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzAisaCensoredDetailPageProcessor.class);
    @Value("${site.domain}")
    private String DOMAIN;
    /*
    列表页面
    http://taohuabbs.cc/forum-220-1.html
    http://taohuabbs.cc/forum-220-2.html
     */
    @Value("http://${site.domain}/forum-220-\\d+\\.html")
    private String URL_LIST;
    /*
    详情页面
    http://taohuabbs.cc/thread-1064269-1-1.html
    http://taohuabbs.cc/thread-1064271-1-5.html
     */
    @Value("http://${site.domain}/thread-\\d+-\\d+-\\d+\\.html")
    private String URL_POST;
    @Value("http://${site.domain}/forum.php?mod=attachment&aid=")
    private String TORRENT_URL_PREFIX;
    @Value("${site.timeout}")
    private int TIME_OUT;
    @Value("${site.sleep.time}")
    private int SLEEP_TIME;
    private Site site = Site
            .me()
            .setDomain(DOMAIN)
            .setSleepTime(SLEEP_TIME)
            .setTimeOut(TIME_OUT)
            .setUserAgent(USER_AGENT);

    public void process(Page page) {

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().$("tbody[id^=normalthread_]")
                    .xpath("//tbody/tr/td[@class='icn']").links().regex(URL_POST).all());
            //获取所有符合列表格式的列表页
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else if (page.getUrl().regex(URL_POST).match()) {
            //获取编号
            String code;
            {
                String fullTitle = StringUtils.trimAndUpper(page.getHtml()
                        .xpath("//span[@id='thread_subject']/text()").toString());
                String regex = "(?<=\\[)(\\S+)(?=\\])";
                Matcher m = Pattern.compile(regex).matcher(fullTitle);
                code = StringUtils.trimAndUpper(m.find() ? m.group() : "");
            }

            //获取内容
            String content = page.getHtml()
                    .xpath("//div[@id='ct']//div[@class='t_fsz']//td[@class='t_f']/text()").toString();
            System.out.println(content);
            String[] contents = content.split("\r\n");
            //获取标题
            String title = contents[0].split("：")[1];
            //获取文件大小
            String fileSize = contents[1].split("：")[1];
            Long filmSize = new Long(fileSize.substring(0, fileSize.length() - 2));
            //文件格式
            String fileFormat = contents[2].split("：")[1];
            //发行日期
            String issueDate = contents[4].split("：")[1];
            //片长
            //演员
            //監督
            //シリーズ 系列
            //メーカー 生厂商
            //レーベル 发行商
            //ジャンル 标签

            //解析封面
            String coverImgUrl = page.getHtml().xpath("//ignore_js_op/img/@file").toString();

            //解析预览
            List<String> previewUrls = page.getHtml()
                    .xpath("//div[@class='pcb']//td[@class='t_f']/img/@file").all();
            if (previewUrls != null || previewUrls.size() != 0) {
                previewUrls = previewUrls.stream().filter(n -> !StringUtils.isBlank(n)).collect(Collectors.toList());
            }

            //解析种子地址
            String torrentUrl;
            String[] torrentLink = page.getHtml()
                    .xpath("//div[@class='pattl']//a/@href").toString()
                    .split("aid=");
            if (torrentLink == null || torrentLink.length < 2) {
                LOGGER.info("无法正确解析种子的url={}", page.getUrl());
                return;
            } else {
                torrentUrl = TORRENT_URL_PREFIX + torrentLink[1];
            }

            ProductDO productDO = new ProductDO();
            productDO.setCode(code);
            productDO.setTitle(title);

            List<ProductImgDO> productImgDOList = new ArrayList<>();
            {
                ProductImgDO productImgDO = new ProductImgDO();
                productImgDO.setType(0);
                productImgDO.setUrl(coverImgUrl);
                productImgDOList.add(productImgDO);
            }
            {
                if (previewUrls != null && previewUrls.size() != 0) {
                    productImgDOList.addAll(previewUrls.stream().map(n -> {
                        ProductImgDO productImgDO = new ProductImgDO();
                        productImgDO.setType(0);
                        productImgDO.setUrl(n);
                        return productImgDO;
                    }).collect(Collectors.toList()));
                }
            }

            FilmDO filmDO = new FilmDO();
            filmDO.setFileUrl(torrentUrl);
            page.putField("productDO", productDO);
            page.putField("filmDO", filmDO);
            page.putField("productImgDOList", productImgDOList);

        } else {
            LOGGER.info("不支持的url={}", page.getUrl());
        }
    }

    public Site getSite() {
        return site;
    }
}

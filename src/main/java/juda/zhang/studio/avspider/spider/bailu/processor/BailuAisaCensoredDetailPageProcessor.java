package juda.zhang.studio.avspider.spider.bailu.processor;

import juda.zhang.studio.avspider.core.model.FilmDO;
import juda.zhang.studio.avspider.core.model.ProductDO;
import juda.zhang.studio.avspider.core.model.ProductImgDO;
import juda.zhang.studio.avspider.core.model.TagDO;
import juda.zhang.studio.avspider.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 解析百撸论坛亚洲有碼原創板块的帖子，下载其中的种子文件以及图片。
 * Created by Juda.Zhang on 2017/6/5.
 */
@Service("bailuAisaCensoredDetailPageProcessor")
public class BailuAisaCensoredDetailPageProcessor implements PageProcessor {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    private final static Logger LOGGER = LoggerFactory.getLogger(BailuAisaCensoredDetailPageProcessor.class);
    @Value("${bailu.site.domain}")
    private String DOMAIN;
    /*
    列表页面
    http://www.671hd.com/forum-2-1.html
    http://www.671hd.com/forum-2-2.html
     */
    @Value("http://${bailu.site.domain}/forum-2-\\d+\\.html")
    private String URL_LIST;
    /*
    详情页面
    http://www.671hd.com/thread-52730-1-1.html
    http://www.671hd.com/thread-103-1-25.html
     */
    @Value("http://${bailu.site.domain}/thread-\\d+-\\d+-\\d+\\.html")
    private String URL_POST;
    @Value("http://${bailu.site.domain}/forum.php?mod=attachment&aid=")
    private String TORRENT_URL_PREFIX;
    @Value("${bailu.site.timeout}")
    private int TIME_OUT;
    @Value("${bailu.site.sleep.time}")
    private int SLEEP_TIME;
    @Value("${file.retry.times}")
    private int retryTimes;

    private Site site = Site
            .me()
            .setDomain(DOMAIN)
            .setSleepTime(SLEEP_TIME)
            .setTimeOut(TIME_OUT)
            .setUserAgent(USER_AGENT)
            .setCycleRetryTimes(retryTimes)
            .setRetryTimes(retryTimes);

    public void process(Page page) {

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            Selectable normalThread = page.getHtml().$("tbody[id^=normalthread_]");
            Selectable classIcn = normalThread.xpath("//tbody/tr/td/div/h2/span");
            Selectable links = classIcn.links();
            page.addTargetRequests(page.getHtml().$("tbody[id^=normalthread_]")
                    .xpath("//tbody/tr/td/div/h2/span").links().regex(URL_POST).all());
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
            Selectable content = page.getHtml()
                    .xpath("//div[@class='t_fsz']//td[@class='t_f']/text()");
            //获取标题
            String title = StringUtils.trim(content.regex("片名：(.*?)容量").toString());

            //获取文件大小
            long fileSize = 0l;
            //文件格式
            String fileFormat = "";
            //发行日期
            String issueDate = "";
            //片长
            Integer duration = 0;
            //演员
            String actressName = "";
            //監督
            String director = "";
            //シリーズ 系列
            String series = "";
            //メーカー 生厂商
            String manufactor = "";
            //レーベル 发行商
            String producer = "";
            //ジャンル 标签
            String genre = "";
            try {
                //文件格式
                fileFormat = StringUtils.trim(content.regex("格式：(.*?)配信開始日").toString());
                //获取文件大小
                fileSize = (long) (new Double(StringUtils.trim(content.regex("容量：(.*?)GB").toString())) * 1000);
                //发行日期
                issueDate = StringUtils.trim(content.regex("商品発売日：[  ]*(.*?)収録時間").toString());
                //片长
                duration = new Integer(StringUtils.trim(content.regex("収録時間：[  ]*(.*?)分").toString()));
                //演员
                actressName = StringUtils.trim(content.regex(" 出演者：[  ]*(.*?)レーベル").toString());
                //レーベル 发行商
                producer = StringUtils.trim(content.regex(" レーベル：[  ]*(.*?)ジャンル").toString());
                //ジャンル 标签
                genre = StringUtils.trim(content.regex(" ジャンル：[  ]*(.*?)品番").toString());
            } catch (Exception e) {
                LOGGER.warn("获取信息出错,使用默认值！", e);
            }

            //解析封面
            String coverImgUrl = page.getHtml().xpath("//div[@class='t_fsz']//td[@class='t_f']/").xpath("//ignore_js_op/img/@file").toString();

            //解析预览
            List<String> previewUrls = page.getHtml().xpath("//div[@class='t_fsz']//td[@class='t_f']/")
                    .xpath("//img/@src").all();
            if (previewUrls != null || previewUrls.size() != 0) {
                previewUrls = previewUrls.stream().filter(n -> !StringUtils.isBlank(n)).collect(Collectors.toList());
            }

            //解析种子地址
            String torrentUrl;
            String[] torrentLink = page.getHtml().xpath("//div[@class='t_fsz']//td[@class='t_f']/")
                    .xpath("//ignore_js_op//a/@href").toString()
                    .split("aid=");
            if (torrentLink == null || torrentLink.length < 2) {
                LOGGER.info("无法正确解析种子的url={}", page.getUrl());
                return;
            } else {
                torrentUrl = TORRENT_URL_PREFIX + torrentLink[1];
            }

            //过滤空内容
            actressName = StringUtils.equals(actressName, "----") ? null : actressName;
            director = StringUtils.equals(director, "----") ? null : director;
            series = StringUtils.equals(series, "----") ? null : series;

            //组装ProductDO
            ProductDO productDO = new ProductDO();
            productDO.setCode(code);
            productDO.setTitle(title);

            //处理多女演员的情况。
            List<String> actresses = !org.apache.commons.lang3.StringUtils.isBlank(actressName) ?
                    Arrays.asList(actressName.split(" ")) : new ArrayList<>();

            productDO.setActressName(actresses.size() == 1 ? actressName : null);
            productDO.setManufacturer(manufactor);
            productDO.setSeries(series);
            productDO.setIssueDate(issueDate);
            productDO.setDuration(duration);
            productDO.setDirector(director);
            productDO.setCensoredType(1);
            productDO.setRegion(0);
            productDO.setType(0);
            productDO.setActresses(actresses);

            //组装TagDO
            TagDO tagDO = new TagDO();
            tagDO.setName("");

            //组装FilmDO
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
                        productImgDO.setType(2);
                        productImgDO.setUrl(n);
                        return productImgDO;
                    }).collect(Collectors.toList()));
                }
            }

            FilmDO filmDO = new FilmDO();
            filmDO.setFileUrl(torrentUrl);
            filmDO.setIsHD(1);
            filmDO.setFileFormat(fileFormat);
            filmDO.setSize(fileSize);
            page.putField("productDO", productDO);
            page.putField("productImgDOList", productImgDOList);
            page.putField("filmDO", filmDO);
            page.putField("tagDO", tagDO);

        } else {
            LOGGER.info("不支持的url和内容。url={}，content={}", page.getUrl(), page.getRawText());
        }
    }

    public Site getSite() {
        return site;
    }
}

package juda.zhang.studio.taohuazuspider.spider.processor;

import juda.zhang.studio.taohuazuspider.core.model.FilmDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
import juda.zhang.studio.taohuazuspider.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 解析桃花族论坛亚洲有碼原創板块的帖子，下载其中的种子文件以及图片。
 * Created by 晨辉 on 2017/6/5.
 */
@Service("thzAisaCensoredDetailPageProcessor")
public class ThzAisaCensoredDetailPageProcessor implements PageProcessor {

    public static final String DOMAIN = "taohuabbs.cc";
    public static final String TORRENT_URL_PREFIX = "http://" + DOMAIN + "/forum.php?mod=attachment&aid=";
    public static final int TIME_OUT = 60000;
    public static final int SLEEP_TIME = 100;
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
    /*
    列表页面
    http://taohuabbs.cc/forum-220-1.html
    http://taohuabbs.cc/forum-220-2.html
     */
    public static final String URL_LIST = "http://taohuabbs\\.cc/forum-220-\\d+\\.html";
    /*
    详情页面
    http://taohuabbs.cc/thread-1064269-1-1.html
    http://taohuabbs.cc/thread-1064271-1-5.html
     */
    public static final String URL_POST = "http://taohuabbs\\.cc/thread-\\d+-\\d+-\\d+\\.html";
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzAisaCensoredDetailPageProcessor.class);
    private Site site = Site
            .me()
            .setDomain(DOMAIN)
            .setSleepTime(SLEEP_TIME)
            .setTimeOut(TIME_OUT)
            .setUserAgent(USER_AGENT);

    public void process(Page page) {

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            ////tbody[@id='normalthread*']/tr/td[@class="icn"]
            //Selectable s=page.getHtml().xpath("//tbody[@id='normalthread'*]/tr/td[@class='icn']");
            page.addTargetRequests(page.getHtml().$("tbody[id^=normalthread_]").xpath("//tbody/tr/td[@class='icn']").links().regex(URL_POST).all());
            //获取所有符合列表格式的列表页
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else if (page.getUrl().regex(URL_POST).match()) {
            //获取完整标题
            String fullTitle = StringUtils.trimAndUpper(page.getHtml().xpath("//span[@id='thread_subject']/text()").toString());
            if (StringUtils.isBlank(fullTitle)) {
                LOGGER.info("无法正确解析的url={}", page.getUrl());
                return;
            }

            //解析编号
            String regex = "(?<=\\[)(\\S+)(?=\\])";
            Matcher m = Pattern.compile(regex).matcher(fullTitle);
            String code = StringUtils.trimAndUpper(m.find() ? m.group() : "");
            //解析标题

            //解析封面
            String coverImgUrl = page.getHtml().xpath("//ignore_js_op/img/@file").toString();
            if (StringUtils.isBlank(coverImgUrl)) {
                LOGGER.info("无法正确解析封面预览的url={}", page.getUrl());
                return;
            }

            //解析预览
            List<String> previewUrls = page.getHtml().xpath("//div[@class='pcb']//td[@class='t_f']/img/@file").all();
            if (previewUrls != null || previewUrls.size() != 0) {
                previewUrls = previewUrls.stream().filter(n -> !StringUtils.isBlank(n)).collect(Collectors.toList());
            }

            //解析种子地址
            String torrentUrl;
            String[] torrentLink = page.getHtml().xpath("//div[@class='pattl']//a/@href").toString().split("aid=");
            if (torrentLink == null || torrentLink.length < 2) {
                LOGGER.info("无法正确解析种子的url={}", page.getUrl());
                return;
            } else {
                torrentUrl = TORRENT_URL_PREFIX + torrentLink[1];
            }

            ProductDO productDO = new ProductDO();
            productDO.setCode(code);
            productDO.setTitle(fullTitle);
            productDO.setCoverImgUrl(coverImgUrl);
            productDO.setPreviewUrls(previewUrls);

            FilmDO filmDO = new FilmDO();
            filmDO.setFileUrl(torrentUrl);
            page.putField("productDO", productDO);
            page.putField("filmDO", filmDO);
        } else {
            LOGGER.info("不支持的url={}", page.getUrl());
        }
    }

    public Site getSite() {
        return site;
    }
}

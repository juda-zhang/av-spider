package juda.zhang.studio.taohuazuspider.core.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 解析桃花族论坛亚洲有碼原創板块的帖子，下载其中的种子文件以及图片。
 * Created by 晨辉 on 2017/6/5.
 */
public class TaohuazuPageProcessor implements PageProcessor {

    public static final String DOMAIN = "taohuabbs.cc";

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

    private Site site = Site
            .me()
            .setDomain(DOMAIN)
            .setSleepTime(SLEEP_TIME)
            .setUserAgent(USER_AGENT);

    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            ////tbody[@id='normalthread*']/tr/td[@class="icn"]
            //Selectable s=page.getHtml().xpath("//tbody[@id='normalthread'*]/tr/td[@class='icn']");
            page.addTargetRequests(page.getHtml().xpath("//tbody/tr/td[@class='icn']").links().regex(URL_POST).all());
            //获取所有符合列表格式的列表页
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else if (page.getUrl().regex(URL_POST).match()) {
            String title = page.getHtml().xpath("//span[@id='thread_subject']/text()").toString();

            page.putField("title", title.toString());
            /*page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
            page.putField("date",
                    page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));*/
        } else {
            System.out.println("不支持的url=" + page.getUrl());
        }
    }

    public Site getSite() {
        return site;
    }
}

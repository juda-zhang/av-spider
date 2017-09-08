package juda.zhang.studio.avspider.spider.dmm.processor;

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
 * 解析DMM网站的图片帖子，下载其中的种子文件以及图片。
 * Created by Juda.Zhang on 2017/6/5.
 */
@Service("dmmDateSortedPageProcessor")
public class DMMDateSortedPageProcessor implements PageProcessor {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
    private final static Logger LOGGER = LoggerFactory.getLogger(DMMDateSortedPageProcessor.class);
    @Value("${dmm.site.domain}")
    private String DOMAIN;
    /*
    日期排序的列表页面
    http://www.dmm.co.jp/digital/videoa/-/list/=/sort=date/page=1/
    http://www.dmm.co.jp/digital/videoa/-/list/=/sort=date/page=417/
     */
    @Value("http://${dmm.site.domain}/digital/videoa/-/list/=/sort=date/page=\\d+\\/")
    private String DATE_SORTED_URL_LIST;
    /*
    详情页面
    http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=hnd00420/?i3_ref=list&i3_ord=4
    http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=21awpr00009/?i3_ref=list&i3_ord=25
     */
    @Value("http://${dmm.site.domain}/digital/videoa/-/detail/=/cid=\\w+/\\w+")
    private String URL_POST;
    @Value("${dmm.site.timeout}")
    private int TIME_OUT;
    @Value("${dmm.site.sleep.time}")
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
        if (page.getUrl().regex(DATE_SORTED_URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().$("tbody[id^=normalthread_]")
                    .xpath("//tbody/tr/td[@class='icn']").links().regex(URL_POST).all());
            //获取所有符合列表格式的列表页
            page.addTargetRequests(page.getHtml().links().regex(DATE_SORTED_URL_LIST).all());
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
                    .xpath("//div[@id='ct']//div[@class='t_fsz']//td[@class='t_f']/text()");
            //获取标题
            String title = StringUtils.trim(content.regex("片名：(.*?)容量").toString());
            //获取文件大小
            long fileSize = (long) (new Double(StringUtils.trim(content.regex("容量：(.*?)GB").toString())) * 1000);
            //文件格式
            String fileFormat = StringUtils.trim(content.regex("格式：(.*?)配信開始日").toString());
            //发行日期
            String issueDate = StringUtils.trim(content.regex("商品発売日：[  ]*(.*?)収録時間").toString());
            //片长
            Integer duration = new Integer(StringUtils.trim(content.regex("収録時間：[  ]*(.*?)分").toString()));
            //演员
            String actressName = StringUtils.trim(content.regex(" 出演者：[  ]*(.*?)監督").toString());
            //監督
            String director = StringUtils.trim(content.regex(" 監督：[  ]*(.*?)シリーズ").toString());
            //シリーズ 系列
            String series = StringUtils.trim(content.regex(" シリーズ：[  ]*(.*?)メーカー").toString());
            //メーカー 生厂商
            String manufactor = StringUtils.trim(content.regex(" メーカー：[  ]*(.*?)レーベル").toString());
            //レーベル 发行商
            String producer = StringUtils.trim(content.regex(" レーベル：[  ]*(.*?)ジャンル").toString());
            //ジャンル 标签

            //解析封面
            String coverImgUrl = page.getHtml().xpath("//ignore_js_op/img/@file").toString();

            //解析预览
            List<String> previewUrls = page.getHtml()
                    .xpath("//div[@class='pcb']//td[@class='t_f']/img/@file").all();
            if (previewUrls != null || previewUrls.size() != 0) {
                previewUrls = previewUrls.stream().filter(n -> !StringUtils.isBlank(n)).collect(Collectors.toList());
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
            filmDO.setIsHD(1);
            filmDO.setFileFormat(fileFormat);
            filmDO.setSize(fileSize);
            page.putField("productDO", productDO);
            page.putField("productImgDOList", productImgDOList);
            page.putField("filmDO", filmDO);
            page.putField("tagDO", tagDO);

        } else {
            LOGGER.info("不支持的url={}", page.getUrl());
        }
    }

    public Site getSite() {
        return site;
    }
}

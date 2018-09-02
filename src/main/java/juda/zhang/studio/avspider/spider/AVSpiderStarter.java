package juda.zhang.studio.avspider.spider;

import juda.zhang.studio.avspider.spider.bailu.pipline.BailuDBStorePipeline;
import juda.zhang.studio.avspider.spider.bailu.pipline.BailuFileSavePipeline;
import juda.zhang.studio.avspider.spider.bailu.processor.BailuAisaCensoredDetailPageProcessor;
import juda.zhang.studio.avspider.spider.taohuazu.pipline.ThzDBStorePipeline;
import juda.zhang.studio.avspider.spider.taohuazu.pipline.ThzFileSavePipeline;
import juda.zhang.studio.avspider.spider.taohuazu.processor.ThzAisaCensoredDetailPageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juda.Zhang on 2017/6/7.
 */
@Service
public class AVSpiderStarter {
    public static final String TEMP_FILE_DIR = "C:/TEMP";
    private final static Logger LOGGER = LoggerFactory.getLogger(AVSpiderStarter.class);
    @Value("${spider.thread}")
    private int THREAD_NUM;
    private List<Spider> spiderList = new ArrayList<>();

    //桃花族相关
    @Value("http://${site.domain}/forum-220-1.html")
    private String START_URL;
    @Resource
    private ThzAisaCensoredDetailPageProcessor thzAisaCensoredDetailPageProcessor;
    @Resource
    private ThzFileSavePipeline thzFileSavePipeline;
    @Resource
    private ThzDBStorePipeline thzDBStorePipeline;

    //百撸相关
    @Value("http://${bailu.site.domain}/forum-2-1.html")
    private String BAILU_START_URL;
    @Resource
    private BailuAisaCensoredDetailPageProcessor bailuAisaCensoredDetailPageProcessor;
    @Resource
    private BailuFileSavePipeline bailuFileSavePipeline;
    @Resource
    private BailuDBStorePipeline bailuDBStorePipeline;

    @PostConstruct
    private void run() {
        spiderList.add(Spider.create(thzAisaCensoredDetailPageProcessor)
                .thread(THREAD_NUM)
                //.setScheduler(new FileCacheQueueScheduler(TEMP_FILE_DIR))
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(thzFileSavePipeline)
                .addPipeline(thzDBStorePipeline));
/*        spiderList.add(Spider.create(bailuAisaCensoredDetailPageProcessor)
                .thread(THREAD_NUM)
                //.setScheduler(new FileCacheQueueScheduler(TEMP_FILE_DIR))
                .addUrl(BAILU_START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(bailuFileSavePipeline)
                *//*.addPipeline(bailuDBStorePipeline)*//*);*/

        spiderList.forEach(spider -> {
            try {
                SpiderMonitor.instance().register(spider);
            } catch (JMException e) {
                LOGGER.error("spider监控注册失败!", e);
            }
            spider.start();
            LOGGER.info("spider启动完毕!");

        });

    }

    @PreDestroy
    public void destroy() {
        spiderList.forEach(spider -> spider.stop());
        LOGGER.info("spider关闭完毕！");
    }
}

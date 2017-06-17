package juda.zhang.studio.avspider.spider;

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

/**
 * Created by Juda.Zhang on 2017/6/7.
 */
@Service
public class AVSpiderStarter {
    public static final String TEMP_FILE_DIR = "C:/TEMP";
    private final static Logger LOGGER = LoggerFactory.getLogger(AVSpiderStarter.class);
    @Value("${spider.thread}")
    private int THREAD_NUM;
    @Value("http://${site.domain}/forum-220-1.html")
    private String START_URL;
    @Resource
    private ThzAisaCensoredDetailPageProcessor thzAisaCensoredDetailPageProcessor;
    @Resource
    private ThzFileSavePipeline thzFileSavePipeline;
    @Resource
    private ThzDBStorePipeline thzDBStorePipeline;

    private Spider taohuazuSpider;

    @PostConstruct
    private void run() {
        taohuazuSpider = Spider.create(thzAisaCensoredDetailPageProcessor)
                .thread(THREAD_NUM)
                //.setScheduler(new FileCacheQueueScheduler(TEMP_FILE_DIR))
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(thzFileSavePipeline)
                .addPipeline(thzDBStorePipeline);
        try {
            SpiderMonitor.instance().register(taohuazuSpider);
        } catch (JMException e) {
            LOGGER.error("taohuazu-spider监控注册失败!", e);
        }
        taohuazuSpider.start();
        LOGGER.info("taohuazu-spider启动完毕!");
    }

    @PreDestroy
    public void destroy() {
        taohuazuSpider.stop();
        LOGGER.info("taohuazu-spider关闭完毕！");
    }
}

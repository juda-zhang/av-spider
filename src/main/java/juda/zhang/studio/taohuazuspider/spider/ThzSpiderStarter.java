package juda.zhang.studio.taohuazuspider.spider;

import juda.zhang.studio.taohuazuspider.spider.pipline.ThzFileSavePipline;
import juda.zhang.studio.taohuazuspider.spider.processor.ThzAisaCensoredDetailPageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.management.JMException;

/**
 * Created by zhangchenhui160 on 2017/6/7.
 */
@Service
public class ThzSpiderStarter {
    public static final int THREAD_NUM = 1;
    public static final String TEMP_FILE_DIR = "C:/TEMP";
    public static final String START_URL = "http://taohuabbs.cc/forum-220-1.html";
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzSpiderStarter.class);
    @Resource
    private ThzAisaCensoredDetailPageProcessor thzAisaCensoredDetailPageProcessor;
    @Resource
    private ThzFileSavePipline thzFileSavePipline;

    private Spider taohuazuSpider;

    @PostConstruct
    private void run() {
        taohuazuSpider = Spider.create(thzAisaCensoredDetailPageProcessor)
                .thread(THREAD_NUM)
                .setScheduler(new FileCacheQueueScheduler(TEMP_FILE_DIR))
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(thzFileSavePipline);
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

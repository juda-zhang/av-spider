package juda.zhang.studio.taohuazuspider.spider;

import juda.zhang.studio.taohuazuspider.spider.pipline.TaohuazuPipline;
import juda.zhang.studio.taohuazuspider.spider.processor.TaohuazuPageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.management.JMException;

/**
 * Created by zhangchenhui160 on 2017/6/7.
 */
@Service
public class TaohuazuSpiderStarter {
    public static final int THREAD_NUM = 1;
    public static final String START_URL = "http://taohuabbs.cc/forum-220-1.html";
    private final static Logger LOGGER = LoggerFactory.getLogger(TaohuazuSpiderStarter.class);
    @Resource
    private TaohuazuPageProcessor taohuazuPageProcessor;
    @Resource
    private TaohuazuPipline taohuazuPipline;

    private Spider taohuazuSpider;

    @PostConstruct
    private void run() {
        taohuazuSpider = Spider.create(taohuazuPageProcessor)
                .thread(THREAD_NUM)
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(taohuazuPipline);
        try {
            SpiderMonitor.instance().register(taohuazuSpider);
        } catch (JMException e) {
            LOGGER.error("taohuazu-spider监控注册失败!", e);
        }
        taohuazuSpider.start();
        taohuazuSpider.stop();
        LOGGER.info("taohuazu-spider启动完毕!");
    }

    @PreDestroy
    public void destroy() {
        taohuazuSpider.stop();
        LOGGER.info("taohuazu-spider关闭完毕！");
    }
}

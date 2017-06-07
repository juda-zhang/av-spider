package juda.zhang.studio.taohuazuspider.spider;

import juda.zhang.studio.taohuazuspider.spider.pipline.TaohuazuPipline;
import juda.zhang.studio.taohuazuspider.spider.processor.TaohuazuPageProcessor;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.annotation.Resource;
import javax.management.JMException;

/**
 * Created by zhangchenhui160 on 2017/6/7.
 */
@Service
public class TaohuazuSpiderStarter {

    public static final int THREAD_NUM = 1;

    public static final String START_URL = "http://taohuabbs.cc/forum-220-1.html";
    @Resource
    private TaohuazuPageProcessor taohuazuPageProcessor;
    @Resource
    private TaohuazuPipline taohuazuPipline;

    public TaohuazuSpiderStarter() {
        //run();
    }

    private void run() {
        Spider taohuazuSpider = Spider.create(taohuazuPageProcessor)
                .thread(THREAD_NUM)
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(taohuazuPipline);
        try {
            SpiderMonitor.instance().register(taohuazuSpider);
        } catch (JMException e) {
            e.printStackTrace();
        }
        taohuazuSpider.start();
    }
}

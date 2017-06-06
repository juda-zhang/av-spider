package juda.zhang.studio.taohuazuspider;

import juda.zhang.studio.taohuazuspider.core.pipline.TaohuazuPipline;
import juda.zhang.studio.taohuazuspider.core.processor.TaohuazuPageProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.management.JMException;

/**
 * Created by 晨辉 on 2017/6/5.
 */
public class Main {
    public static final int THREAD_NUM = 1;

    public static final String START_URL = "http://taohuabbs.cc/forum-220-1.html";

    public static void main(String[] args) {
        Spider taohuazuSpider = Spider.create(new TaohuazuPageProcessor())
                .thread(THREAD_NUM)
                .addUrl(START_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new TaohuazuPipline());
        try {
            SpiderMonitor.instance().register(taohuazuSpider);
        } catch (JMException e) {
            e.printStackTrace();
        }
        taohuazuSpider.start();
    }
}

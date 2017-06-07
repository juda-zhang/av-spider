package juda.zhang.studio.taohuazuspider.spider.processor;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;

/**
 * @author yuewenxin
 * @version v 0.1 15/1/20 16:11 aaronyue Exp $$
 */
@RunWith(SpringRunner.class)
public class ThzAisaCensoredDetailPageProcessorTest {

    @Resource
    @InjectMocks
    private ThzAisaCensoredDetailPageProcessor thzAisaCensoredDetailPageProcessor;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);

    }

    public void testProcess() {
        Page page = new Page();
        thzAisaCensoredDetailPageProcessor.process(page);
    }
}

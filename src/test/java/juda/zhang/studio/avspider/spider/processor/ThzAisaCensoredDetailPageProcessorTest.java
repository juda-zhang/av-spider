package juda.zhang.studio.avspider.spider.processor;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;

/**
 * @author Juda.Zhang
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

package juda.zhang.studio.taohuazuspider.spider.processor;


import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;

/**
 * @author yuewenxin
 * @version v 0.1 15/1/20 16:11 aaronyue Exp $$
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {Main.class})
public class TaohuazuPageProcessorTest {

    @Resource
    @InjectMocks
    private TaohuazuPageProcessor taohuazuPageProcessor;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);

    }

    public void testProcess() {
        Page page = new Page();
        taohuazuPageProcessor.process(page);
    }
}

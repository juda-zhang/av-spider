package juda.zhang.studio.taohuazuspider.spider.pipline;

import juda.zhang.studio.taohuazuspider.core.dal.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * 数据库保存的管道
 * Created by Juda.Zhang on 2017/6/9.
 */
@Service("thzDBStorePipeline")
public class ThzDBStorePipeline implements Pipeline {
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzDBStorePipeline.class);
    @Resource
    private ProductMapper productMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}

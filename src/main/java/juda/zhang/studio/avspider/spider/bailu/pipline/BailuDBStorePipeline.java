package juda.zhang.studio.avspider.spider.bailu.pipline;

import juda.zhang.studio.avspider.core.manager.ProductManager;
import juda.zhang.studio.avspider.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库保存的管道
 * Created by Juda.Zhang on 2017/6/9.
 */
@Service("bailuDBStorePipeline")
public class BailuDBStorePipeline implements Pipeline {
    private final static Logger LOGGER = LoggerFactory.getLogger(BailuDBStorePipeline.class);
    @Resource
    private ProductManager productManager;

    @Override
    public void process(ResultItems resultItems, Task task) {
        ProductDO productDO = resultItems.get("productDO");
        List<ProductImgDO> productImgDOList = resultItems.get("productImgDOList");
        FilmDO filmDO = resultItems.get("filmDO");
        ActressDO actressDO = resultItems.get("actress");
        TagDO tagDO = resultItems.get("tagDO");

        if (productDO != null) {
            productManager.addOrUpdateProduct(productDO);
        }

    }
}

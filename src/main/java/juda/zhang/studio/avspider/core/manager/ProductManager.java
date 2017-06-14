package juda.zhang.studio.avspider.core.manager;

import juda.zhang.studio.avspider.core.model.ActressDO;
import juda.zhang.studio.avspider.core.model.ProductDO;

/**
 * 管理产品的管理类
 * Created by zhangchenhui160 on 2017/6/9.
 */
public interface ProductManager {
    /**
     * 添加或者更新产品
     *
     * @param productDO
     */
    ProductDO addOrUpdateProduct(ProductDO productDO);

    /**
     * 添加或更新女演员
     *
     * @param actressDO
     */
    ActressDO addOrUpdateActress(ActressDO actressDO);
}

package juda.zhang.studio.taohuazuspider.core.manager;

import juda.zhang.studio.taohuazuspider.core.model.ProductDO;

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
    void addOrUpdateProduct(ProductDO productDO);
}

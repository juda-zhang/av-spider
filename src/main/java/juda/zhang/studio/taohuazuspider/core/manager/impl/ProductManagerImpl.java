package juda.zhang.studio.taohuazuspider.core.manager.impl;

import juda.zhang.studio.taohuazuspider.core.dal.mapper.ProductMapper;
import juda.zhang.studio.taohuazuspider.core.manager.ProductManager;
import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangchenhui160 on 2017/6/9.
 */
@Service("productManager")
public class ProductManagerImpl implements ProductManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductManager.class);

    @Resource
    private ProductMapper productMapper;

    @Override
    public void addOrUpdateProduct(ProductDO productDO) {

    }
}

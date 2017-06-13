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
        String code = productDO.getCode();
        ProductDO productDOExist = productMapper.getByCode(code);
        if (productDOExist != null) {
            LOGGER.info("产品已存在，更新内容！code={}", productDO.getCode());
            productDO.setId(productDOExist.getId());
            productMapper.update(productDO);
        } else {
            LOGGER.info("新增产品！code={}", productDO.getCode());
            productMapper.insert(productDO);
        }
    }
}

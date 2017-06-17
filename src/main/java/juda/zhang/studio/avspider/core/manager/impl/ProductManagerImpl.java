package juda.zhang.studio.avspider.core.manager.impl;

import juda.zhang.studio.avspider.core.dal.mapper.ActressMapper;
import juda.zhang.studio.avspider.core.dal.mapper.ProductMapper;
import juda.zhang.studio.avspider.core.manager.ProductManager;
import juda.zhang.studio.avspider.core.model.ActressDO;
import juda.zhang.studio.avspider.core.model.ProductDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Juda.Zhang on 2017/6/9.
 */
@Service("productManager")
public class ProductManagerImpl implements ProductManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductManager.class);

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ActressMapper actressMapper;

    @Override
    public ProductDO addOrUpdateProduct(ProductDO productDO) {
        String code = productDO.getCode();
        ProductDO productDOExist = productMapper.getByCode(code);
        if (productDOExist != null) {
            LOGGER.info("产品已存在，更新内容！code={}", productDO.getCode());
            productDO.setId(productDOExist.getId());
            productDO.setGmtCreated(productDOExist.getGmtCreated());
            productMapper.update(productDO);
        } else {
            LOGGER.info("新增产品！code={}", productDO.getCode());
            productMapper.insert(productDO);
        }

        return productDO;
    }

    @Override
    public ActressDO addOrUpdateActress(ActressDO actressDO) {
        String name = actressDO.getName();
        ActressDO actressDOExist = actressMapper.getByName(name);
        if (actressDOExist != null) {
            LOGGER.info("女演员已存在，更新内容！name={}", actressDOExist.getName());
            actressDO.setId(actressDOExist.getId());
            actressDO.setGmtCreated(actressDOExist.getGmtCreated());
            actressMapper.update(actressDO);
        } else {
            LOGGER.info("新增女演员！name={}", actressDOExist.getName());
            actressMapper.insert(actressDO);
        }

        return actressDO;
    }
}

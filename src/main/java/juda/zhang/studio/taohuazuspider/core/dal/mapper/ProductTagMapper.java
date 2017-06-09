package juda.zhang.studio.taohuazuspider.core.dal.mapper;

import juda.zhang.studio.taohuazuspider.core.model.ProductTagDO;

/**
 * Created by zhangchenhui160 on 2017/6/9.
 */
public interface ProductTagMapper {

    void insert(ProductTagDO productTagDO);

    int update(ProductTagDO productTagDO);

    void deleteById(long id);
}

package juda.zhang.studio.taohuazuspider.core.dal.mapper;

import juda.zhang.studio.taohuazuspider.core.model.ProductActressDO;

/**
 * Created by zhangchenhui160 on 2017/6/9.
 */
public interface ProductActressMapper {

    void insert(ProductActressDO productActressDO);

    int update(ProductActressDO productActressDO);

    void deleteById(long id);
}

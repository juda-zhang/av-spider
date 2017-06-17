package juda.zhang.studio.avspider.core.dal.mapper;

import juda.zhang.studio.avspider.core.model.ProductDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * Created by Juda.Zhang on 2017/6/9.
 */
@Service("productMapper")
public interface ProductMapper extends GenricMapper<ProductDO> {

    /**
     * 根据编码获取唯一产品
     *
     * @param code
     * @return
     */
    ProductDO getByCode(@Param("code") String code);

}

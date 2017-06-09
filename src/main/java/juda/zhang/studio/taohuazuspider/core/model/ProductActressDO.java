package juda.zhang.studio.taohuazuspider.core.model;

/**
 * 产品和女优的多对多关系
 * Created by zhangchenhui160 on 2017/6/9.
 */
public class ProductActressDO extends BaseDO {

    private long productId;

    private long actressId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getActressId() {
        return actressId;
    }

    public void setActressId(long actressId) {
        this.actressId = actressId;
    }
}

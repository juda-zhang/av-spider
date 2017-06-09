package juda.zhang.studio.taohuazuspider.core.model;

/**
 * 产品和标签的多对多关系
 * Created by zhangchenhui160 on 2017/6/9.
 */
public class ProductTagDO extends BaseDO {
    /**
     * 产品id
     */
    private long productId;
    /**
     * 标签Id
     */
    private long tagId;

    /**
     * 冗余的标签名称
     */
    private String tagName;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

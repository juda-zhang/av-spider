package juda.zhang.studio.avspider.core.model;

/**
 * 图片预览
 * Created by Juda.Zhang on 2017/6/9.
 */
public class ProductImgDO extends BaseDO {

    private long productId;

    private String url;
    /**
     * 0:封面 1:封底 2:内容预览
     */
    private int type;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

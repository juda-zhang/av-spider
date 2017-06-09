package juda.zhang.studio.taohuazuspider.core.model;

/**
 * 图片预览
 * Created by zhangchenhui160 on 2017/6/9.
 */
public class ProductImgUrlDO {

    private String url;
    /**
     * 0:封面 1:封底 2:内容预览
     */
    private int type;

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

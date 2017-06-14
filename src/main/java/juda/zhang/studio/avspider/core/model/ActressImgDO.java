package juda.zhang.studio.avspider.core.model;

/**
 * 图片预览
 * Created by zhangchenhui160 on 2017/6/9.
 */
public class ActressImgDO {

    private long actressId;

    private String url;
    /**
     * 0:封面 2:内容预览
     */
    private int type;

    public long getActressId() {
        return actressId;
    }

    public void setActressId(long actressId) {
        this.actressId = actressId;
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

package juda.zhang.studio.avspider.core.model;

/**
 * 女优姓名。一个女优可能会有多个名字。
 * 与ActressDO关系为1：N
 * Created by Juda.Zhang on 2017/6/7.
 */
public class ActressNameDO extends BaseDO {
    /**
     * 女优Id
     */
    private long actressId;
    /**
     * 艺名，唯一
     */
    private String name;
    /**
     * 英文名，唯一
     */
    private String englishName;
    /**
     * 1:显示 0:不显示
     */
    private int isShow;

    public long getActressId() {
        return actressId;
    }

    public void setActressId(long actressId) {
        this.actressId = actressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
}

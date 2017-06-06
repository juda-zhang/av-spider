package juda.zhang.studio.taohuazuspider.core.model;

/**
 * 女优姓名。一个女优可能会有多个名字。
 * 与ActressDO关系为1：N
 * Created by Juda.Zhang on 2017/6/7.
 */
public class ActressNameDO {
    /**
     * 女优Id
     */
    private long actressId;
    /**
     * 姓名，唯一
     */
    private String name;
    /**
     * 英文名
     */
    private String englishName;

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
}

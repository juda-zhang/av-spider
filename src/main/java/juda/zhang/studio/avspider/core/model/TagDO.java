package juda.zhang.studio.avspider.core.model;

/**
 * 标签
 * Created by Juda.Zhang on 2017/6/7.
 */
public class TagDO extends BaseDO {
    /**
     * 标签中文名称，唯一
     */
    private String name;
    /**
     * 标签日文名，唯一
     */
    private String japaneseName;
    /**
     * 标签英文名，唯一
     */
    private String englishName;
    /**
     * 类别
     */
    private String category;

    public String getJapaneseName() {
        return japaneseName;
    }

    public void setJapaneseName(String japaneseName) {
        this.japaneseName = japaneseName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

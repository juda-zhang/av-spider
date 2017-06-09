package juda.zhang.studio.taohuazuspider.core.model;

import java.util.List;

/**
 * 描述作品的DO
 * Created by 晨辉 on 2017/6/6.
 */
public class ProductDO extends BaseDO {
    /**
     * 编号
     */
    private String code;
    /**
     * 制片商
     */
    private String manufacturer;
    /**
     * 标题
     */
    private String title;
    /**
     * 0:亚洲 1:欧美 2:大陆 3:港台 4:东南亚 5:中亚 6:南美 7:其他
     */
    private Integer region;
    /**
     * 0：无码 1:有码
     */
    private Integer censoredType;
    /**
     * 0:成人 1:素人 2:三级 3:写真
     */
    private Integer type;
    /**
     * 主要女优姓名
     */
    private String actressName;
    /**
     * 主要男优姓名
     */
    private String actorName;
    /**
     * 导演
     */
    private String director;
    /**
     * 出品方
     */
    private String producer;
    /**
     * 系列
     */
    private String series;
    /**
     * 片长，单位分钟
     */
    private Integer duration;
    /**
     * 发行日期,YYYY-MM-DD
     */
    private String issueDate;
    /**
     * 标签
     */
    private List<String> tags;
    /**
     * 所有女优姓名
     */
    private List<String> actresses;
    /**
     * 预览
     */
    private List<ProductImgUrlDO> previewUrls;

    public void setRegion(Integer region) {
        this.region = region;
    }

    public void setCensoredType(Integer censoredType) {
        this.censoredType = censoredType;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getActressName() {
        return actressName;
    }

    public void setActressName(String actressName) {
        this.actressName = actressName;
    }

    public List<String> getActresses() {
        return actresses;
    }

    public void setActresses(List<String> actresses) {
        this.actresses = actresses;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }


    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getCensoredType() {
        return censoredType;
    }

    public void setCensoredType(int censoredType) {
        this.censoredType = censoredType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ProductImgUrlDO> getPreviewUrls() {
        return previewUrls;
    }

    public void setPreviewUrls(List<ProductImgUrlDO> previewUrls) {
        this.previewUrls = previewUrls;
    }

    /**
     * 返回[code]title的完整名称
     *
     * @return
     */
    public String getFullTitle() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(code).append("]").append(title);
        return sb.toString();
    }

}

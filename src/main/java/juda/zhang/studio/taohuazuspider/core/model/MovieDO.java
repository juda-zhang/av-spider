package juda.zhang.studio.taohuazuspider.core.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述影片的DO
 * Created by 晨辉 on 2017/6/6.
 */
public class MovieDO extends BaseDO{
    /**
     * 编号前缀
     */
    private String codePrefix;
    /**
     * 编号
     */
    private String code;
    /**
     * 标题
     */
    private String title;
    /**
     * 女优名称
     */
    private List<String> actresses;
    /**
     * 男优名称
     */
    private List<String> actors;
    /**
     * 导演
     */
    private String director;
    /**
     * 制片商
     */
    private String manufacturer;
    /**
     * 出品方
     */
    private String producer;
    /**
     * 系列
     */
    private String series;
    /**
     * 标签
     */
    private List<String> tags;
    /**
     * 封面预览
     */
    private String coverImgUrl;
    /**
     * 内容预览
     */
    private List<String> previewUrls;

    /**
     * 种子文件的地址
     */
    private List<String> torrentUrls;

    public String getCodePrefix() {
        return codePrefix;
    }

    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
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

    public List<String> getActresses() {
        return actresses;
    }

    public void setActresses(List<String> actresses) {
        this.actresses = actresses;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public List<String> getPreviewUrls() {
        return previewUrls;
    }

    public void setPreviewUrls(List<String> previewUrls) {
        this.previewUrls = previewUrls;
    }

    public List<String> getTorrentUrls() {
        return torrentUrls;
    }

    public void setTorrentUrls(List<String> torrentUrls) {
        this.torrentUrls = torrentUrls;
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

    /**
     * 获取所有的预览
     *
     * @return
     */
    public List<String> getAllImgUrls() {
        List<String> imgs = new ArrayList<String>();
        if (!StringUtils.isBlank(coverImgUrl)) {
            imgs.add(coverImgUrl);
        }
        if (previewUrls != null && previewUrls.size() != 0) {
            imgs.addAll(previewUrls);
        }
        return imgs;
    }
}

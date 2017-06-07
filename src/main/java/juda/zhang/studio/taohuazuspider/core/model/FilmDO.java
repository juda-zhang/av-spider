package juda.zhang.studio.taohuazuspider.core.model;

/**
 * 具体电影的信息
 * Created by Juda.Zhang on 2017/6/7.
 */
public class FilmDO extends BaseDO {
    /**
     * 作品Id
     */
    private long productId;

    /**
     * 大小，单位M
     */
    private Long size;

    /**
     * 是否高清
     */
    private int isHD;
    /**
     * 是否有中文字幕
     */
    private int hasChineseSubtitle;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 0:种子 1:直接下载地址
     */
    private int fileUrlMode;
    /**
     * 后缀
     */
    private String fileFormat;
    /**
     * 种子文件的地址，下载地址
     */
    private String fileUrl;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public int getIsHD() {
        return isHD;
    }

    public void setIsHD(int isHD) {
        this.isHD = isHD;
    }

    public int getHasChineseSubtitle() {
        return hasChineseSubtitle;
    }

    public void setHasChineseSubtitle(int hasChineseSubtitle) {
        this.hasChineseSubtitle = hasChineseSubtitle;
    }

    public int getFileUrlMode() {
        return fileUrlMode;
    }

    public void setFileUrlMode(int fileUrlMode) {
        this.fileUrlMode = fileUrlMode;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}

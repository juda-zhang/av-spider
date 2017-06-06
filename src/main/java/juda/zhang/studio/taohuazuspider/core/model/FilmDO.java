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
     * 电影名称
     */
    private String fileName;
    /**
     * 大小，单位M
     */
    private Long size;
    /**
     * 后缀
     */
    private String format;
}

package juda.zhang.studio.taohuazuspider.core.model;

/**
 * 女优的基本信息
 * Created by Juda.Zhang on 2017/6/7.
 */
public class ActressDO extends BaseDO {
    /**
     * 女优姓名
     */
    private String name;
    /**
     * 身高，单位cm
     */
    private Integer height;
    /**
     * 罩杯
     */
    private String cup;
    /**
     * 0:亚洲 1:欧美 2:大陆 3:港台 4:东南亚 5:中亚 6:南美 7:其他
     */
    private Integer region;
    /**
     * 胸围
     */
    private Integer bust;
    /**
     * 腰围
     */
    private Integer waist;
    /**
     * 臀围
     */
    private Integer hips;
    /**
     * 爱好
     */
    private String hobbies;
}

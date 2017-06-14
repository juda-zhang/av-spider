package juda.zhang.studio.taohuazuspider.core.dal.mapper;

import juda.zhang.studio.taohuazuspider.core.model.ActressDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * Created by zhangchenhui160 on 2017/6/9.
 */
@Service("actressMapper")
public interface ActressMapper extends GenricMapper<ActressDO> {
    /**
     * 根据姓名获取唯一的女演员
     *
     * @param name
     * @return
     */
    ActressDO getByName(@Param("name") String name);
}

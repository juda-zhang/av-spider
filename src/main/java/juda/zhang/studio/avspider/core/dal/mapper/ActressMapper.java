package juda.zhang.studio.avspider.core.dal.mapper;

import juda.zhang.studio.avspider.core.model.ActressDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * Created by Juda.Zhang on 2017/6/9.
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

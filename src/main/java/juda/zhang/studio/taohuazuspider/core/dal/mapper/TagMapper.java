package juda.zhang.studio.taohuazuspider.core.dal.mapper;

import juda.zhang.studio.taohuazuspider.core.model.TagDO;

/**
 * Created by zhangchenhui160 on 2017/6/9.
 */
public interface TagMapper {

    void insert(TagDO tagDO);

    int update(TagDO tagDO);

    void deleteById(long id);
}

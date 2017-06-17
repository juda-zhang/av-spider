package juda.zhang.studio.avspider.core.dal.mapper;

import juda.zhang.studio.avspider.core.model.BaseDO;
import org.apache.ibatis.annotations.Param;

/**
 * @param <T> 具体的DO
 * @author Juda.Zhang
 */
public interface GenricMapper<T extends BaseDO> {
    /**
     * 新增记录
     *
     * @param entity
     * @return
     */
    void insert(T entity);

    /**
     * 根据唯一标识删除记录
     *
     * @param id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 修改记录
     *
     * @param entity
     */
    int update(T entity);

    /**
     * 根据唯一标识获取记录
     *
     * @param id
     * @return
     */
    T getById(@Param("id") Long id);
}

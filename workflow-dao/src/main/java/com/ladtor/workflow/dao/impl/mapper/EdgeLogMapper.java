package com.ladtor.workflow.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ladtor.workflow.dao.domain.EdgeLog;

/**
 * @author liudongrong
 * @date 2019/1/14 21:55
 */
public interface EdgeLogMapper extends BaseMapper<EdgeLog> {
    boolean saveOrUpdate(EdgeLog edgeLog);
}

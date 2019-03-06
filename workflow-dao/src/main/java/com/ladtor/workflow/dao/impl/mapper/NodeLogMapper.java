package com.ladtor.workflow.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ladtor.workflow.dao.domain.NodeLog;

/**
 * @author liudongrong
 * @date 2019/1/15 15:54
 */
public interface NodeLogMapper extends BaseMapper<NodeLog> {
    boolean saveOrUpdate(NodeLog nodeLog);
}

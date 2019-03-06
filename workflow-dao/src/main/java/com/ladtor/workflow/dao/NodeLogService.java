package com.ladtor.workflow.dao;

import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.dao.domain.NodeLog;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/15 15:55
 */
public interface NodeLogService{

    boolean save(NodeLog nodeLog);

    boolean update(NodeLog nodeLog);

    boolean saveOrUpdate(NodeLog nodeLog);

    List<NodeLog> list(ThreeTuple threeTuple);

    NodeLog get(FourTuple fourTuple);
}

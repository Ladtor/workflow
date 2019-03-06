package com.ladtor.workflow.dao;

import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.dao.domain.ExecuteLog;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/15 14:31
 */
public interface ExecuteLogService{
    List<ExecuteLog> listBySerialNo(String serialNo);

    ExecuteLog get(ThreeTuple threeTuple);

    boolean save(ExecuteLog executeLog);

    boolean update(ExecuteLog executeLog);
}

package com.ladtor.workflow.dao;

import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.dao.domain.EdgeLog;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/15 15:55
 */
public interface EdgeLogService{

    List<EdgeLog> list(ThreeTuple threeTuple);

    boolean saveOrUpdate(EdgeLog edgeLog);

}

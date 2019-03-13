package com.ladtor.workflow.dao;

import com.ladtor.workflow.dao.domain.WorkFlow;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/13 10:22
 */
public interface WorkFlowService {

    WorkFlow get(String serialNo);

    boolean update(WorkFlow workFlow);

    boolean save(WorkFlow workFlow);

    boolean delete(String serialNo);

    List<WorkFlow> list();

    List<WorkFlow> searchBySerialNoOrName(String keywords);

}

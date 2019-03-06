package com.ladtor.workflow.dao;

import com.ladtor.workflow.dao.domain.Graph;

/**
 * @author liudongrong
 * @date 2019/1/14 22:33
 */
public interface GraphService {

    boolean save(Graph graph);

    void update(Graph graph);

    Graph get(String serialNo, Integer version);

}

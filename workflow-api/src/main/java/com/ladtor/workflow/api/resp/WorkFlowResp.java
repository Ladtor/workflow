package com.ladtor.workflow.api.resp;

import com.ladtor.workflow.dao.domain.EdgeLog;
import com.ladtor.workflow.dao.domain.Graph;
import com.ladtor.workflow.dao.domain.NodeLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/14 22:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowResp {
    private Integer id;
    private String name;
    private String serialNo;
    private Integer version;
    private Integer runVersion;
    private Graph graph;
    private List<NodeLog> nodeLogList;
    private List<EdgeLog> edgeLogList;

    private Date createdAt;
    private Date updatedAt;
}

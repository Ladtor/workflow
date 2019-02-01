package com.ladtor.workflow.bo.resp;

import com.ladtor.workflow.bo.domain.EdgeLog;
import com.ladtor.workflow.bo.domain.NodeLog;
import com.ladtor.workflow.bo.req.GraphReq;
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
    private GraphReq graph;
    private List<NodeLog> nodeLogList;
    private List<EdgeLog> edgeLogList;

    private Date createdAt;
    private Date updatedAt;
}

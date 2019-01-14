package com.ladtor.workflow.bo.resp;

import com.ladtor.workflow.bo.GraphBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private GraphBo graph;

    private Date createdAt;
    private Date updatedAt;
}

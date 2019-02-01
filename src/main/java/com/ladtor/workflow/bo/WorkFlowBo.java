package com.ladtor.workflow.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 19:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowBo {
    private String serialNo;
    private String name;
    private Integer version;
    private GraphBo graph;
}

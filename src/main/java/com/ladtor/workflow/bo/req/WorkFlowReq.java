package com.ladtor.workflow.bo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/14 22:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowReq {
    private String name;
    private GraphReq graph;
}

package com.ladtor.workflow.bo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/25 16:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphReq {
    private List<NodeReq> nodes;
    private List<EdgeReq> edges;
}

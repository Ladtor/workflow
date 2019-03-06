package com.ladtor.workflow.api.req;

import com.alibaba.fastjson.JSONObject;
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
    private List<JSONObject> nodes;
    private List<JSONObject> edges;
}

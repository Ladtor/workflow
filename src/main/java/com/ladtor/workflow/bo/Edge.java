package com.ladtor.workflow.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 19:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    private String id;
    private String source;
    private String target;

    public boolean valid(JSONObject result) {
        // TODO: 2019/1/13
        return true;
    }
}

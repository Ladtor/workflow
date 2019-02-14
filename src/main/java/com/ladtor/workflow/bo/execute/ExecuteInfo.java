package com.ladtor.workflow.bo.execute;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.constant.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liudongrong
 * @date 2019/1/12 21:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ExecuteInfo implements Serializable {
    private FourTuple fourTuple;
    private NodeType nodeType;
    private JSONObject params;

    public JSONObject getParams() {
        if (params == null) {
            params = new JSONObject();
        }
        return params;
    }
}

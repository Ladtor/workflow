package com.ladtor.workflow.bo.execute;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.constant.NodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 21:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ExecuteInfo {
    private String serialNo;
    private Integer version;
    private Integer runVersion;
    private NodeType nodeType;
    private Node node;
    @Builder.Default
    private JSONObject result = new JSONObject();
}

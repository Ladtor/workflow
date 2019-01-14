package com.ladtor.workflow.bo;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.execute.*;
import com.ladtor.workflow.constant.NodeType;
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
public class Node {
    private String id;
    private String label;
    private NodeType nodeType;
    @Builder.Default
    private JSONObject extra = new JSONObject();

    public ExecuteInfo getExecuteInfo(Integer runVersion) {
        ExecuteInfo executeInfo = null;
        switch (nodeType) {
            case START:
                executeInfo = extra.toJavaObject(StartExecuteInfo.class);
                break;
            case RESULT:
                executeInfo = extra.toJavaObject(ResultExecuteInfo.class);
                break;
            case TASK:
                executeInfo = extra.toJavaObject(TaskExecuteInfo.class);
                break;
            case HTTP:
                executeInfo = extra.toJavaObject(HttpExecuteInfo.class);
                break;
            case WORK_FLOW:
                executeInfo = extra.toJavaObject(WorkFlowExecuteInfo.class);
                break;
        }
        if (executeInfo != null) {
            executeInfo.setNodeType(nodeType);
            executeInfo.setNode(this);
            executeInfo.setRunVersion(runVersion);
        }
        return executeInfo;
    }

    public boolean isReady(Integer runVersion) {
        return true;
    }
}

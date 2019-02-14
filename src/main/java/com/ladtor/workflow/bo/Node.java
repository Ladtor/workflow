package com.ladtor.workflow.bo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.domain.NodeLog;
import com.ladtor.workflow.bo.execute.*;
import com.ladtor.workflow.constant.NodeType;
import com.ladtor.workflow.constant.StatusEnum;
import com.ladtor.workflow.service.NodeLogService;
import com.ladtor.workflow.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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

    public ExecuteInfo getExecuteInfo(ThreeTuple threeTuple) {
        ExecuteInfo executeInfo = null;
        if (extra == null) extra = new JSONObject();
        extra.put("params", extra.getJSONObject("params"));
        extra.put("initParams", extra.getJSONObject("initParams"));
        extra.put("requestParams", extra.getJSONObject("requestParams"));
        switch (nodeType) {
            case START:
                executeInfo = extra.toJavaObject(StartExecuteInfo.class);
                break;
            case RESULT:
                executeInfo = extra.toJavaObject(ResultExecuteInfo.class);
                break;
            case TASK:
                executeInfo = extra.toJavaObject(TaskExecuteInfo.class);
                JSONArray task = extra.getJSONArray("task");
                ((TaskExecuteInfo) executeInfo).setTaskNodeKey(task.get(0).toString());
                ((TaskExecuteInfo) executeInfo).setTaskKey(task.get(1).toString());
                break;
            case HTTP:
                executeInfo = extra.toJavaObject(HttpExecuteInfo.class);
                break;
            case WORK_FLOW:
                executeInfo = extra.toJavaObject(WorkFlowExecuteInfo.class);
                break;
            case OR:
                executeInfo = extra.toJavaObject(OrExecuteInfo.class);
                break;
            case AND:
                executeInfo = extra.toJavaObject(AndExecuteInfo.class);
                break;
        }
        if (executeInfo != null) {
            executeInfo.setNodeType(this.getNodeType());
            executeInfo.setFourTuple(new FourTuple(threeTuple, id));
        }
        return executeInfo;
    }

    public boolean isReady(ThreeTuple threeTuple) {
        NodeLogService nodeLogService = BeanUtil.getBean(NodeLogService.class);
        NodeLog nodeLog = nodeLogService.get(new FourTuple(threeTuple, id));
        if (nodeLog == null) {
            return false;
        }
        return StatusEnum.SUCCESS.toString().equals(nodeLog.getStatus());
    }

    public JSONObject getExecuteResult(ThreeTuple threeTuple) {
        NodeLogService nodeLogService = BeanUtil.getBean(NodeLogService.class);
        NodeLog nodeLog = nodeLogService.get(new FourTuple(threeTuple, id));
        if (nodeLog != null && StringUtils.isNotEmpty(nodeLog.getResult())) {
            return JSON.parseObject(nodeLog.getResult());
        }
        return new JSONObject();
    }
}

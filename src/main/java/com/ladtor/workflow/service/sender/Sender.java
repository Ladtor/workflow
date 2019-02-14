package com.ladtor.workflow.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.execute.FourTuple;

/**
 * @author liudongrong
 * @date 2019/2/6 11:48
 */
public interface Sender {
    public JSONObject send(FourTuple fourTuple, String nodeKey, String taskKey, JSONObject params);
}

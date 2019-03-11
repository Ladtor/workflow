package com.ladtor.workflow.core.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;

/**
 * @author liudongrong
 * @date 2019/2/6 11:48
 */
public interface Sender {
    JSONObject send(FourTuple fourTuple, String nodeKey, String taskKey, JSONObject params);
}

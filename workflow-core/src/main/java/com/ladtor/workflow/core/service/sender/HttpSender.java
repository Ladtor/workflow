package com.ladtor.workflow.core.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.dao.TaskApplicationService;
import com.ladtor.workflow.dao.domain.TaskApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * @author liudongrong
 * @date 2019/2/6 11:49
 */
@Slf4j
public class HttpSender implements Sender {

    public static final String TASK_EXECUTE_PATH = "task/execute";
    private final RestTemplate restTemplate;
    private TaskApplicationService taskApplicationService;

    public HttpSender(RestTemplate restTemplate, TaskApplicationService taskApplicationService) {
        this.restTemplate = restTemplate;
        this.taskApplicationService = taskApplicationService;
    }

    @Override
    public JSONObject send(FourTuple fourTuple, String nodeKey, String taskKey, JSONObject params) {
        String url = getUrl(nodeKey, taskKey, fourTuple);
        JSONObject jsonObject = restTemplate.postForObject(url, params, JSONObject.class);
        return jsonObject;
    }

    private String getUrl(String nodeKey, String taskKey, FourTuple fourTuple) {
        TaskApplication taskApplication = taskApplicationService.get(nodeKey);
        String serviceUrl = taskApplication.getUrl();
        return String.format("%s/%s/%s/%s/%d/%d/%s", serviceUrl, TASK_EXECUTE_PATH, taskKey, fourTuple.getSerialNo(), fourTuple.getVersion(), fourTuple.getRunVersion(), fourTuple.getNodeId());
    }
}

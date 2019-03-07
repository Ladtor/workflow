package com.ladtor.workflow.core.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.FourTuple;
import com.ladtor.workflow.core.bo.Application;
import com.ladtor.workflow.core.exception.ClientException;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author liudongrong
 * @date 2019/2/6 11:49
 */
@Slf4j
public class HttpSender implements Sender {

    public static final String TASK_EXECUTE_PATH = "task/execute";
    private final RestTemplate restTemplate;
    private AdminServerProperties adminServerProperties;
    private ServerProperties serverProperties;

    public HttpSender(RestTemplate restTemplate, AdminServerProperties adminServerProperties, ServerProperties serverProperties) {
        this.restTemplate = restTemplate;
        this.adminServerProperties = adminServerProperties;
        this.serverProperties = serverProperties;
    }

    @Override
    public JSONObject send(FourTuple fourTuple, String nodeKey, String taskKey, JSONObject params) {
        String url = getUrl(nodeKey, taskKey, fourTuple);
        JSONObject jsonObject = restTemplate.postForObject(url, params, JSONObject.class);
        return jsonObject;
    }

    private String getUrl(String nodeKey, String taskKey, FourTuple fourTuple) {
        String address = "127.0.0.1";
        Integer port = 8080;
        if (serverProperties.getAddress() != null) {
            address = serverProperties.getAddress().getHostAddress();
        }
        if (serverProperties.getPort() != null) {
            port = serverProperties.getPort();
        }
        String adminContextPath = adminServerProperties.getContextPath();
        String url = String.format("http://%s:%d%s/applications/%s", address, port, adminContextPath, nodeKey);
        JSONObject jsonObject;
        try {
            jsonObject = restTemplate.getForObject(url, JSONObject.class);
        } catch (HttpClientErrorException e) {
            throw new ClientException("节点错误");
        }
        Application application = jsonObject.toJavaObject(Application.class);
        if (CollectionUtils.isEmpty(application.getInstances())) {
            throw new ClientException("找不到该节点");
        }
        int i = new Random().nextInt(application.getInstances().size());
        Application.Instance instance = application.getInstances().get(i);
        String serviceUrl = instance.getRegistration().getServiceUrl();
        return String.format("%s%s/%s/%s/%d/%d/%s", serviceUrl, TASK_EXECUTE_PATH, taskKey, fourTuple.getSerialNo(), fourTuple.getVersion(), fourTuple.getRunVersion(), fourTuple.getNodeId());
    }
}

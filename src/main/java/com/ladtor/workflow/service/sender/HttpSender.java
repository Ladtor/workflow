package com.ladtor.workflow.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.Application;
import com.ladtor.workflow.bo.execute.FourTuple;
import com.ladtor.workflow.exception.ClientException;
import com.ladtor.workflow.service.HttpClientTemplate;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.CollectionUtils;

import java.util.Random;

import static com.ladtor.workflow.service.HttpClientTemplate.GET;
import static com.ladtor.workflow.service.HttpClientTemplate.POST;

/**
 * @author liudongrong
 * @date 2019/2/6 11:49
 */
@Slf4j
public class HttpSender implements Sender {

    public static final String TASK_EXECUTE_PATH = "task/execute";
    private final HttpClientTemplate httpClientTemplate;
    private AdminServerProperties adminServerProperties;
    private ServerProperties serverProperties;

    public HttpSender(HttpClientTemplate httpClientTemplate, AdminServerProperties adminServerProperties, ServerProperties serverProperties) {
        this.httpClientTemplate = httpClientTemplate;
        this.adminServerProperties = adminServerProperties;
        this.serverProperties = serverProperties;
    }

    @Override
    public JSONObject send(FourTuple fourTuple, String nodeKey, String taskKey, JSONObject params) {
        return httpClientTemplate.execute(POST, getUrl(nodeKey, taskKey, fourTuple), params);
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
        JSONObject jsonObject = httpClientTemplate.execute(GET, String.format("http://%s:%d%s/applications/%s", address, port, adminContextPath, nodeKey));
        Application application = jsonObject.toJavaObject(Application.class);
        if (CollectionUtils.isEmpty(application.getInstances())) {
            throw new ClientException("节点错误");
        }
        int i = new Random().nextInt(application.getInstances().size());
        Application.Instance instance = application.getInstances().get(i);
        String serviceUrl = instance.getRegistration().getServiceUrl();
        return String.format("%s%s/%s/%s/%d/%d/%s", serviceUrl, TASK_EXECUTE_PATH, taskKey, fourTuple.getSerialNo(), fourTuple.getVersion(), fourTuple.getRunVersion(), fourTuple.getNodeId());
    }
}

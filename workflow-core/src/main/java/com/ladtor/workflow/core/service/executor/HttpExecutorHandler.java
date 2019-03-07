package com.ladtor.workflow.core.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.bo.execute.HttpExecuteInfo;
import com.ladtor.workflow.core.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author liudongrong
 * @date 2019/1/12 16:50
 */
@Service
@Slf4j
class HttpExecutorHandler extends AbstractExecutorHandler<HttpExecuteInfo> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Executor executor;

    public static final String NAME = "HTTP";

    public HttpExecutorHandler() {
        super(NAME);
    }

    public HttpExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void preExecute(HttpExecuteInfo executeInfo) {
        JSONObject params = executeInfo.getParams();
        JSONObject requestParams = executeInfo.getRequestParams();
        if (params == null) {
            params = new JSONObject();
        }
        if (requestParams == null) {
            requestParams = new JSONObject();
        }
        params.putAll(requestParams);
        executeInfo.setParams(params);
    }

    @Override
    protected void doExecute(HttpExecuteInfo executeInfo) {
        String errorMessage = null;
        JSONObject params = executeInfo.getParams();
        String method = executeInfo.getMethod();
        String url = executeInfo.getUrl() + queryString(params);
        try {
            JSONObject result = null;
            if (HttpMethod.GET.matches(method)) {
                result = restTemplate.getForObject(url, JSONObject.class, params);
            } else if (HttpMethod.POST.matches(method)) {
                result = restTemplate.postForObject(url, params, JSONObject.class, params);
            }
//            JSONObject result = httpClientTemplate.execute(method, executeInfo.getUrl(), params);
            ExecuteResult executeResult = buildExecuteResult(executeInfo, result);
            executor.success(executeResult);
        } catch (ClientException e) {
            errorMessage = e.getMessage();
        } catch (RestClientException e) {
            log.warn("curl fail {}", url, e);
            errorMessage = e.getClass().getSimpleName();
        }
        if (errorMessage != null) {
            JSONObject result = new JSONObject();
            result.put("message", errorMessage);
            ExecuteResult executeResult = buildExecuteResult(executeInfo, result);
            executor.fail(executeResult);
        }
    }

    private String queryString(JSONObject params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.length() == 0 ? "" : "?" + sb.toString().substring(1);
    }

    @Override
    protected void doSuccess(ExecuteResult executeResult) {

    }

    @Override
    protected void doFail(ExecuteResult executeResult) {

    }

    @Override
    protected boolean doCancel(HttpExecuteInfo executeInfo) {
        return false;
    }
}

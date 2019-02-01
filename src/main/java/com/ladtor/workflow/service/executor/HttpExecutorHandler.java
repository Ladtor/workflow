package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.execute.ExecuteResult;
import com.ladtor.workflow.bo.execute.HttpExecuteInfo;
import com.ladtor.workflow.exception.HttpClientException;
import com.ladtor.workflow.service.HttpClientTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 16:50
 */
@Service
@Slf4j
class HttpExecutorHandler extends AbstractExecutorHandler<HttpExecuteInfo> {

    @Autowired
    private HttpClientTemplate httpClientTemplate;

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
        try {
            JSONObject result = httpClientTemplate.execute(executeInfo.getMethod(), executeInfo.getUrl(), executeInfo.getParams());
            ExecuteResult executeResult = buildExecuteResult(executeInfo, result);
            if(result.getInteger(HttpClientTemplate.STATUS_CODE) < 400){
                executor.success(executeResult);
            }else {
                executor.fail(executeResult);
            }
        } catch (HttpClientException e) {
            log.warn("curl fail {}", executeInfo.getUrl(), e);
            JSONObject result = new JSONObject();
            result.put("message", e.getMessage());
            ExecuteResult executeResult = buildExecuteResult(executeInfo, result);
            executor.fail(executeResult);
        }
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

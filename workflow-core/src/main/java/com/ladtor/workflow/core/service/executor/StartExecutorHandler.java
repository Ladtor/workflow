package com.ladtor.workflow.core.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import com.ladtor.workflow.core.bo.execute.StartExecuteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liudongrong
 * @date 2019/1/13 10:53
 */
@Service
public class StartExecutorHandler extends AbstractExecutorHandler<StartExecuteInfo> {

    public static final String NAME = "START";

    @Autowired
    private Executor executor;


    public StartExecutorHandler() {
        super(NAME);
    }

    public StartExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void preExecute(StartExecuteInfo executeInfo) {
        JSONObject params = executeInfo.getParams();
        if (params == null) {
            params = new JSONObject();
        }
        params.put("START_AT", new Date());
        params.putAll(executeInfo.getInitParams());
        executeInfo.setParams(params);
    }

    @Override
    protected void doExecute(StartExecuteInfo executeInfo) {
        executor.success(buildExecuteResult(executeInfo, executeInfo.getParams()));
    }

    @Override
    protected void doSuccess(ExecuteResult executeResult) {

    }

    @Override
    protected void doFail(ExecuteResult executeResult) {

    }

    @Override
    protected boolean doCancel(StartExecuteInfo executeInfo) {
        return false;
    }
}

package com.ladtor.workflow.service.executor;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.execute.StartExecuteInfo;
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
    protected void doExecute(StartExecuteInfo executeInfo) {
        JSONObject result = executeInfo.getResult();
        result.put("START_AT", new Date());
        executor.success(executeInfo);
    }

    @Override
    protected void doSuccess(StartExecuteInfo executeInfo) {

    }

    @Override
    protected void doFail(StartExecuteInfo executeInfo) {

    }

    @Override
    protected boolean doCancel(StartExecuteInfo executeInfo) {
        return false;
    }
}

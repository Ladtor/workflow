package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.ResultExecuteInfo;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/13 15:20
 */
@Service
public class ResultExecuteHandler extends AbstractExecutorHandler<ResultExecuteInfo> {

    public static final String NAME = "RESULT";

    public ResultExecuteHandler() {
        super(NAME);
    }

    public ResultExecuteHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(ResultExecuteInfo executeInfo) {

    }

    @Override
    protected void doSuccess(ResultExecuteInfo executeInfo) {

    }

    @Override
    protected void doFail(ResultExecuteInfo executeInfo) {

    }

    @Override
    protected boolean doCancel(ResultExecuteInfo executeInfo) {
        return false;
    }
}

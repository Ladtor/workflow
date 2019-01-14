package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.HttpExecuteInfo;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 16:50
 */
@Service
class HttpExecutorHandler extends AbstractExecutorHandler<HttpExecuteInfo> {

    public static final String NAME = "HTTP";

    public HttpExecutorHandler() {
        super(NAME);
    }

    public HttpExecutorHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(HttpExecuteInfo executeInfo) {

    }

    @Override
    protected void doSuccess(HttpExecuteInfo executeInfo) {

    }

    @Override
    protected void doFail(HttpExecuteInfo executeInfo) {

    }

    @Override
    protected boolean doCancel(HttpExecuteInfo executeInfo) {
        return false;
    }
}

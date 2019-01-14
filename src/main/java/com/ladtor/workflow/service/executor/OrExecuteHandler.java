package com.ladtor.workflow.service.executor;

import com.ladtor.workflow.bo.execute.OrExecuteInfo;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/13 22:49
 */
@Service
public class OrExecuteHandler extends AbstractExecutorHandler<OrExecuteInfo> {

    public static final String NAME = "OR";

    public OrExecuteHandler() {
        super(NAME);
    }

    public OrExecuteHandler(String name) {
        super(name);
    }

    @Override
    protected void doExecute(OrExecuteInfo executeInfo) {

    }

    @Override
    protected void doSuccess(OrExecuteInfo executeInfo) {

    }

    @Override
    protected void doFail(OrExecuteInfo executeInfo) {

    }

    @Override
    protected boolean doCancel(OrExecuteInfo executeInfo) {
        return false;
    }
}

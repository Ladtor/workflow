package com.ladtor.workflow.core.service.executor.listener;

import com.alibaba.fastjson.JSON;
import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.core.bo.execute.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/12 22:24
 */
@Service
@Slf4j
public class LogExecutorListener implements ExecutorListener {
    @Override
    public void before(String executorName, ExecuteInfo executeInfo) {
        log.info("executor {} before {} ", executorName, JSON.toJSONString(executeInfo));
    }

    @Override
    public void complete(String executorName, ExecuteResult executeResult) {
        log.info("executor {} complete {} ", executorName, JSON.toJSONString(executeResult));
    }

    @Override
    public void success(String executorName, ExecuteResult executeResult) {
        log.info("executor {} success {} ", executorName, JSON.toJSONString(executeResult));
    }

    @Override
    public void fail(String executorName, ExecuteResult executeResult) {
        log.info("executor {} fail {} ", executorName, JSON.toJSONString(executeResult));
    }

    @Override
    public void cancel(String executorName, ExecuteInfo executeInfo) {
        log.info("executor {} cancel {} ", executorName, JSON.toJSONString(executeInfo));
    }

}

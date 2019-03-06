package com.ladtor.workflow.core.service.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.common.constant.StatusEnum;
import com.ladtor.workflow.dao.ExecuteLogService;
import com.ladtor.workflow.dao.domain.ExecuteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExecuteLogWrapper {
    @Autowired
    private ExecuteLogService executeLogService;

    public boolean save(ThreeTuple threeTuple, StatusEnum status, JSONObject params, JSONObject result) {
        ExecuteLog executeLog = ExecuteLog.builder()
                .serialNo(threeTuple.getSerialNo())
                .version(threeTuple.getVersion())
                .runVersion(threeTuple.getRunVersion())
                .status(status.toString())
                .build();
        if (params != null) {
            executeLog.setParams(params.toJSONString());
        }
        if (result != null) {
            executeLog.setResult(result.toJSONString());
        }
        return executeLogService.save(executeLog);
    }

    public void success(ThreeTuple threeTuple, JSONObject result) {
        update(threeTuple, StatusEnum.SUCCESS, result);
    }

    public void fail(ThreeTuple threeTuple, JSONObject result) {
        update(threeTuple, StatusEnum.FAIL, result);
    }

    public boolean update(ThreeTuple threeTuple, StatusEnum status, JSONObject result) {
        ExecuteLog executeLog = executeLogService.get(threeTuple);
        executeLog.setStatus(status.toString());
        executeLog.setResult(result.toJSONString());
        executeLog.setUpdatedAt(new Date());
        return executeLogService.update(executeLog);
    }
}

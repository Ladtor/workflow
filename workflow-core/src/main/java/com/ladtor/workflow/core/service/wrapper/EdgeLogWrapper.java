package com.ladtor.workflow.core.service.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.common.constant.StatusEnum;
import com.ladtor.workflow.dao.EdgeLogService;
import com.ladtor.workflow.dao.domain.EdgeLog;
import com.ladtor.workflow.dao.domain.ExecuteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EdgeLogWrapper {
    @Autowired
    private EdgeLogService edgeLogService;

    public boolean saveOrUpdate(ThreeTuple threeTuple, String edgeId, String source, String target, boolean result, JSONObject params) {
        EdgeLog edgeLog =EdgeLog.builder()
                .serialNo(threeTuple.getSerialNo())
                .version(threeTuple.getVersion())
                .runVersion(threeTuple.getRunVersion())
                .edgeId(edgeId)
                .source(source)
                .target(target)
                .result(result)
                .params(params.toJSONString())
                .build();
        return edgeLogService.saveOrUpdate(edgeLog);
    }

}

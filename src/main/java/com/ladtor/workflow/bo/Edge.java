package com.ladtor.workflow.bo;

import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.bo.execute.ThreeTuple;
import com.ladtor.workflow.service.EdgeLogService;
import com.ladtor.workflow.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;

/**
 * @author liudongrong
 * @date 2019/1/12 19:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edge{
    private String id;
    private String source;
    private String target;
    private String condition;

    public boolean run(ThreeTuple threeTuple, JSONObject params) {
        boolean result = true;
        if (StringUtils.isNotEmpty(condition)) {
            result = MVEL.evalToBoolean(condition, params);
        }
        EdgeLogService edgeLogService = BeanUtil.getBean(EdgeLogService.class);
        edgeLogService.saveOrUpdate(threeTuple, id, source, target, result, params);
        return result;
    }
}

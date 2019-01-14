package com.ladtor.workflow.bo.execute;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/13 10:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartExecuteInfo extends ExecuteInfo {
    private JSONObject initParams;
}

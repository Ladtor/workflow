package com.ladtor.workflow.core.bo.execute;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/13 10:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpExecuteInfo extends ExecuteInfo {
    private String url;
    private String method;
    private JSONObject requestParams;
}

package com.ladtor.workflow.bo.execute;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 21:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteResult extends JSONObject {
    private String result;
}

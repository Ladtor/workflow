package com.ladtor.workflow.bo.execute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 21:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowExecuteInfo extends ExecuteInfo {
    private String subSerialNo;
}

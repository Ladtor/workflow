package com.ladtor.workflow.bo.execute;

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
public class TaskExecuteInfo extends ExecuteInfo {
    private String taskNodeKey;
    private String taskKey;
}

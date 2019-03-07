package com.ladtor.workflow.core.bo.trigger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 22:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CronTriggerInfo extends BaseTriggerInfo {
    private String cronText;
    private Integer times;
}

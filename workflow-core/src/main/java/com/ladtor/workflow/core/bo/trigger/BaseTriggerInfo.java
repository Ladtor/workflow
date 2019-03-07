package com.ladtor.workflow.core.bo.trigger;

import com.ladtor.workflow.common.constant.TriggerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/12 22:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseTriggerInfo {
    private TriggerType triggerType;
}

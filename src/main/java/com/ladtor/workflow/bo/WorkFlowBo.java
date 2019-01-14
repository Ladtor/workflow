package com.ladtor.workflow.bo;

import com.ladtor.workflow.bo.domain.WorkFlow;
import com.ladtor.workflow.service.WorkFlowService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liudongrong
 * @date 2019/1/12 19:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowBo {
    private String serialNo;
    private String name;
    private Integer version;
    private GraphBo graph;

    @Autowired
    private WorkFlowService workFlowService;

    @Transactional
    public Integer createRunVersion() {
        WorkFlow workFlow = workFlowService.get(serialNo);
        workFlow.setRunVersion(workFlow.getRunVersion() + 1);
        workFlowService.update(workFlow);
        return workFlow.getRunVersion();
    }
}

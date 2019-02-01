package com.ladtor.workflow.service.chain;

import com.ladtor.workflow.bo.execute.FourTuple;
import lombok.Setter;

/**
 * @author liudongrong
 * @date 2019/1/28 14:01
 */
public class NodeCheckHandler {
    @Setter
    private AbstractNodeCheck nodeCheck;

    public NodeCheckHandler(AbstractNodeCheck nodeCheck) {
        this.nodeCheck = nodeCheck;
    }

    public boolean canRun(FourTuple fourTuple){
        return nodeCheck.canRun(fourTuple);
    }
}

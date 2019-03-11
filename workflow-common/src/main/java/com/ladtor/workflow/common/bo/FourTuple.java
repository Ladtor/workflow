package com.ladtor.workflow.common.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liudongrong
 * @date 2019/1/31 19:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FourTuple extends ThreeTuple {
    private String nodeId;

    public FourTuple() {
    }

    public FourTuple(String serialNo, Integer version, Integer runVersion, String nodeId) {
        super(serialNo, version, runVersion);
        this.nodeId = nodeId;
    }

    public FourTuple(ThreeTuple threeTuple, String nodeId) {
        super(threeTuple.getSerialNo(), threeTuple.getVersion(), threeTuple.getRunVersion());
        this.nodeId = nodeId;
    }
}

package com.ladtor.workflow.bo.execute;

import lombok.Data;

/**
 * @author liudongrong
 * @date 2019/1/31 19:09
 */
@Data
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

package com.ladtor.workflow.common.bo;

import lombok.Data;

/**
 * @author liudongrong
 * @date 2019/1/31 19:01
 */
@Data
public class ThreeTuple extends TwoTuple {
    private Integer runVersion;

    public ThreeTuple() {
    }

    public ThreeTuple(TwoTuple twoTuple, Integer runVersion) {
        super(twoTuple.getSerialNo(), twoTuple.getVersion());
        this.runVersion = runVersion;
    }

    public ThreeTuple(String serialNo, Integer version, Integer runVersion) {
        super(serialNo, version);
        this.runVersion = runVersion;
    }
}

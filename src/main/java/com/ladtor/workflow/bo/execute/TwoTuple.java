package com.ladtor.workflow.bo.execute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liudongrong
 * @date 2019/1/31 19:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoTuple {
    private String serialNo;
    private Integer version;
}

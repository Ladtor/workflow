package com.ladtor.workflow.bo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liudongrong
 * @date 2019/1/13 21:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteLog {
    private Integer id;
    private Integer graphId;
    private String serialNo;
    private Integer version;
    private Integer runVersion;
    private String target;
    private String source;
    private String status;
    private String params;
    private String result;
    private Date createdAt;
    private Date updatedAt;
}

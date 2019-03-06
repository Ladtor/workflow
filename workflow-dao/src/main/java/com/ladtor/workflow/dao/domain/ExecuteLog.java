package com.ladtor.workflow.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liudongrong
 * @date 2019/1/15 14:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteLog {
    private Integer id;
    private String serialNo;
    private Integer version;
    private Integer runVersion;
    private String status;
    private String params;
    private String result;
    private Date createdAt;
    private Date updatedAt;
}

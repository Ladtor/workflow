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
public class EdgeLog {
    private Integer id;
    private String serialNo;
    private Integer version;
    private Integer runVersion;
    private String edgeId;
    private String source;
    private String target;
    private Boolean result;
    private String params;
    private Date createdAt;
    private Date updatedAt;
}

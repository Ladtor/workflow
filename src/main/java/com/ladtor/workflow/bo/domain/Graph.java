package com.ladtor.workflow.bo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liudongrong
 * @date 2019/1/13 20:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Graph {
    private Integer id;
    private String serialNo;
    private Integer version;
    private String data;
    private Date createdAt;
    private Date updatedAt;
}

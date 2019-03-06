package com.ladtor.workflow.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liudongrong
 * @date 2019/2/7 20:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;
    private String taskKey;
    private String name;
    private String node;
    private Date createdAt;
    private Date updatedAt;
}

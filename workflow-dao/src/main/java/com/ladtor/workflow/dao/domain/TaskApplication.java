package com.ladtor.workflow.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskApplication {
    private Integer id;
    private String name;
    private String url;
    private Date createdAt;
    private Date updatedAt;
}

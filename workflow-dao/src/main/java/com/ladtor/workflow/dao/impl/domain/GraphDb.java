package com.ladtor.workflow.dao.impl.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("graph")
public class GraphDb {
    private Integer id;
    private String serialNo;
    private Integer version;
    private String data;
    private Date createdAt;
    private Date updatedAt;
}

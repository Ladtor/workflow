package com.ladtor.workflow.dao.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private List<JSONObject> nodes;
    private List<JSONObject> edges;
    private Date createdAt;
    private Date updatedAt;
}

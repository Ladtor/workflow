package com.ladtor.workflow.util.serializer;

import com.alibaba.fastjson.JSON;
import com.ladtor.workflow.bo.GraphBo;
import org.springframework.stereotype.Component;

/**
 * @author liudongrong
 * @date 2019/1/14 23:15
 */
@Component
public class GraphSerializer {
    public String serialize(GraphBo graph){
        return JSON.toJSONString(graph);
    }

    public GraphBo deserialize(String graph){
        return JSON.parseObject(graph, GraphBo.class);
    }
}

package com.ladtor.workflow.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.Edge;
import com.ladtor.workflow.bo.GraphBo;
import com.ladtor.workflow.bo.Node;
import com.ladtor.workflow.bo.domain.Graph;
import com.ladtor.workflow.bo.req.GraphReq;
import com.ladtor.workflow.bo.req.NodeReq;
import com.ladtor.workflow.mapper.GraphMapper;
import org.apache.commons.compress.utils.Lists;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/14 22:33
 */
@Service
@CacheConfig(cacheNames = "graph")
public class GraphService extends ServiceImpl<GraphMapper, Graph> {

    @CacheEvict(key = "#serialNo + #version")
    public void save(String serialNo, Integer version, GraphReq graphReq) {
        this.save(Graph.builder()
                .data(JSON.toJSONString(graphReq))
                .serialNo(serialNo)
                .version(version)
                .build());
    }

    @CacheEvict(key = "#serialNo + #version")
    public void update(String serialNo, Integer version, GraphReq graphReq) {
        QueryWrapper<Graph> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo).eq("version", version);

        this.update(Graph.builder()
                .data(JSON.toJSONString(graphReq))
                .serialNo(serialNo)
                .version(version)
                .build(), wrapper);
    }

    @Cacheable(cacheNames = "graph", key = "#serailNo + #version")
    public Graph get(String serialNo, Integer version) {
        QueryWrapper<Graph> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo).eq("version", version);
        return this.getOne(wrapper);
    }

    public GraphReq getReq(String serialNo, Integer version) {
        Graph graph = get(serialNo, version);
        GraphReq graphReq = JSON.parseObject(graph.getData(), GraphReq.class);
        return graphReq;
    }

    public GraphBo getBo(String serialNo, Integer version){
        GraphReq graphReq = getReq(serialNo, version);
        return GraphBo.builder()
                .serialNo(serialNo)
                .version(version)
                .nodes(parseNode(graphReq.getNodes()))
                .edges(parseList(graphReq.getEdges(), Edge.class))
                .build();
    }

    private List<Node> parseNode(List<NodeReq> nodes) {
        List<Node> nodeList = parseList(nodes, Node.class);
        if (nodeList == null) {
            return Lists.newArrayList();
        }
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setExtra(nodes.get(i));
        }
        return nodeList;
    }

    private <T> List<T> parseList(List list, Class<T> clazz) {
        List<T> ts = JSON.parseArray(JSON.toJSONString(list), clazz);
        if (ts == null) {
            return Lists.newArrayList();
        }
        return ts;
    }
}

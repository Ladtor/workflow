package com.ladtor.workflow.dao.impl.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.dao.GraphService;
import com.ladtor.workflow.dao.domain.Graph;
import com.ladtor.workflow.dao.impl.domain.GraphDb;
import com.ladtor.workflow.dao.impl.mapper.GraphMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "graph")
public class GraphServiceImpl extends ServiceImpl<GraphMapper, GraphDb> implements GraphService {

    @CacheEvict(key = "#graph.serialNo + #graph.version")
    @Override
    public boolean save(Graph graph) {
        return super.save(convert(graph));
    }

    @CacheEvict(key = "#graph.serialNo + #graph.version")
    public void update(Graph graph) {
        super.updateById(convert(graph));
    }

    @Cacheable(key = "#serialNo + #version")
    public Graph get(String serialNo, Integer version) {
        QueryWrapper<GraphDb> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo).eq("version", version);
        return convert(super.getOne(wrapper));
    }


    private Graph convert(GraphDb graphDb){
        Graph graph = JSON.parseObject(graphDb.getData(), Graph.class);
        graph.setId(graphDb.getId());
        graph.setSerialNo(graphDb.getSerialNo());
        graph.setVersion(graphDb.getVersion());
        graph.setCreatedAt(graphDb.getCreatedAt());
        graph.setUpdatedAt(graphDb.getUpdatedAt());
        return graph;
    }

    private GraphDb convert(Graph graph){
        return GraphDb.builder()
                .id(graph.getId())
                .serialNo(graph.getSerialNo())
                .version(graph.getVersion())
                .updatedAt(graph.getUpdatedAt())
                .createdAt(graph.getCreatedAt())
                .data(JSON.toJSONString(graph))
                .build();
    }
}

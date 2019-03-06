package com.ladtor.workflow.core.service.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ladtor.workflow.core.bo.Edge;
import com.ladtor.workflow.core.bo.GraphBo;
import com.ladtor.workflow.core.bo.Node;
import com.ladtor.workflow.dao.GraphService;
import com.ladtor.workflow.dao.domain.Graph;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GraphWrapper {
    @Autowired
    private GraphService graphService;

    public void save(String serialNo, Integer version, Graph graph) {
        graph.setSerialNo(serialNo);
        graph.setVersion(version);
        graphService.save(graph);
    }

    public void update(String serialNo, Integer version, List<JSONObject> nodes, List<JSONObject> edges) {
        Graph graph = graphService.get(serialNo, version);
        graph.setNodes(nodes);
        graph.setEdges(edges);
        graph.setUpdatedAt(new Date());
        graphService.update(graph);
    }

    public GraphBo getBo(String serialNo, Integer version){
        Graph graph = graphService.get(serialNo, version);
        return GraphBo.builder()
                .serialNo(serialNo)
                .version(version)
                .nodes(parseNode(graph.getNodes()))
                .edges(parseEdge(graph.getEdges()))
                .build();
    }

    private List<Edge> parseEdge(List<JSONObject> edges) {
        return parseList(edges, Edge.class);
    }

    private List<Node> parseNode(List<JSONObject> nodes) {
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

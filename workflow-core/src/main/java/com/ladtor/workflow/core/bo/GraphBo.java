package com.ladtor.workflow.core.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ladtor.workflow.core.bo.execute.ExecuteInfo;
import com.ladtor.workflow.common.bo.ThreeTuple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liudongrong
 * @date 2019/1/12 19:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphBo {
    public static final String START_ID = "00000000";
    public static final String RESULT_ID = "ffffffff";
    private String serialNo;
    private Integer version;
    private List<Node> nodes;
    private List<Edge> edges;

    public List<Edge> getSourceEdges(Node node) {
        return edges.stream().filter(edge -> edge.getSource().equals(node.getId())).collect(Collectors.toList());
    }

    public List<Edge> getTargetEdges(Node node) {
        return edges.stream().filter(edge -> edge.getTarget().equals(node.getId())).collect(Collectors.toList());
    }

    public Node getSourceNode(Edge edge) {
        return nodes.stream().filter(node -> node.getId().equals(edge.getSource())).findFirst().orElse(null);
    }

    public Node getTargetNode(Edge edge) {
        return nodes.stream().filter(node -> node.getId().equals(edge.getTarget())).findFirst().orElse(null);
    }

    @JSONField(serialize = false)
    public Node getStartNode() {
        return getNode(START_ID);
    }

    @JSONField(serialize = false)
    public Node getResultNode() {
        return getNode(RESULT_ID);
    }

    public List<Node> getTargetNodes(Node sourceNode) {
        return getSourceEdges(sourceNode).stream().map(this::getTargetNode).collect(Collectors.toList());
    }

    public ExecuteInfo getExecuteInfo(Node node, Integer runVersion) {
        return node.getExecuteInfo(new ThreeTuple(serialNo, version, runVersion));
    }

    public Node getNode(String nodeId) {
        return nodes.stream().filter(node -> node.getId().equals(nodeId)).findFirst().orElse(null);
    }
}

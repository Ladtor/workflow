package com.ladtor.workflow.bo;

import com.ladtor.workflow.bo.execute.ExecuteInfo;
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

    public Node getStartNode() {
        return nodes.stream().filter(node -> node.getId().equals(START_ID)).findFirst().orElse(null);
    }

    public Node getResultNode() {
        return nodes.stream().filter(node -> node.getId().equals(RESULT_ID)).findFirst().orElse(null);
    }

    public List<Node> getTargetNodes(Node sourceNode) {
        return getSourceEdges(sourceNode).stream().map(this::getTargetNode).collect(Collectors.toList());
    }

    public ExecuteInfo getExecuteInfo(Node node, Integer runVersion) {
        ExecuteInfo executeInfo = node.getExecuteInfo(runVersion);
        if (executeInfo != null) {
            executeInfo.setSerialNo(serialNo);
            executeInfo.setVersion(version);
        }
        return executeInfo;
    }
}

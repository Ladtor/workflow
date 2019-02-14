package com.ladtor.workflow.bo;

import com.ladtor.workflow.bo.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/2/7 20:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskNode {
    private String name;
    private List<Task> taskList;
}

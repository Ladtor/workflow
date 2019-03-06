package com.ladtor.workflow.dao;

import com.ladtor.workflow.dao.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/2/7 21:07
 */
@Service
public interface TaskService{

    List<Task> selectByNodeName(String nodeName);

    boolean save(Task task);

    List<Task> list();
}

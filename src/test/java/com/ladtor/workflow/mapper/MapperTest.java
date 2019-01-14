package com.ladtor.workflow.mapper;

import com.ladtor.workflow.WorkflowApplicationTests;
import com.ladtor.workflow.bo.domain.ExecuteLog;
import com.ladtor.workflow.bo.domain.Graph;
import com.ladtor.workflow.bo.domain.WorkFlow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/14 13:01
 */
public class MapperTest extends WorkflowApplicationTests {

    @Autowired
    private WorkFlowMapper workFlowMapper;
    @Autowired
    private GraphMapper graphMapper;
    @Autowired
    private ExecuteLogMapper executeLogMapper;
    
    @Test
    public void test(){
        WorkFlow workFlow = workFlowMapper.selectOne(null);
        Graph graph = graphMapper.selectOne(null);
        ExecuteLog executeLog = executeLogMapper.selectOne(null);
    }
}
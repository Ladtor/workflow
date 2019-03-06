package com.ladtor.workflow.dao.impl.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ladtor.workflow.dao.*;
import com.ladtor.workflow.dao.impl.service.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudongrong
 * @date 2019/1/14 12:52
 */
@Configuration
@MapperScan("com.ladtor.workflow.dao.impl.mapper")
public class MyBatisPlusConfig {
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(EdgeLogService.class)
    public EdgeLogService edgeLogService(){
        return new EdgeLogServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ExecuteLogService.class)
    public ExecuteLogService executeLogService(){
        return new ExecuteLogServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(GraphService.class)
    public GraphService graphService(){
        return new GraphServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(NodeLogService.class)
    public NodeLogService nodeLogService(){
        return new NodeLogServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TaskService.class)
    public TaskService taskService(){
        return new TaskServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(WorkFlowService.class)
    public WorkFlowService workFlowService(){
        return new WorkFlowServiceImpl();
    }
}

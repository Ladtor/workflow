package com.ladtor.workflow.api.aop;

import com.ladtor.workflow.dao.domain.NodeLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Component
@Aspect
public class NodeUpdateListener {
    public static final String NODE_LOG = "/nodeLog";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Pointcut("execution(* com.ladtor.workflow.dao.NodeLogService+.*(..))")
    public void nodeUpdate() {
    }

    @AfterReturning("nodeUpdate()")
    public void afterReturn(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        if(arg instanceof NodeLog){
            NodeLog nodeLog = (NodeLog) arg;
            simpMessagingTemplate.convertAndSend(NODE_LOG, nodeLog);
        }
    }
}

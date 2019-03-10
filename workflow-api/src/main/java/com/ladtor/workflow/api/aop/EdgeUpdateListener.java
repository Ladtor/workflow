package com.ladtor.workflow.api.aop;

import com.ladtor.workflow.dao.domain.EdgeLog;
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
public class EdgeUpdateListener {
    public static final String EDGE_LOG = "/edgeLog";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Pointcut("execution(* com.ladtor.workflow.dao.EdgeLogService+.saveOrUpdate(..))")
    public void edgeUpdate(){}

    @AfterReturning("edgeUpdate()")
    public void afterReturn(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        EdgeLog edgeLog = (EdgeLog) arg;
        simpMessagingTemplate.convertAndSend(EDGE_LOG, edgeLog);
    }
}

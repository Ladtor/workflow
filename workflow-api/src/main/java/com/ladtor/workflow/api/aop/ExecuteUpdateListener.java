package com.ladtor.workflow.api.aop;


import com.ladtor.workflow.dao.domain.ExecuteLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExecuteUpdateListener {

    public static final String EXECUTE_LOG = "/executeLog";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Pointcut("execution(* com.ladtor.workflow.dao.ExecuteLogService+.*(..))")
    public void executeUpdate() {
    }

    @AfterReturning("executeUpdate()")
    public void afterReturn(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        if(arg instanceof ExecuteLog){
            ExecuteLog executeLog = (ExecuteLog) arg;
            simpMessagingTemplate.convertAndSend(EXECUTE_LOG, executeLog);
        }
    }
}

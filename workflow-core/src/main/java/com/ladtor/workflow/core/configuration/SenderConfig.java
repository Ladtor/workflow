package com.ladtor.workflow.core.configuration;

import com.ladtor.workflow.core.service.sender.HttpSender;
import com.ladtor.workflow.core.service.sender.Sender;
import com.ladtor.workflow.dao.TaskApplicationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author liudongrong
 * @date 2019/2/6 11:50
 */
@Configuration
public class SenderConfig {
    @Bean
    @ConditionalOnMissingBean
    public Sender sender(RestTemplate restTemplate, TaskApplicationService taskApplicationService){
        return new HttpSender(restTemplate, taskApplicationService);
    }
}

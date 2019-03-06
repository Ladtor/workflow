package com.ladtor.workflow.core.configuration;

import com.ladtor.workflow.core.service.HttpClientTemplate;
import com.ladtor.workflow.core.service.sender.HttpSender;
import com.ladtor.workflow.core.service.sender.Sender;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudongrong
 * @date 2019/2/6 11:50
 */
@Configuration
public class SenderConfig {
    @Bean
    @ConditionalOnMissingBean
    public Sender sender(HttpClientTemplate httpClientTemplate, AdminServerProperties adminServerProperties, ServerProperties serverProperties){
        return new HttpSender(httpClientTemplate, adminServerProperties, serverProperties);
    }
}

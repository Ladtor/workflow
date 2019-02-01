package com.ladtor.workflow.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudongrong
 * @date 2019/1/30 15:54
 */
@Configuration
public class HttpClientConfig {
    @Bean
    public HttpClient httpClient(){
        return HttpClients.createDefault();
    }
}

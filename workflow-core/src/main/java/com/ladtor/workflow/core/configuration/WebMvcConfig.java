package com.ladtor.workflow.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;

/**
 * @author liudongrong
 * @date 2019/1/19 12:53
 */
@Configuration
public class WebMvcConfig {
    @Bean
    public FormContentFilter formContentFilter() {
        return new FormContentFilter();
    }
}

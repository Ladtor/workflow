package com.ladtor.workflow.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author liudongrong
 * @date 2019/1/19 12:53
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig {
    @Bean
    public FormContentFilter formContentFilter() {
        return new FormContentFilter();
    }
}

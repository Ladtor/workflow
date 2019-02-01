package com.ladtor.workflow.configuration;

import com.ladtor.workflow.service.chain.FailNodeCheck;
import com.ladtor.workflow.service.chain.HasBeanRunNodeCheck;
import com.ladtor.workflow.service.chain.NodeCheckHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liudongrong
 * @date 2019/1/28 14:25
 */
@Configuration
public class NodeCheckChainConfig {
    @Bean
    public NodeCheckHandler nodeCheckHandler(HasBeanRunNodeCheck hasBeanRunNodeCheck, FailNodeCheck failNodeCheck){
        hasBeanRunNodeCheck.setNext(failNodeCheck);
        return new NodeCheckHandler(hasBeanRunNodeCheck);
    }
}

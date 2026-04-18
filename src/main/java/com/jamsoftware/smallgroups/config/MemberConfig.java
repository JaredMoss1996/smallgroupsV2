package com.jamsoftware.smallgroups.config;

import com.jamsoftware.smallgroups.model.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    Member currentMember() {
        return null;
    }
}

package com.serven.zsb.configuration;

import com.serven.zsb.runner.ZSBRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/18
 */
@Configuration
@SpringBootApplication
public class ZSBAutoConfiguration {

    public ZSBAutoConfiguration() {

    }

    @Bean
    public ZSBRunner zsbRunner (){
        return new ZSBRunner();
    }
}

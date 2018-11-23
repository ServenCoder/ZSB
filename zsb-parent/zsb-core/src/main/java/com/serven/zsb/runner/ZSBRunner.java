package com.serven.zsb.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/18
 */
@Slf4j
public class ZSBRunner implements CommandLineRunner {

    public ZSBRunner() {}

    @Override
    public void run(String... args) throws Exception {
        log.info("zjs-core starting...");
    }
}

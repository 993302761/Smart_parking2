package com.example.administrators;

import com.example.administrators.serviceImpl.AdministratorsServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ModuleRegisterApplicationTests {
    private static final Logger LOGGER= LoggerFactory.getLogger(AdministratorsServiceImpl.class);

    @Test
    void contextLoads() {
        // 日志消息输出
//        LOGGER.fatal("打印fatal");
        LOGGER.error("打印error");
        LOGGER.warn("打印warn");
        LOGGER.info("打印info");
        LOGGER.debug("打印debug");
        LOGGER.trace("打印trace");
    }

}

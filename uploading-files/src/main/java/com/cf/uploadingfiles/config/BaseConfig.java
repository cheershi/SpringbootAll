package com.cf.uploadingfiles.config;

import com.cf.uploadingfiles.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：fengchen
 * @date ：Created in 2019/8/6 10:43
 * @description：配置
 * @version:
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class BaseConfig {

    /**
     * 项目启动时 清空上传的文件夹 并创建
     * @param storageService
     * @return
     */
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}

package com.flycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 *
 * @author FLY
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
//spring-boot 启动
@SpringBootApplication(scanBasePackages = "com.flycode")
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
//打包成war部署tomcat
/*@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}*/


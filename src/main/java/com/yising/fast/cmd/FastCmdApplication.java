package com.yising.fast.cmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 应用
 *
 * @author yising
 */
@EnableJpaAuditing
@SpringBootApplication
public class FastCmdApplication {
	public static void main(String[] args) {
		SpringApplication.run(FastCmdApplication.class, args);
	}

}

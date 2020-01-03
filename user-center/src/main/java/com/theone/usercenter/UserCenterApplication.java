package com.theone.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.theone.usercenter.dao")
@ComponentScan(basePackages = {"com.theone"})
@SpringBootApplication
@EnableBinding(Sink.class)
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}

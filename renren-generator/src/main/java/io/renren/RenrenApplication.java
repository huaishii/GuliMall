package io.renren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@MapperScan("io.renren.dao")
public class RenrenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenrenApplication.class, args);

		StringBuilder

		ReentrantLock reentrantLock = new ReentrantLock();
		Object o = new Object();
		synchronized (o){

		}
	}
}

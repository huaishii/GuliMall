package com.ljl.gulimall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * openFeign:
 *  想要远程调用别的服务
 *  1.引入openFeign
 *  2.编写一个接口，告诉springCloud这个接口需要调用远程服务
 *      2.1.声名接口的每一个方法都是调用那个远程服务的那个请求
 *  3.开启远程调用功能
 */

@EnableFeignClients(basePackages = "com.ljl.gulimall.member.feign")
@EnableDiscoveryClient
@MapperScan("com/ljl/gulimall/member/dao")
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}

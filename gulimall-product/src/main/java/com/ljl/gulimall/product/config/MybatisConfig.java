package com.ljl.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("com.ljl.gulimall.product.dao")
public class MybatisConfig {

    //引入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置请求页大于最大页操作，true调回首页，false继续请求，默认false
        paginationInterceptor.setOverflow(true);
        //设置没页最大限制数量默认500，-1 不限制
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }

}

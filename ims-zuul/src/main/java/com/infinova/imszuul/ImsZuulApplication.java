package com.infinova.imszuul;

import com.infinova.imszuul.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients({"com.infinova.imszuul.service"})
@SpringBootApplication
public class ImsZuulApplication {

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ImsZuulApplication.class, args);
    }

}

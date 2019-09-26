package com.infinova.imseureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ImsEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImsEurekaApplication.class, args);
    }

}

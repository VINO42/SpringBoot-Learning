package io.github.vino42;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAsync
@SpringBootApplication
public class SpringBootDemosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemosApplication.class, args);
    }

}

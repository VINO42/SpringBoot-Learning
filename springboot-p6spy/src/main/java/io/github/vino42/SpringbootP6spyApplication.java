package io.github.vino42;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootP6spyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootP6spyApplication.class, args);
    }

}

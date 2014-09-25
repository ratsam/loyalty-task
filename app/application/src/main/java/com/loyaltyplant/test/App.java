package com.loyaltyplant.test;

import com.loyaltyplant.test.cfg.Config;
import org.springframework.boot.SpringApplication;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class App {

    public static void main(String... args) {
        SpringApplication.run(Config.class, args);
    }
}

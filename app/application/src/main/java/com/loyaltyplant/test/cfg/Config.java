package com.loyaltyplant.test.cfg;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@EnableRetry
@ComponentScan("com.loyaltyplant.test")
public class Config {

}

package com.loyaltyplant.test.cfg;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Configuration
@EnableTransactionManagement
@EntityScan("com.loyaltyplant.test.domain")
@EnableJpaRepositories("com.loyaltyplant.test.repository")
public class JpaConfig {

}

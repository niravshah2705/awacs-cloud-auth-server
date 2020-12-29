package com.aiocdwacs.awacscloudauthserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.aiocdwacs.awacscloudauthserver.model.Authority;
import com.aiocdwacs.awacscloudauthserver.model.User;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.aiocdwacs.awacscloudauthserver.repository",
        entityManagerFactoryRef = "authEntityManagerFactory",
        transactionManagerRef= "authTransactionManager")
public class AuthDatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("auth.datasource")
    public DataSourceProperties authDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("auth.datasource.configuration")
    public DataSource authDataSource() {
        return authDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "authEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(authDataSource())
                .packages(User.class, Authority.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager authTransactionManager(
            final @Qualifier("authEntityManagerFactory") LocalContainerEntityManagerFactoryBean authEntityManagerFactory) {
        return new JpaTransactionManager(authEntityManagerFactory.getObject());
    }

}
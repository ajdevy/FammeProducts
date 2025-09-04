package com.respiroc.products.db
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/famme_db"
            username = "springboot"
            password = "123321"
            driverClassName = "org.postgresql.Driver"

            // Pool settings
            maximumPoolSize = 10
            validate()
        }
        return HikariDataSource(config)
    }
}
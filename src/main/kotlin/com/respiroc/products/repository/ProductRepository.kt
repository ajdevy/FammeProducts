package com.respiroc.products.repository

import com.respiroc.products.domain.entity.Product
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class ProductRepository(private val jdbcClient: JdbcClient) {

    fun findAll(): List<Product> =
        jdbcClient.sql("SELECT id, title FROM products ORDER BY id")
            .query { rs, _ ->
                Product(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    // TODO: add variants
                    variants = emptyList()
                )
            }
            .list()

    fun save(title: String) {
        jdbcClient.sql(
            "INSERT INTO products (title) VALUES (:title) RETURNING id"
        )
            .param("title", title)
            .query()
    }
}
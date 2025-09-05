package com.respiroc.products.repository

import com.respiroc.products.domain.entity.Product
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet


@Repository
class ProductRepository(private val jdbcClient: JdbcClient) {

    fun findAll(): List<Product> =
        jdbcClient.sql("SELECT product_id, title FROM products ORDER BY id")
            .query { resultSet, _ ->
                resultSet.toProduct()
            }
            .list()

    fun save(product: Product) {
        val sql = """
            INSERT INTO products (product_id, title) 
            VALUES (:product_id, :title) 
            ON CONFLICT (product_id)
            DO UPDATE SET
                title = EXCLUDED.title
            """.trimIndent()

        jdbcClient.sql(sql)
            .param("title", product.title)
            .param("product_id", product.id)
            .update()
    }

    fun save(productId: Long, title: String): Product {
        return jdbcClient.sql(
            "INSERT INTO products (product_id, title) VALUES (:product_id, :title) RETURNING id, product_id, title"
        )
            .param("title", title)
            .param("product_id", productId)
            .query { resultSet, _ ->
                resultSet.toProduct()
            }
            .single()
    }

    @Transactional
    fun truncateToLast50Rows() {
        val sql = """
            DELETE FROM products
            WHERE id NOT IN (
                SELECT id FROM products
                ORDER BY id DESC
                LIMIT 50
            )
            
            """.trimIndent()

        jdbcClient.sql(sql).update()
    }

}

private fun ResultSet.toProduct(): Product =
    Product(
        id = getLong("product_id"),
        title = getString("title"),
        // TODO: add variants
        variants = emptyList()
    )
package com.respiroc.products.scheduling

import com.respiroc.products.client.ProductClient
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProductSyncJob(
    private val productClient: ProductClient,
    private val jdbcClient: JdbcClient
) {

    @Scheduled(fixedRate = 60_000, initialDelay = 0)
    @Transactional
    fun syncProducts() {
        println("📡 Fetching products from remote")
        val products = productClient.fetchProducts()

        products.take(50)
            .forEach { product ->
                jdbcClient.sql(
                    """
                INSERT INTO products (id, title)
                VALUES (:id, :title)
                ON CONFLICT (id) DO UPDATE SET
                    title = EXCLUDED.title
                """
                )
                    .param("id", product.id)
                    .param("title", product.title)
                    .update()
            }

        println("✅ Synced ${products.size} products into database.")
    }
}
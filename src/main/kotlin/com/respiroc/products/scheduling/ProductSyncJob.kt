package com.respiroc.products.scheduling

import com.respiroc.products.client.ProductClient
import com.respiroc.products.repository.ProductRepository
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ThreadLocalRandom

@Component
class ProductSyncJob(
    private val productRepository: ProductRepository,
    private val productClient: ProductClient
) {

    @Scheduled(fixedRate = 600_000, initialDelay = 0)
    @Transactional
    fun syncProducts() {
        println("📡 Fetching products from remote")
        val products = productClient.fetchProducts()

        products.take(50)
            .forEach { product ->
                productRepository.save(product)
            }

        productRepository.truncateToLast50Rows();

        println("✅ Synced ${products.size} products into database.")
    }
}
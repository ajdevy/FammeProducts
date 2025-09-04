package com.respiroc.products.client

import com.respiroc.products.domain.entity.Product
import com.respiroc.products.domain.entity.ProductResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI

@Component
class ProductClient(
    private val webClient: WebClient = WebClient.builder().build()
) {

    /**
     * Fetches products as a regular List<Product> (blocking call)
     */
    fun fetchProducts(): List<Product> {
        return webClient
            .get()
            .uri(URI.create("https://famme.no/products.json"))
            .retrieve()
            .bodyToMono(ProductResponse::class.java)
            .block()
            ?.products
            ?: emptyList()
    }
}
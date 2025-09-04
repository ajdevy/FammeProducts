package com.respiroc.products.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductResponse(
    val products: List<Product>
)

data class Product(
    val id: Long,
    val title: String,
    val variants: List<ProductVariant>
)

data class ProductVariant(
    val id: Long,
    val title: String,
    val price: Double,
    @JsonProperty("available")
    val isAvailable: Boolean,
    val featuredImage: FeaturedImage?
)

data class FeaturedImage(
    val id: Long,
    @JsonProperty("src")
    val imageUrl: String
)
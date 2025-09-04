package com.respiroc.products.domain.mapper

import com.respiroc.products.client.ProductDto
import com.respiroc.products.db.entity.ProductEntity
import com.respiroc.products.domain.entity.Product

fun Product.toDatabase(): ProductEntity =
    ProductEntity(
        id = id,
        title = title
    )

fun ProductEntity.fromDatabase(): Product =
    Product(
        id = id,
        title = title,
        variants = emptyList()
    )

fun ProductDto.toDatabase(): ProductEntity =
    ProductEntity(
        id = id,
        title = title
    )
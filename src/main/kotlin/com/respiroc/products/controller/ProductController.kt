package com.respiroc.products.controller

import com.respiroc.products.domain.entity.Product
import com.respiroc.products.repository.ProductRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom
import java.util.random.RandomGenerator

@Controller
class ProductController(
    private val repository: ProductRepository
) {

    @GetMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    // HTMX: Load all products into table
    @GetMapping("/products", produces = ["text/html"])
    fun loadProductsFragment(model: Model): String {
        model.addAttribute("products", repository.findAll())
        return "index :: productTable"
    }

    // HTMX: Add new product → return only the new row
    @PostMapping("/products", produces = ["text/html"])
    fun addProduct(
        @RequestParam("title")
        productTitle: String,
        model: Model
    ): String {
        val saved = repository.save(
            ThreadLocalRandom.current().nextLong(),
            productTitle
        )
        model.addAttribute("product", saved)
        return "index :: productRow"
    }
}
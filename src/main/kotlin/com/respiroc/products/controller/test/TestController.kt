package com.respiroc.products.controller.test

import com.respiroc.products.client.ProductDto
import com.respiroc.products.repository.ProductRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController(
    private val repository: ProductRepository
) {
    @GetMapping("/test")
    fun testPage(model: Model): String {
        model.addAttribute("products", repository.findAll())
        return "test"
    }

    fun getDummyProducts(): List<ProductDto> = listOf(
        ProductDto(1, "Laptop"),
        ProductDto(2, "Phone"),
        ProductDto(3, "Tablet")
    )
}
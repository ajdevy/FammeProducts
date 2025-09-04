package com.respiroc.products.client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        // Increase buffer size to 10 MB to avoid DataBufferLimitException
        val strategies = ExchangeStrategies.builder()
            .codecs { codecs ->
                codecs.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) // 10 MB
            }
            .build()

        return WebClient.builder()
            .exchangeStrategies(strategies)
            .build()
    }
}
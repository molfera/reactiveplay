package com.mol.reactapp;

import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("generator")
class GeneratorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductRepository productRepository;

    GeneratorController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping(value = "products/{count}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Product> generate(@PathVariable int count) {
        UUID series = UUID.randomUUID();

        logger.info(String.format("Reactive controller!!. Series: %s, count: %s", series, count));

        List<Product> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generateProduct(i, series));
        }

        var start = LocalTime.now();
        var result = productRepository.saveAll(list);
        logger.info(String.format("Save time[ns]: %s", Duration.between(start, LocalTime.now()).toNanos()));

        return result;
    }

    @PutMapping(value = "products/{count}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Mono<Long> generateI(@PathVariable int count) {
        return generate(count).count();
    }

    private Product generateProduct(Integer integer, UUID series) {
        Faker faker = new Faker();
        return Product.builder()
                .series(series)
                .numberInSeries(integer)
                .name(faker.commerce().productName())
                .brand(faker.commerce().brand())
                .price(new BigDecimal(faker.commerce().price()))
                .build();
    }

}

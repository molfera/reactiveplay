package com.mol.standardapp;

import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("generator")
class OldGeneratorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository productRepository;

    public OldGeneratorController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping(value = "products/{count}")
    List<Product> generate(@PathVariable int count) {
        UUID series = UUID.randomUUID();
        List<Product> list = new ArrayList<>();

        logger.info(String.format("No reactive controller. Series: %s, count: %s", series, count));

        for (int i = 0; i < count; i++) {
            list.add(generateProduct(i, series));
        }

        var start = LocalTime.now();

        var result = productRepository.saveAll(list);
        logger.info(
                String.format("Save time[ns]: %s", Duration.between(start, LocalTime.now()).toNanos()));
        return result;

    }

    @PutMapping(value = "products/{count}")
    int generateI(@PathVariable int count) {
        return generate(count).size();
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

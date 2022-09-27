package com.mol.reactapp;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findAllBySeries(UUID series);

}

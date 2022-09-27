# Description
Reactive i standard Spring Boot application with mongo database.

## Load tests

Apache Benchmark: https://httpd.apache.org/docs/2.4/programs/ab.html

### reactive app
ab -n 10000 -c 10 -m POST -l  http://localhost:8080/generator/products/10

### Standard app
ab -n 10000 -c 10 -m POST -l  http://localhost:8081/generator/products/10
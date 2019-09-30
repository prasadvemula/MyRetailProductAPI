# item
## Introduction
This is a Restful aggregator service which collects product name and price information from different sources and returns output as shown below:

{
    "product_id": "76198491",
    "title": "Samsung 55\" Smart 4K UHD TV - Charcoal Black (UN55RU7100FXZA)",
    "current_price": {
	    "value": "499.99",
	    "currency": "USD"
     }
}

## Technology Stack
* Java 8
* SpringBoot
* SpringDataCassandra
* Gradle
* RxJava

## Dependency
### Apache cassandra
It requires the database up and running at node mentioned in application.yml. In this example it uses local instance.
To run in local create the below keyspace, table info and provide cassandra host, port and keyspace info as part of application.yml

create KEYSPACE product WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

create table price (product_id text, price text, currency text, PRIMARY KEY (product_id));

sample insert statement: INSERT INTO price (product_id, price, currency) values ('52268280','29.99', 'USD');

## Running app

gradle bootRun

## Routes

[list all routes that this service supports]

| Method | Route | Class | Description
| ------ | ----- | ----- | -----------
| GET  | /v1/product/76198491 | ProductController.getProductDetails | Item detail with price
| POST | /v1/price | ProductController.createPriceById | create price for a given product id
| PUT | /v1/price/76198491 | ProductController.updatePriceById | update price details

## Swagger spec for service definitions/contracts

http://localhost:8082/swagger-ui.html

## Hystrix Monitor

http://localhost:8082/hystrix
    - Enter http://localhost:8082/actuator/hystrix.stream then enter Monitor Stream button

## Sample CURL Requests

curl -X POST \
  http://localhost:8082/v1/price \
  -H 'content-type: application/json' \
  -d '{
  	"product_id": "76198491",
	"price":"499.99",
	"currency":"USD"
}'

curl -X GET \
  http://localhost:8082/v1/product/76198491
  
curl -X PUT \
  http://localhost:8082/v1/price/76198491 \
  -H 'content-type: application/json' \
  -d '{
	"price":"449.99"
}'

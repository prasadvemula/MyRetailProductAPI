FROM docker.target.com/tap/alpine-jre
ADD build/libs/myretail-product-api-0.1.0.jar myretail-product-api-0.1.0.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "myretail-product-api-0.1.0.jar"]
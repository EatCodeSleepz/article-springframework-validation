# Validation


## Introduction
Validation in `Java` as I know, can use one of the following:
1. Bean Validation (this is not `Spring` specific)
2. Spring `org.springframework.validation.Validator`  
https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation

This article will focus on the first one, bean validator. For the second one, the documentation link above from Spring above already provides quite some detail.

How the bean validation looks like.
```
@Min(1)
int quantity;
```

The above `@javax.validation.constraints.Min` annotation will not do the actual validation. It is just a marker; it belongs to a spec, JSR (beanvalidation.org). As of this writing, the only implementation of `Bean Validation 2.0 (JSR-380)` is `Hibernate Validator`.

If we include `spring-boot-starter-validation`, the `Hibernate Validator` has been included.


## Style of This Article
* This article focuses on basic knowledge of validation, how it works. To get readers quickly started. But the material in this article should be enough for the project backend validation.
* Each chapters are in their own directory; as separate Maven projects.
* Please open the corresponding `.java` while reading the article. Do not just read the article.
* The test cases that come with the article are for the readers to easily run it, see the outcome.
Without need to deploy the code and manual input, call the endpoint.
Of course if readers prefer to deploy the code, no problem. Test cases in this article by no means to act as a 'normal test case'; to test the codes.

To start, please open `table-of-content.md`. Happy reading :)

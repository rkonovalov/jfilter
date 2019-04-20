<div align="center">
  <a href="https://rkonovalov.github.io/projects/jfilter/1.0.12/">
    <img src="https://rkonovalov.github.io/assets/images/jfilter-logo.svg" alt="JFilter Main page">
  </a>
  <br>
</div>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.org/rkonovalov/jfilter.svg?branch=master)](https://travis-ci.org/rkonovalov/jfilter)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.rkonovalov/json-ignore/badge.svg?style=blue)](https://search.maven.org/search?q=a:json-ignore)
[![Javadocs](https://www.javadoc.io/badge/com.github.rkonovalov/json-ignore.svg)](https://www.javadoc.io/doc/com.github.rkonovalov/json-ignore)
[![codecov](https://codecov.io/gh/rkonovalov/jfilter/branch/master/graph/badge.svg)](https://codecov.io/gh/rkonovalov/jfilter)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a0133be1929145eabe7d50217587b896)](https://www.codacy.com/app/rkonovalov/jfilter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rkonovalov/jfilter&amp;utm_campaign=Badge_Grade)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=jfilter&metric=alert_status)](https://sonarcloud.io/dashboard?id=jfilter)

# About
This module could be used in Spring Web Service project for filter(exclude) of fields in Service response.
When you used Jackson @JsonView interface and need more powerful and flexibility, this module could be useful.
For information please follow the links below.

## Publications
Publication on [medium.com](https://medium.com/spring-web-service-response-filtering/spring-web-service-response-filtering-5dcff6679327)

Publication on [dzone.com](https://dzone.com/articles/spring-web-service-response-filtering)

## Index
* [Main page](https://rkonovalov.github.io/projects/jfilter/1.0.12/)
* [Requirements](https://rkonovalov.github.io/projects/jfilter/1.0.12/requirements/)
* [Diagram](https://rkonovalov.github.io/projects/jfilter/1.0.12/diagram/)
* [Installation](https://rkonovalov.github.io/projects/jfilter/1.0.12/installation/)
* [Getting started](https://rkonovalov.github.io/projects/jfilter/1.0.12/getting-started/)
* [Examples](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/)
  * [Simple field filter](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/filter-field/)  
  * [Session strategy filter](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/filter-strategy/) 
  * [XML Schema-based filter configuration](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/filter-file/)
  * [Whole Spring Controller filtration](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/filter-controller/)
  * [Dynamic Filter](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/filter-dynamic/)
* [Release Notes](https://rkonovalov.github.io/projects/jfilter/1.0.12/release-notes/)


# Diagram of module flowchart
![Diagram](https://rkonovalov.github.io/assets/images/jfilter-diagram.1.0.8.png)


# Installation
For using this module you need to import dependency

```xml
<dependency>
    <groupId>com.github.rkonovalov</groupId>
    <artifactId>json-ignore</artifactId>
    <version>1.0.12</version>
</dependency>
```

# Getting started
For activation of JFilter module just add next annotation

```java
@EnableJsonFilter
```

# Example o usage
This example illustrates how easy you can configure Service Response.
Just add FieldFilterSetting annotation with filterable fields and module will exclude them from response

```java
    @FieldFilterSetting(className = User.class, fields = {"id", "password", "secretKey"})
    
    @RequestMapping(value = "/users/signIn",
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})            
    public User signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userController.signInUser(email, password);
    }
```

* Service response **before** filtration

```json
{
  "id": 10,
  "email": "janedoe@gmail.com", 
  "fullName": "Jane Doe",
  "password": "12345",
  "secretKey": "54321",
  "address": {
    "id": 15,
    "apartmentNumber": 22,
    "street": {
      "id": 155,
      "streetName": "Bourbon Street",
      "streetNumber": 15
    }
  }
}
```

* Service response **after** filtration

```json
{ 
  "email": "janedoe@gmail.com", 
  "fullName": "Jane Doe",
  "address": {
    "id": 15,
    "apartmentNumber": 22,
    "street": {
      "id": 155,
      "streetName": "Bourbon Street",
      "streetNumber": 15
    }
  }
}
```

For more examples please follow to the link [Examples](https://rkonovalov.github.io/projects/jfilter/1.0.12/examples/)

# Release notes

## Version 1.0.12
    * Added ObjectMapperCache for caching of ObjectMapper from each method in Spring controller
    * Added FilterConfiguration for extending of exist list of ObjectMappers and enabling/disabling of module
    * Refactored code mechanizm of serializing Spring http messages
    
## Version 1.0.11
    * Added HttpServletRequest in RequestSession for using in DynamicFilterComponent Case #6 
    * Added FilterBehaviour for keep/hide filtering fields Case #7 
    
## Version 1.0.10
    * Fixed bug in integration with Swagger UI Case #3

## Version 1.0.9
    * Fixed bug "No converter found for return value of type" Case #1
    * Added little features from Case #4, Case #5 

## Version 1.0.8
    * Added Dynamic Filter which allows to create own filters with custom behaviour
    * Added DynamicSessionFilter for storing custom field filter in session
    * Added feature for auto loading XML Schema-based configuration filter when it changed on runtime
    * Changed package name from com.json.ignore to com.jfilter 
    * Changed package from advice to components
    * Fixed bug in FileFilter which not allowed to load xml configuration from external source
    
## Version 1.0.7
    * Added FileWatcher controller which auto reloads modified XML Schema based configurations
    * Refactored Javadoc
    * Fixed incorrect Jackson XML dependency importing

## Version 1.0.6
    * Added JSON/XML converters inherited from HttpMessageConverter
    * Removed native reflection and added Jackson BeanSerializerModifier for field filtering
    * Added EnableJsonFilter annotation for enabling/disabling filtration
    * Added ability to apply filter annotations on whole Spring Rest controller
    * Fixed bugs

## Version 1.0.5
    * Added Filter provider for improving execution speed
    * Fixed bugs 

## Version 1.0.4
    * Added xml Schema-based configuration
    * Fixed bugs 

## Version 1.0.3
    * Added session strategy filtering

## Version 1.0.2
    * Added additional constructors

## Version 1.0.0
    * Initial release

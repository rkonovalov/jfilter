<div align="center">
  <a href="https://rkonovalov.github.io/projects/jfilter/1.0.18/">
    <img src="https://rkonovalov.github.io/assets/images/jfilter-logo.svg" alt="JFilter Main page">
  </a>
  <br>
</div>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.rkonovalov/jfilter/badge.svg?style=blue)](https://search.maven.org/search?q=g:com.github.rkonovalov%20a:jfilter)


# About
JFilter library could be used in Spring Web Service project for the filter(exclude) of fields in Service response.
When you used Jackson @JsonView interface and need more powerful and flexibility, this library could be useful.
For information please follow the links below.

## Publications
Article in [medium.com](https://medium.com/spring-web-service-response-filtering/spring-web-service-response-filtering-5dcff6679327)

Article in [dzone.com](https://dzone.com/articles/spring-web-service-response-filtering)

## Samples
You can view all samples in GitHub [Samples](https://github.com/rkonovalov/jfilter-samples)

## Index
* [Main page](https://rkonovalov.github.io/projects/jfilter/1.0.18/)
* [Requirements](https://rkonovalov.github.io/projects/jfilter/1.0.18/requirements/)
* [Diagram](https://rkonovalov.github.io/projects/jfilter/1.0.18/diagram/)
* [Installation](https://rkonovalov.github.io/projects/jfilter/1.0.18/installation/)
* [Getting started](https://rkonovalov.github.io/projects/jfilter/1.0.18/getting-started/)
* [Configuration](https://rkonovalov.github.io/projects/jfilter/1.0.18/configuration/)
* [Examples](https://rkonovalov.github.io/projects/jfilter/1.0.18/examples/)
  * [Simple field filter](https://rkonovalov.github.io/projects/jfilter/1.0.18/examples/filter-field/)  
  * [Session strategy filter](https://rkonovalov.github.io/projects/jfilter/1.0.18/examples/filter-strategy/) 
  * [XML Schema-based filter configuration](https://rkonovalov.github.io/projects/jfilter/1.0.18/examples/filter-file/)
  * [Whole Spring Controller filtration](https://rkonovalov.github.io/projects/jfilter/1.0.18/examples/filter-controller/)
  * [Dynamic Filter](https://rkonovalov.github.io/projects/jfilter/1.0.18/examples/filter-dynamic/)
* [Release Notes](https://rkonovalov.github.io/projects/jfilter/1.0.18/release-notes/)


# Diagram of library flowchart
![Diagram](https://rkonovalov.github.io/assets/images/jfilter-diagram-1.0.18.svg)


# Installation
For using this library you need to import dependency

```xml
<dependency>
    <groupId>com.github.rkonovalov</groupId>
    <artifactId>jfilter</artifactId>
    <version>1.0.18</version>
</dependency>
```
* Attention: if you have used previous versions of library please rename artifactId from **json-ignore** to **jfilter**

If you need to add SOAP Web Services support you need to import SOAP dependency also
```xml
<dependency>
    <groupId>com.github.rkonovalov</groupId>
    <artifactId>jfilter-soap</artifactId>
    <version>1.0.18</version>
</dependency>
```
Link to project [JFilter Soap](https://github.com/rkonovalov/jfilter-soap)

# Getting started
For activation of JFilter library just add next annotation

```java
@EnableJsonFilter
```

# Example o usage
This example illustrates how easy you can configure Service Response.
Just add FieldFilterSetting annotation with filterable fields and library will exclude them from response.

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

# Release notes

## Version 1.0.18
    * Added ability of using DynamicFilter along with FilterSettings, Case #16
    * Added ability of using DynamicFilter on class level, Case #18
    * Added support of SOAP Web Services (alpha version)

## Version 1.0.17
    * Added ability of using custom Message Converters, Case #11
    * Added ability of change configuration of exist ObjectMappers, Case #11
    * Removed of using ConverterMapperModifier, instead module uses MixinFilter for field filtering.
      It's need for correct filtering of Java 8 types Case #12
    
## Version 1.0.16
    * Simplified setting FilterFields into session or request attibutes for using DynamicSessionFilter
    * Changed artifactId of project from json-ignore to jfilter

## Version 1.0.15
    * Added ability of using default Spring MessageConverters MappingJackson2HttpMessageConverter and MappingJackson2XmlHttpMessageConverter instead of FilterConverter
    * Added FilterXmlMapper and FilterObjectMapper for overriding default ObjectMapper and XmlMapper when using default Spring MessageConverters
    * Changed generic type from Serializable to Object in FilterConverter
    
## Version 1.0.14
    * Refactored FilterConverter class for inheriting AbstractHttpMessageConverter instead of HttpMessageConverter
    * Added Comparator class for simplify if...else branching. Including in dynamic filter components
    * Refactored DynamicSessionFilter component
    
## Version 1.0.13
    * Fixed bug with LocalDateTime serializing
    
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

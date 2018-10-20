[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.org/rkonovalov/jsonignore.svg?branch=master)](https://travis-ci.org/rkonovalov/jsonignore)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.rkonovalov/json-ignore/badge.svg?style=blue)](https://search.maven.org/search?q=a:json-ignore)
[![Javadocs](http://www.javadoc.io/badge/com.github.rkonovalov/json-ignore.svg?1.0.3)](http://www.javadoc.io/doc/com.github.rkonovalov/json-ignore)
[![codecov](https://codecov.io/gh/rkonovalov/jsonignore/branch/master/graph/badge.svg)](https://codecov.io/gh/rkonovalov/jsonignore)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a0133be1929145eabe7d50217587b896)](https://www.codacy.com/app/rkonovalov/jsonignore?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rkonovalov/jsonignore&amp;utm_campaign=Badge_Grade)

# Json ignore module
Json ignore module for Spring Framework can be used in Spring MVC Rest project for filter(exclude) of fields in json response.

# Getting started
For using this module need to follow for next steps

## Importing dependency
If you are using Maven you need add next dependency

```xml
<dependency>
    <groupId>com.github.rkonovalov</groupId>
    <artifactId>json-ignore</artifactId>
    <version>1.0.4</version>
</dependency>
```
* If you are using another build automation tool, you can find configuration string by this link: https://search.maven.org/artifact/com.github.rkonovalov/json-ignore/1.0.4/jar

## Activating of Spring Advice component
You just need to add ComponentScan annotation which will find and activate filter advice class

```text
@ComponentScan({"com.json.ignore.advice"})
```
* Example 

```java
@Configuration
@ComponentScan({"com.json.ignore.advice"})
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter {
    
}
```

If you are using XML Schema-based configuration, you can add next configuration

```xml
 <context:component-scan base-package="com.json.ignore.advice"/>
```

## RestController class example
This example Rest class provides user authentication process. 
Method signIn returns User object
```java
@RestController
public class SessionService {
    @Autowired
    private UserController userController;   

    @FieldFilterSetting(className = User.class, fields = {"password", "secretKey"})
    @RequestMapping(value = "/users/signIn",
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})            
    public User signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userController.signInUser(email, password);
    }
}
```
## Example classes

### User class
```java
public class User {
    private Integer id;
    private String email;
    private String fullName;
    private String password;
    private String secretKey;
    private Address address;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public User setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User setAddress(Address address) {
        this.address = address;
        return this;
    }
}
```

### Address class
```java
public class Address {
    private Integer id;
    private Integer apartmentNumber;
    private Street street;

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public Address setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public Address setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
        return this;
    }

    public Street getStreet() {
        return street;
    }

    public Address setStreet(Street street) {
        this.street = street;
        return this;
    }
}
```

### Street class
```java
public class Street {
    private Integer id;
    private String streetName;
    private Integer streetNumber;

    public Street() {
    }

    public Integer getId() {
        return id;
    }

    public Street setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public Street setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public Street setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }
}
```

### Example of Json response without filtration(exclusion)
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
# JsonIgnoreSetting annotation configuration
By using JsonIgnoreSetting you can flexible configure of Json response result. You can exclude fields on specific class, subclasses or whole object

## Filtration(exclusion) of fields on specific class
```text
 @FieldFilterSetting(className = User.class, fields = {"password", "secretKey"})
```
Where: className specific class, fields - fields which we need to exclude from response
### Result
```json
{  
  "id":10,
  "email":"janedoe@gmail.com",
  "fullName":"Jane Doe",
  "address":{  
    "id":15,
    "apartmentNumber":22,
    "street":{  
      "id":155,
      "streetName":"Bourbon Street",
      "streetNumber":15
    }
  }
}
```

## Filtration(exclusion) of fields on subclasses
```text
 @FieldFilterSetting(className = User.class, fields = {"id", "password", "secretKey"})
 @FieldFilterSetting(className = Address.class, fields = {"id", "apartmentNumber"})
 @FieldFilterSetting(className = Street.class, fields = {"id", "streetNumber"})
 ...
```
In this example we declared multiple settings. If module finds next classes in response next fields will be excluded:
1) On User.class, fields: id, password, secretKey
2) On Address.class, fields: id, apartmentNumber
3) On Street.class, fields: id, streetNumber
### Result:
```json
{
  "email": "janedoe@gmail.com", 
  "fullName": "Jane Doe",
  "address": {
    "street": {
      "streetName": "Bourbon Street"
    }
  }
}
```

## Filter of fields on whole object
```text
 @FieldFilterSetting(fields = {"id"})
 ...
```
If you need to exclude some fields in class and subclasses, you shouldn't specify className parameter
### Result:
```json
{
  "email": "janedoe@gmail.com", 
  "fullName": "Jane Doe",
  "password":"12345",
  "secretKey": "54321",
  "address": {
    "apartmentNumber": 22,
    "street": {
      "streetName": "Bourbon Street",
      "streetNumber": 15
    }
  }
}
```
## Session strategy filtering
Sometimes you need to use different filtration at same response. As example: user can have different previlegies like: USER, ADMIN...
And for admin you can provide system critical fields. Next example shows how can do it

```java
@RestController
public class SessionService {
    @Autowired
    private UserController userController;   


    //Check if session has attribute ROLE with value USER
    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password"})
    })

    //Check if session has attribute ROLE with value ADMIN
    @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
            @FieldFilterSetting(fields = {"id"})
    })
  
    @RequestMapping(value = "/users/signIn",
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})            
    public User signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userController.signInUser(email, password);
    }
}
```
As you can see we have added two session strategies. How it works?
* Filter algorith attempts to find in session attributes attribute with name "ROLE"
* If attribute found, then algorithm compares session attribute value with "USER"
* If strings is equal, then algorithm attempts to filter fields specified in ignoreFields

So you can specify different filtering strategies depending of attributes in session

## File configuration
Also you can configure filtration in xml Schema-based configuration

* Example

```java
@RestController
public class SessionService {
    @Autowired
    private UserController userController;   


    //Check if session has attribute ROLE with value USER
    @FileFilterSetting(fileName = "filter_configuration.xml")
    @RequestMapping(value = "/users/signIn",
            params = {"email", "password"}, method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})            
    public User signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userController.signInUser(email, password);
    }
}
```

* And filter_configuration.xml example

```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE config PUBLIC
        "-//json/json-ignore mapping DTD 1.0//EN"
        "https://rkonovalov.github.io/json-ignore-schema-1.0.dtd">
<config>
    <controller class-name="com.SessionService">
        <strategy attribute-name="ROLE" attribute-value="USER">
                <filter class="com.User">
                    <field name="id"/>
                    <field name="password"/>
                </filter>
        </strategy>
        <strategy attribute-name="ROLE" attribute-value="ADMIN">
            <filter class="com.User">
                <field name="id"/>
            </filter>
        </strategy>        
    </controller>
</config>
```



# Release notes

## Version 1.0.4
Added xml Schema-based configuration
Fixed bugs 

## Version 1.0.3
Added session strategy filtering

## Version 1.0.2
Added additional constructors

## Version 1.0.0
Initial release

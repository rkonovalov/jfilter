[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Build Status](https://travis-ci.org/rkonovalov/jsonignore.svg?branch=master)](https://travis-ci.org/rkonovalov/jsonignore)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.rkonovalov/json-ignore/badge.svg?style=blue)](https://maven-badges.herokuapp.com/maven-central/com.github.rkonovalov/json-ignore/)
[![Javadocs](http://www.javadoc.io/badge/com.github.rkonovalov/json-ignore.svg)](http://www.javadoc.io/doc/com.github.rkonovalov/json-ignore)
[![codecov](https://codecov.io/gh/rkonovalov/jsonignore/branch/master/graph/badge.svg)](https://codecov.io/gh/rkonovalov/jsonignore)

# Json ignore module
Json ignore module for Spring Framework can be used in Spring MVC Rest project for filtering(excluding) of json response.

# Getting started
For using this module need to follow for next steps

## Importing dependency
If you are using Maven you need add next dependecy

```xml
<dependency>
    <groupId>com.github.rkonovalov</groupId>
    <artifactId>json-ignore</artifactId>
    <version>1.0.2</version>
</dependency>
```
If you are using another build automation tool, you can find configuration string by this URL:
https://search.maven.org/artifact/com.github.rkonovalov/json-ignore/1.0.2/jar

## ControllerAdvice class example
For handling response from Rest controller we should to create ControllerAdvice class

```java
@ControllerAdvice
public class IgnoreAdvice implements ResponseBodyAdvice<Serializable> {
    public static final Logger logger = Logger.getLogger(IgnoreAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //Check if JsonIgnoreSetting annotation exists in method
        return JsonIgnoreFields.annotationFound(methodParameter);
    }

    @Override
    public Serializable beforeBodyWrite(Serializable obj, MethodParameter methodParameter, MediaType mediaType,
                                        Class<? extends HttpMessageConverter<?>> aClass, 
                                        ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //Parse JsonIgnoreSetting annotation in string and try to filter(exclude) fields of Json response                               
        JsonIgnoreFields ignoreFields = new JsonIgnoreFields(methodParameter);
        try {
            ignoreFields.ignoreFields(obj);
        } catch (IllegalAccessException e) {
            if(logger.isEnabledFor(Level.ERROR))
                logger.error(e);
        }
        return obj;
    }
}
```

## RestController class example
This example Rest class provides user authentication process. 
Method signIn returns User object
```java
@RestController
public class SessionService {
    @Autowired
    private UserController userController;   

    @JsonIgnoreSetting(className = User.class, fields = {"password", "secretKey"})
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
 @JsonIgnoreSetting(className = User.class, fields = {"password", "secretKey"})
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
 @JsonIgnoreSetting(className = User.class, fields = {"id", "password", "secretKey"})
 @JsonIgnoreSetting(className = Address.class, fields = {"id", "apartmentNumber"})
 @JsonIgnoreSetting(className = Street.class, fields = {"id", "streetNumber"})
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

## Filtration(exclusion) of fields on whole object
```text
 @JsonIgnoreSetting(fields = {"id"})
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
# Release notes
## Version 1.0.2
Added additional constructors

## Version 1.0.0
Initial release

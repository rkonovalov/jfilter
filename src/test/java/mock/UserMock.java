package mock;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class UserMock implements Serializable {
    private Integer id;
    private String email;
    private String fullName;
    private String password;

    private int intValue;
    private Collection<String> collectionValue;
    private Map<String, String> mapValue;
    private boolean boolValue;

    public UserMock() {
    }

    public Integer getId() {
        return id;
    }

    public UserMock setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserMock setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserMock setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserMock setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getIntValue() {
        return intValue;
    }

    public UserMock setIntValue(int intValue) {
        this.intValue = intValue;
        return this;
    }

    public Collection<String> getCollectionValue() {
        return collectionValue;
    }

    public UserMock setCollectionValue(Collection<String> collectionValue) {
        this.collectionValue = collectionValue;
        return this;
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    public UserMock setMapValue(Map<String, String> mapValue) {
        this.mapValue = mapValue;
        return this;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public UserMock setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
        return this;
    }
}

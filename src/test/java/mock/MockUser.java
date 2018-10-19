package mock;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class MockUser implements Serializable {
    private Integer id;
    private String email;
    private String fullName;
    private String password;

    private int intValue;
    private Collection<String> collectionValue;
    private Map<String, String> mapValue;
    private boolean boolValue;
    private byte byteValue;
    private char charValue;
    private double doubleValue;
    private float floatValue;
    private long longValue;
    private short shortValue;

    public Integer getId() {
        return id;
    }

    public MockUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MockUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public MockUser setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MockUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getIntValue() {
        return intValue;
    }

    public MockUser setIntValue(int intValue) {
        this.intValue = intValue;
        return this;
    }

    public Collection<String> getCollectionValue() {
        return collectionValue;
    }

    public MockUser setCollectionValue(Collection<String> collectionValue) {
        this.collectionValue = collectionValue;
        return this;
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    public MockUser setMapValue(Map<String, String> mapValue) {
        this.mapValue = mapValue;
        return this;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public MockUser setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
        return this;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public MockUser setByteValue(byte byteValue) {
        this.byteValue = byteValue;
        return this;
    }

    public char getCharValue() {
        return charValue;
    }

    public MockUser setCharValue(char charValue) {
        this.charValue = charValue;
        return this;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public MockUser setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public MockUser setFloatValue(float floatValue) {
        this.floatValue = floatValue;
        return this;
    }

    public long getLongValue() {
        return longValue;
    }

    public MockUser setLongValue(long longValue) {
        this.longValue = longValue;
        return this;
    }

    public short getShortValue() {
        return shortValue;
    }

    public MockUser setShortValue(short shortValue) {
        this.shortValue = shortValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockUser)) return false;
        MockUser user = (MockUser) o;
        return intValue == user.intValue &&
                boolValue == user.boolValue &&
                byteValue == user.byteValue &&
                charValue == user.charValue &&
                Double.compare(user.doubleValue, doubleValue) == 0 &&
                Float.compare(user.floatValue, floatValue) == 0 &&
                longValue == user.longValue &&
                shortValue == user.shortValue &&
                Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(collectionValue, user.collectionValue) &&
                Objects.equals(mapValue, user.mapValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, fullName, password, intValue, collectionValue, mapValue, boolValue, byteValue, charValue, doubleValue, floatValue, longValue, shortValue);
    }
}

// File: repository/UserRepository.java
package Respository;

import utility.Connection;

import java.util.ArrayList;
import model.User;

public class UserRespository implements Respository<User> {
    public UserRespository(String string) {
    }

    @Override
    public ArrayList<User> find(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection connection) {
        // Implement find method for User
        // ...
        return new ArrayList<>();
    }

    @Override
    public User findOne(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection connection) {
        // Implement findOne method for User
        // ...
        return new User(joinTableName, joinTableName, joinTableName);
    }

    @Override
    public User insert(String[] data, Connection connection) {
        // Implement insert method for User
        // ...
        return new User(null, null, null);
    }

    public void show(String columnName, String operator, String keyword) {
    }

    public ArrayList<User> find(String keyword, Object conn) {
        return null;
    }
}

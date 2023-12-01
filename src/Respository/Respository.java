package Respository;

import utility.Connection;

import java.util.ArrayList;

public interface Respository<T> {
    ArrayList<T> find(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection connection);

    T findOne(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection connection);

    T insert(String[] data, Connection connection);
}

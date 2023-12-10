package repository;

import utility.Connection;

import java.util.ArrayList;

import model.Model;

public interface Repository {
    
	public abstract void find(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection conn	);
	public abstract void findOne(String columnName, String[] conditions, boolean joinTable, String joinTableName, Connection conn);
	public abstract void insert(Model model, Connection conn);
	public abstract void show(String columnName, String operator, String keyword, Connection conn);
	
}

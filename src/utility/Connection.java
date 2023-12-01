package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Connection {
	
	private static Connection instance;
	
    private String filePath;
	
	private Connection(String filePath) {
        this.filePath = filePath;
	}
	
	public static Connection getInstance(String filePath) {
		if(instance == null) {
			instance = new Connection(filePath);
		}
		return instance;
	}

    public BufferedReader openFile() throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }
	
    public FileWriter writeFile() throws IOException {
        return new FileWriter(filePath, true);
    }
	

}

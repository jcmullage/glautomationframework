package helper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import helper.logger.LoggerHelper;

public class DataBaseHelper {
	
	private static Logger log = LoggerHelper.getLogger(DataBaseHelper.class);
	private static String url = "jdbc:mysql://192.168.1.28:3306/db_vis";
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String userName = "vit_user";
	private static String password = "vit_User123#";
	private static Connection connection;
	private static DataBaseHelper instance = null;
	
	
	public DataBaseHelper() {
		connection = getSingleInstanceConnection();
	}
	
	
	/* when we are calling the getInstance method instance will be null so, 
	a new instance of the DataBaseHelp class will be created, which will automatically 
	call the constructor which will call the getSingleInstanceConnection method which will return a sql connection 
	*/
	public static DataBaseHelper getInstance() {
		if(instance==null) {
			instance = new DataBaseHelper();
		}
		return instance;
	}
	
	
	private Connection getSingleInstanceConnection() {
		try {
			Class.forName(driverName);
			try {
				connection = DriverManager.getConnection(url, userName, password);
				if(connection!=null) {
					log.info("Connected to database");
				}
			} catch (SQLException e) {
				log.error("Failed to create database connection - " + e);
			}
		} catch (ClassNotFoundException e) {
			log.error("Driver not found - " + e);
		}
		return connection;
	}
	
	
	public Connection getConenction() {
		return connection;
	}
	
	public static ResultSet getResultSet(String dbQuery) {
		instance = DataBaseHelper.getInstance();
		connection = instance.getConenction();
		log.info("Execulting query - " + dbQuery);
		try {
			Statement statment = connection.createStatement();
			ResultSet result = statment.executeQuery(dbQuery);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

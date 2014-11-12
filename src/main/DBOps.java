package main;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Adam on 11/1/2014
 */
public final class DBOps {
    static Connection conn = null;
    static PreparedStatement pst = null;
    static ResultSet resultSet = null;
    ResultSet workingSet = null;
    User[] users = {};
    int found_id = 0;

    public static void connect()
    {

    	String url = "jdbc:mysql://minazone.com:3306/";
    	String db = "project3?useUnicode=true&characterEncoding=utf8";
    	String driver = "com.mysql.jdbc.Driver";
    	String user = "guest";
    	String pass = "victini494";
    	            try {
						Class.forName(driver).newInstance();
						conn = DriverManager.getConnection(url+db,user,pass);
						System.out.println("Connected");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
    }

    public static ArrayList<String> getData(String location, String data, String where, String column) {
      try {

          String statement = "SELECT `" + column + "` FROM `" + location + "` WHERE `" + where + "` = \"" + data + "\"";
          pst = conn.prepareStatement(statement);
          resultSet = pst.executeQuery();
          ArrayList<String> results = new ArrayList<String>();
          while (resultSet.next())
        	  results.add(resultSet.getString(1));
          return results;
      }
      catch (SQLException error) {
          System.out.println("The state change failed. " + error.getMessage());
      }
      return null;
    }
    
    public static ArrayList<String> getColumn(String location, String column) {
        try {

            String statement = "SELECT `" + column + "` FROM `" + location + "`";
            pst = conn.prepareStatement(statement);
            resultSet = pst.executeQuery();
            ArrayList<String> results = new ArrayList<String>();
            while (resultSet.next())
          	  results.add(resultSet.getString(1));
            return results;
        }
        catch (SQLException error) {
            System.out.println("The state change failed. " + error.getMessage());
        }
        return null;
      }

    public static void updateData(String location, String column, String data, String where, String condition) {
    	try {
    		String statement = "UPDATE `" + location + "` SET `" + column + "` = \"" + data + "\" WHERE `" + where + "` = \"" + condition +"\"";
    		pst = conn.prepareStatement(statement);
            pst.executeUpdate();
            System.out.println("Updated table");
    	}
    	catch (SQLException error) {
            System.out.println("The state change failed. " + error.getMessage());
        }

    }
    
    /*  This creates an entire new entry into the table. Think carefully about whether
    	you actually want to use this or the updateData method. Otherwise the DB will
    	be cluttered with useless data.
    */
    public static void insertData(String location, String column, String data) {
    	try {
    		String statement = "INSERT INTO `" + location + "`(`" + column + "`) VALUES(\"" + data + "\")";
    		pst = conn.prepareStatement(statement);
            pst.executeUpdate();
            System.out.println("Inserted data to table");
    	}
    	catch (SQLException error) {
            System.out.println("The state change failed. " + error.getMessage());

        }
    }
}

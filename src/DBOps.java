import java.sql.*;

/**
 * Created by newScanTron on 10/9/2014.
 */
public class DBOps
{
    static Connection conn = null;
    static PreparedStatement pst = null;
    ResultSet resultSet = null;
    ResultSet workingSet = null;
    int found_id = 0;
    String stmnt = "";

    //this connect method is really just to check for a connections
    //if thats something you want to do before you start manipulating
    //the database
    public void connect()
    {

        try
        {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex)
        {
            // handle the error
            System.out.println("we hate you so much and cant find the driver");
        }
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/recipes?allowMultiQueries=true"
                    , "newScanTron", "Cr!TT3rph3r214");
            // Do something with the Connection
            //doing something with this connection

        } catch (SQLException ex)
        {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally
        {
            // going to close all these things but in this program is probable less
            //necessary cus this thing is using basically no memory.
            if (resultSet != null)
            {
                try
                {
                    resultSet.close();
                } catch (SQLException sqlEx)
                {
                } // ignore
                resultSet = null;
            }
            if (pst != null)
            {
                try
                {
                    pst.close();
                } catch (SQLException sqlEx)
                {
                } // ignore
                pst = null;
            }
        }
    }


    public ResultSet searchRecipe(String name)
    {

        try
        {

            String statement = "(SELECT * FROM recipes.recipes WHERE name = \"" + name + "\")";
            pst = conn.prepareStatement(statement);
            resultSet = pst.executeQuery();
            while (resultSet.next())
            {
                System.out.println(resultSet.getInt(1));
            }

            return resultSet;
        } catch (SQLException error)
        {
            System.out.println("prepared statement failed " + error.getMessage());
        }

        return resultSet;
    }
}    //method to search by tag
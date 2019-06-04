package sample;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;


public class linkMySql {
    public Connection guanliconnect()
    {
        Connection myCon = null;
        try {
            myCon =(Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/warehouse?user=root&password=12&" +
                            "autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8" );

            if(myCon != null)
            {
                System.out.println("Successfully connected!");
            }
            return myCon;
        }catch(Exception e)
        {
            System.out.print("not connect to the database!");
        }
        return myCon;
    }
    public Connection caigouconnect()
    {
        Connection myCon = null;
        try {
            myCon =(Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/warehouse?user=caigou&password=12&" +
                            "autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8" );

            if(myCon != null)
            {
                System.out.println("Successfully connected!");
            }
            return myCon;
        }catch(Exception e)
        {
            System.out.print("not connect to the database!");
        }
        return myCon;
    }
    public Connection xiaoshouconnect()
    {
        Connection myCon = null;
        try {
            myCon =(Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/warehouse?user=xiaoshou&password=12&" +
                            "autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8" );

            if(myCon != null)
            {
                System.out.println("Successfully connected!");
            }
            return myCon;
        }catch(Exception e)
        {
            System.out.print("not connect to the database!");
        }
        return myCon;
    }
}

package DatabaseProject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProject1 {
    
    Connection con;
    
    public static void main(String[] args){
        DatabaseProject1 pro = new DatabaseProject1();
        pro.createConnection();
        //pro.createTable();
        //pro.addBatch();
        pro.callableExample();
    }
    
    void callableExample (){
        try {
            CallableStatement stmt = con.prepareCall("{call Simple(?,?)}");
            stmt.setInt(1, 27);
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            Boolean hasResult = stmt.execute();
            if(hasResult){
                ResultSet res = stmt.getResultSet();
                while(res.next()){
                    System.out.println(res.getString("name"));
                }
            }
            int countReturned = stmt.getInt(2);
            System.out.println("Number of members got = " + countReturned);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    
    void addBatch(){
        try {
            Statement stmt = con.createStatement();
            stmt.addBatch("INSERT INTO USERS2 VALUES('USER1',25)");
            stmt.addBatch("INSERT INTO USERS2 VALUES('USER2',30)");
            stmt.addBatch("INSERT INTO USERS2 VALUES('USER3',35)");
            stmt.addBatch("INSERT INTO USERS2 VALUES('USER4',40)");
            stmt.addBatch("INSERT INTO USERS2 VALUES('USER5',45)");
            int [] ar  = stmt.executeBatch();
            for (int i : ar){
                System.out.println(i);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
    }
    
    void createTable() {
        try {
            String q = "CREATE TABLE DB1("
                    + "name varchar(100),"
                    + "age int,"
                    + "salary float"
                    + ");";
            Statement stmt = con.createStatement();
            stmt.execute(q);
            System.out.println("Successfully Created");
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
            System.out.println("Database Connection Successful.");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS2");
            while(rs.next()){
                String name = rs.getString("name");
                int age = rs.getInt("age");
                //System.out.println(name + " age = " + age);
            }
            stmt.close();
            System.out.println("All Records Displayed.");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}


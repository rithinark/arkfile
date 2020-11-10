import java.sql.*;
import java.util.Scanner;

public class jdbc_first{
    public static void main(String[] args){
        Scanner input= new Scanner(System.in);
        String name= input.next();
        int id= input.nextInt();
        Bank b= new Bank();
        b.connect("databasename");
        b.createTable();
        b.createAccount(name,id);
    }
}
class Bank {
    //establishing connection
    Connection con = null; //instance variable

    void connect(String database) {
        String url = "jdbc:mysql://localhost:3306/";
        try {
            con = DriverManager.getConnection(url + database, "root", "password");
            System.out.println("successfully connect");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //create table
    void createTable() {
        String data = "CREATE TABLE account" +
                "(name text NOT NULL," +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "balance INTEGER)";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(data);
            System.out.println("successfully table created");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //create account
    void createAccount(String name, int id) {
        String data = "INSERT INTO account(name,id) VALUES(?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(data)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("succesfull");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

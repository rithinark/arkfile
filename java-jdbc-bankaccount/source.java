import java.sql.*;
import java.util.LinkedList;
import java.util.List;


class wrapper{
    String name=null;
    Integer id= null;
    wrapper(String name){
        this.name=name;
    }
    wrapper(int id){
        this.id=id;
    }
}
public class dbmysqltut{
    public static void main(String[] args)throws InterruptedException{

        DataBase db=new DataBase();
        db.connectDb("Account");
        //db.createtable();
        /*

        List<List<wrapper>> accounts = new LinkedList<>();
        accounts.add(List.of(new wrapper("name1"),new wrapper("name2"),new wrapper("name3")
                            ,new wrapper("name4"),new wrapper("name5"),new wrapper("name6")));

        accounts.add(List.of(new wrapper(1001),new wrapper(1002),new wrapper(1003)
                            ,new wrapper(1004),new wrapper(1005),new wrapper(1006)));
        for(int i=0;i<accounts.get(0).size();i++){
            db.createAccount(accounts.get(0).get(i).name,accounts.get(1).get(i).id);
        }
         */

        //db.deposit(1004,8_00_000);
        //db.withdraw(1004,14_00_000);
        db.getDetails(1002);

        Runnable rn1= ()->{ db.deposit(1002,40_000); };
        Runnable rn2=() -> { db.withdraw(1002,20_000);};

        new Thread(rn1).start();
        new Thread(rn2).start();
        Thread.sleep(10);
        db.getDetails(1002);




    }
}
//Bank account
class DataBase{
    //to establish connection mysql database
    Connection con=null;

    void connectDb(String database){
        String url="jdbc:mysql://localhost:3306/"+database;
        try{
            con= DriverManager.getConnection(url,"root","password");
            System.out.println("successfully connected");
        }catch(SQLException se){
            System.err.println(se.getMessage());}
    }

    //to create a table
    void createtable(){
        String data ="CREATE TABLE account" +
                "(name text NOT NULL," +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "balance INTEGER )";
        try(Statement stmt = con.createStatement();){
            stmt.execute(data);

        }catch(SQLException se){
            System.err.println(se.getMessage());}
    }
    //to create an account
    void createAccount(String name, int id){
        String data="INSERT INTO account(name,id) VALUES(?,?)";
        try(PreparedStatement pstmt= con.prepareStatement(data)){
            pstmt.setString(1,name);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            System.out.println("successfully created");
        }catch(SQLException se){
            System.err.println(se.getMessage());}
    }

    //to deposit
    synchronized void deposit(int id, int amount){
        String dquery="UPDATE account SET balance=? WHERE id=?";
        String rquery="SELECT balance FROM account WHERE id=?";
        try(PreparedStatement pstmt= con.prepareStatement(dquery);
            PreparedStatement pstmt2=con.prepareStatement(rquery)){
            pstmt2.setInt(1,id);
            ResultSet rs= pstmt2.executeQuery();
            rs.next();
            int ebalance=rs.getInt("balance");
            pstmt.setInt(1,amount+ebalance);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            System.out.println("succesfully deposited...");
        }catch(SQLException se){
            System.err.println(se.getMessage());}
    }

    //to withdraw
    synchronized void withdraw(int id, int amount){
        String dquery="UPDATE account SET balance=? WHERE id=?";
        String rquery="SELECT balance FROM account WHERE id=?";
        try(PreparedStatement pstmt= con.prepareStatement(dquery);
            PreparedStatement pstmt2=con.prepareStatement(rquery)){
            pstmt2.setInt(1,id);
            ResultSet rs= pstmt2.executeQuery();
            rs.next();
            int ebalance=rs.getInt("balance");
            if(ebalance>amount){
            pstmt.setInt(1,ebalance-amount);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            System.out.println("sucessfully withdrawn");}
            else
                throw new PayOutOfBoundException();
        }catch(SQLException |PayOutOfBoundException ex){
            System.err.println(ex.getMessage());}
    }


    //to delete account
    void deleteAccount(int id){
        String data="DELETE FROM account WHERE id=?";
        try(PreparedStatement pstmt= con.prepareStatement(data)){
            pstmt.setInt(1,id);
            if(pstmt.executeUpdate()==1)
                System.out.println("account deleted");
            else
                System.out.println("account not found");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


    // to get current balance
    void getDetails(int id){
        String data="SELECT balance FROM account WHERE id=?";
        try(PreparedStatement pstmt = con.prepareStatement(data)){
            pstmt.setInt(1,id);
            ResultSet rs=pstmt.executeQuery();
            rs.next();
            System.out.println("current balance="+rs.getInt("balance"));

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
class PayOutOfBoundException extends Exception{
    PayOutOfBoundException(){
        super("PayOutOfBoundException: savings is less than withdrawal amount");
    }

}

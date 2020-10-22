import java.sql.*;
public class dbmysqltut{
    public static void main(String[] args) {
         Bank bank=new Bank();
         bank.connect("bank");
         //bank.createTable();
        //bank.createAccount("user1",1001);
        //bank.createAccount("user2",1002);
        //bank.deposit(1001,4_00_000);
        //bank.withdraw(1001,3_00_000);
        bank.deleteAccount(1002);
        
    }
}

//bank account
class Bank {
    //establishing connection
    Connection con= null; //instance variable
    void connect(String database){
        String url = "jdbc:mysql://localhost:3306/";
        try{
            con = DriverManager.getConnection(url+database, "root", "password");
            System.out.println("successfully connect");
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    //create table
    void createTable(){
        String data="CREATE TABLE account" +
                "(name text NOT NULL," +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "balance INTEGER)";
        try(Statement stmt =con.createStatement()){
            stmt.execute(data);
            System.out.println("successfully table created");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    //create account
    void createAccount(String name,int id){
        String data="INSERT INTO account(name,id) VALUES(?,?)";
        try(PreparedStatement pstmt = con.prepareStatement(data)){
            pstmt.setString(1,name);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            System.out.println("succesfull");

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    //deposit
    void deposit(int id,int amount){
        String data="UPDATE  account SET balance=? WHERE id=?";
        String get="SELECT balance FROM account WHERE id=?";
        try(PreparedStatement stmt = con.prepareStatement(data);
            PreparedStatement stmt2= con.prepareStatement(get)){
            stmt2.setInt(1,id);
            ResultSet rs= stmt2.executeQuery();
            rs.next();
            int existbalance= rs.getInt("balance");
            stmt.setInt(1,amount+existbalance);
            stmt.setInt(2,id);
            stmt.executeUpdate();
            System.out.println("Suscessfull");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


    //withdraw
    void withdraw(int id,int amount){
        String data="UPDATE  account SET balance=? WHERE id=?";
        String get="SELECT balance FROM account WHERE id=?";
        try(PreparedStatement stmt = con.prepareStatement(data);
            PreparedStatement stmt2= con.prepareStatement(get)){
            stmt2.setInt(1,id);
            ResultSet rs= stmt2.executeQuery();
            rs.next();
            int existbalance= rs.getInt("balance");
            if(existbalance>amount){
            stmt.setInt(1,existbalance-amount);
            stmt.setInt(2,id);
            stmt.executeUpdate();}
            else{
                System.err.println("your existing balance is lower than ur withdrawal amount");
            }
            System.out.println("Suscessfull");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }




    //deleteAccount
    void deleteAccount(int id){
        String data="DELETE FROM account WHERE id=?";
        try(PreparedStatement pstmt= con.prepareStatement(data)){
            pstmt.setInt(1,id);
            if(pstmt.executeUpdate()==1){
                System.out.println("account deleted succesfully");
            }
            else
                System.out.println("account not found");
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


}

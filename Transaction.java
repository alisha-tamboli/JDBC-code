import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//Transaction Handling by using commit() and rollback()
//Here used Databe\ase is Lenden
public class Transaction {
    private static final String url = "jdbc:mysql://localhost:3306/lenden";
    private static final String username = "root";
    private static final String password = "Admin@123";

    //method for checking debited account balance is less or greater that the debit amount 
    static boolean isSufficient(Connection connection, int acc_number, double amount) {
        try {
            String query = "SELECT balance FROM accounts WHERE acc_number = ? ";
            PreparedStatement prestmt = connection.prepareStatement(query);
            prestmt.setInt(1, acc_number);
            ResultSet rs = prestmt.executeQuery();

            if(rs.next()) {
                double curr_balance = rs.getDouble("balance");
                if(amount > curr_balance) {
                    return false;
                }else {
                    return true;
                }
            }
            rs.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
}

	public static void main(String[] args) throws Exception {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }   

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            //stop auto commit()
            connection.setAutoCommit(false);

            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE acc_number = ?";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE acc_number = ?";
            
            //cerating prepared statements
            PreparedStatement debit_prestmt = connection.prepareStatement(debit_query);
            PreparedStatement credit_prestmt = connection.prepareStatement(credit_query);

            //input from users for debit and credit amount
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Account number : ");
            int acc_number = sc.nextInt();
            System.out.println("Enetr amount to be debited : ");
            double amount = sc.nextDouble();
           
            //Set balance
            debit_prestmt.setDouble(1,amount);
            debit_prestmt.setInt(2, acc_number);
            
            credit_prestmt.setDouble(1, amount);
            credit_prestmt.setInt(2, 102);

            //execute the query
            debit_prestmt.executeUpdate();
            credit_prestmt.executeUpdate();

            //Using isSufficient method for checking debited account balance
            if(isSufficient(connection,acc_number, amount)) {
                connection.commit();
                System.out.println("Transaction completed Successfully!");
            }
            else {
                connection.rollback();
                System.out.println("Transaction Failed!!!");
            }

            credit_prestmt.close();
            debit_prestmt.close();
            sc.close();
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
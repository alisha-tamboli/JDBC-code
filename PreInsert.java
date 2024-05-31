import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class PreInsert {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String username = "root";
    private static final String password = "Admin@123";

	public static void main(String[] args) throws Exception {
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }   

        try {
            Connection connection = DriverManager.getConnection(url ,username, password);
        
            String query = "INSERT INTO students(name, age, marks) VALUES(?, ?, ?)";
            
            PreparedStatement prestmt = connection.prepareStatement(query);

			prestmt.setString(1, "Azhar");
            prestmt.setInt(2,18);
            prestmt.setDouble(3, 89.80);

            int rowsaffected = prestmt.executeUpdate();
			if(rowsaffected>0) {
				System.out.println("Data inserted Successfully!");
			}
			else {
				System.out.println("Data not Inserted!!");
			}
            connection.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {
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
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM students WHERE id = 2";

			int rowsaffected = stmt.executeUpdate(query);

			if(rowsaffected>0) {
				System.out.println("Deleted Successfully!");
			}
			else {
				System.out.println("not Deleted!!");
			}
            connection.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}


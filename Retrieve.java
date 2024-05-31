import java.sql.*;

public class Retrieve{
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
            Connection con = DriverManager.getConnection(url ,username, password);
            Statement stmt = con.createStatement();
            // Strinf query = "INSERT INTO users()"
            String query = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                double marks = rs.getDouble("marks");

                System.out.println("ID : " + id);
                System.out.println("NAME : " + name);
                System.out.println("AGE : " + age); 
                System.out.println("MARKS : " + marks);
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

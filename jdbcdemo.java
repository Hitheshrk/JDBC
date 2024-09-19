package open_ended;
import java.sql.*;
public class jdbcdemo
{

	public static void main(String[] args) 
	{
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded succesffully");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","root");
			System.out.println("Conenction established sucessfully..."+con); 
			Statement stmt=con.createStatement();
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}

	}

}

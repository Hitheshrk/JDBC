package open_ended;
import java.sql.*;
import java.io.*;
class admin
{
	final void insert(Connection con) throws IOException 
	{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			System.out.print("Enter Patient name: ");
			String Name = sc.readLine();
			System.out.print("Enter age: ");
			int Age = Integer.parseInt(sc.readLine());
			System.out.print("Enter prevoius health history: ");
			String History = sc.readLine();
			System.out.print("Enter symptoms: ");
			String Symptoms = sc.readLine();
			System.out.print("Enter Treatment: ");
			String Treatment = sc.readLine();
			PreparedStatement checkstmt = con.prepareStatement("SELECT COUNT(*) FROM patient WHERE Name = ?");
			checkstmt.setString(1, Name);
			ResultSet checkResults = checkstmt.executeQuery();
			checkResults.next();
			int count = checkResults.getInt(1);
			if (count > 0) 
			{
				System.out.println("Patient name already present in the table. No insertion is done.");
				return;
			}
			PreparedStatement insstmt = con.prepareStatement("INSERT INTO patient (Name, Age, History, Symptoms, Treatment) VALUES (?, ?, ?, ?, ?)");
			insstmt.setString(1, Name);
			insstmt.setInt(2, Age);
			insstmt.setString(3, History);
			insstmt.setString(4, Symptoms);
			insstmt.setString(5, Treatment);
			insstmt.executeUpdate();
			System.out.println("Patient details inserted successfully into the table.");
		}
		catch(SQLException e)
		{
			System.out.println("caught"+e);
		}
		finally
		{
		}
	}
	public void update(Connection con) throws IOException 
	{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			System.out.print("Enter patient name: ");
			String Name = sc.readLine();
			System.out.print("Enter field to update (Name, Age, History, Symptoms, Treatment): ");
			String field = sc.readLine();
			PreparedStatement checkstmt = con.prepareStatement("SELECT COUNT(*) FROM patient WHERE Name = ?");
			checkstmt.setString(1, Name);
			ResultSet checkResults = checkstmt.executeQuery();
			checkResults.next();
			int count = checkResults.getInt(1);
			if (count == 0) {
				System.out.println("Patient name not found in the table. Update failed.");
				return;
			}
			System.out.print("Enter new value for " + field + ": ");
			String newvalue = sc.readLine();
			PreparedStatement updstmt = con.prepareStatement("UPDATE patient SET " + field + " = ? WHERE name = ?");
			updstmt.setString(1, newvalue);
			updstmt.setString(2, Name);
			updstmt.executeUpdate();
			System.out.println("Changes updated successfully in the table.");
		}
		catch(SQLException e)
		{
			System.out.println("caught"+e);
		}
		finally
		{
		}
	}
	final void delete(Connection con) throws IOException 
	{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			System.out.print("Enter patient name: ");
			String Name = sc.readLine();

			PreparedStatement checkstmt = con.prepareStatement("SELECT COUNT(*) FROM patient WHERE Name = ?");
			checkstmt.setString(1, Name);
			ResultSet checkResults = checkstmt.executeQuery();
			checkResults.next();
			int count = checkResults.getInt(1);
			if (count == 0) {
				System.out.println("Patient name not found in the table. Deletion failed.");
				return;
			}
			PreparedStatement delstmt = con.prepareStatement("DELETE FROM patient WHERE Name = ?");
			delstmt.setString(1, Name);
			delstmt.executeUpdate();
			System.out.println("Patient name deleted successfully from the table.");
		}
		catch(SQLException e)
		{
			System.out.println("caught"+e);
		}
		finally
		{	
		}
	}
	public void read(Connection con) throws IOException
	{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			System.out.print("Enter patient name: ");
			String Name = sc.readLine();
			PreparedStatement checkstmt = con.prepareStatement("SELECT COUNT(*) FROM patient WHERE Name = ?");
			checkstmt.setString(1, Name);
			ResultSet checkResults = checkstmt.executeQuery();
			checkResults.next();
			int count = checkResults.getInt(1);
			if (count == 0) {
				System.out.println("Patient name not found in the table.");
				return;
			}
			PreparedStatement selstmt = con.prepareStatement("SELECT Age,History,Symptoms,Treatment FROM patient WHERE Name = ?");
			selstmt.setString(1, Name);
			ResultSet selResult = selstmt.executeQuery();
			selResult.next();
			System.out.println("Age: " + selResult.getInt("Age"));
			System.out.println("History: " + selResult.getString("History"));
			System.out.println("Symptoms: " + selResult.getString("Symptoms"));
			System.out.println("Treatment: " + selResult.getString("Treatment"));

		}
		catch(SQLException e)
		{
			System.out.println("caught"+e);
		}
		finally
		{	
		}
	}
}
class user extends admin
{
	public void update(Connection con) throws IOException
	{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try
		{

			System.out.print("Enter the patientname: ");
			String Name = sc.readLine();

			System.out.print("Enter Age: ");
			int Age = Integer.parseInt(sc.readLine());

			System.out.print("Enter prevoius History: ");
			String History = sc.readLine();

			System.out.print("Enter symptoms: ");
			String Symptoms = sc.readLine();


			System.out.print("Do you want to store your username? (yes/no): ");
			String answer = sc.readLine();


			if (answer.equalsIgnoreCase("yes")) 
			{
				System.out.print("Enter your username: ");
				String username = sc.readLine();
				PreparedStatement insestmt = con.prepareStatement("INSERT INTO patient.patient (Name, Age, History, Symptoms, Treatment) VALUES (\"?\", ?,\"?\", \"?\", \"?\");");
				insestmt.setString(1, username);
				insestmt.setString(2, Name);
				insestmt.setInt(3, Age);
				insestmt.setString(4, History);
				insestmt.setString(5, Symptoms);
				insestmt.executeUpdate();
			}
			else
			{
				System.out.println("Your data has not been stored. ");
			}
		}
		catch(SQLException e)
		{
			System.out.println("caught"+e);
		}
		finally
		{	
		}
	}
}
public class patient_db 
{
	public static void main(String[] args) throws IOException
	{
		Connection con=null;
		Statement stmt=null;
		try
		{
			int ch1,ch2;
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded succesfully");
			con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/patient?autoReconnect=true&useSSL=false","root","hithesh@mysql");
			System.out.println("Connection object is created"+con);
			stmt=con.createStatement();
			BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.println("Patient Database");
				System.out.println("Login 1)Admin 2)User");
				ch1=Integer.parseInt(sc.readLine());
				if(ch1==1)
				{
					String key;
					System.out.println("Enter Password ");
					key=sc.readLine();
					if(key.equals("hithesh"))
					{
						admin a = new admin();
						do
						{
							System.out.println("ADMIN ACCESS");
							System.out.println("Menu\n1->INSERT\n2->DELETE\n3->UPDATE\n4->READ\n5->STOP"); 
							System.out.println("Enter your choice");
							ch2=Integer.parseInt(sc.readLine());
							switch(ch2)
							{
							case 1:a.insert(con);
							break;
							case 2:a.delete(con);
							break;
							case 3:a.update(con);
							break;
							case 4:a.read(con);
							break;
							case 5:System.out.println("Admin Logged out successful Thank you!");
							break;
							}
						}while(ch2!=5);
					}
				}
				else if(ch1==2)
				{
					user u = new user();
					do
					{
						System.out.println("USER ACCESS");
						System.out.println("Menu\n1->UPDATE\n2->READ\n3->STOP"); 
						System.out.println("Enter your choice");
						ch2=Integer.parseInt(sc.readLine());
						switch(ch2)
						{
						case 1:u.update(con);  
						break;
						case 2:u.read(con);  
						break;
						case 3:System.out.println("User Logged out successful Thank you!");
						break;
						}
					}while(ch2!=3);
				}
				else
				{
					System.out.println("Invalid Choice");
					System.out.println(" Please try again later");
				}
			} catch(Exception e) {}
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Class not Found Exception :" );
			System.out.println(e.getMessage());
		}catch(SQLException e)
		{
			System.out.println("SQL Exception :" );
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				con.close();
				stmt.close();
			}
			catch(SQLException e)
			{
				System.out.println("SQL Exception :" );
				System.out.println(e.getMessage());	
			}
		}
	}
}
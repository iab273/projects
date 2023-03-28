import java.util.ArrayList;

public abstract class User implements java.io.Serializable{
	
	//data fields
	
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	
	//getters and setters
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String f) {
		this.firstName = f;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String l) {
		this.lastName = l;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String p) {
		this.password = p;
	}
	
	
	
	public void print() {
		System.out.println("Username: " + this.username);
	}
	

	//only method shared by both admin and regular user
	
	public abstract void viewAllCourses(ArrayList<Course> Courses);
}

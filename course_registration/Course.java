import java.util.ArrayList;

public class Course implements java.io.Serializable, Comparable<Course> {

	//data fields
	
	private String name;
	private String id;
	private int maxNumberOfStudents;
	private int numberOfStudentsRegistered;
	private ArrayList<Student> roster;
	private String instructor;
	private int section;
	private String location;
	
	//default constructor
	public Course() {
		this.roster = new ArrayList<Student>();
	}
	
	//constructor that accepts all data fields (besides roster) as arguments
	
	public Course(String n, String i, int max, int num, String in, int s, String l) {
		this.name = n;
		this.id = i;
		this.maxNumberOfStudents = max;
		this.numberOfStudentsRegistered = num;
		this.roster = new ArrayList<Student>();
		this.instructor = in;
		this.section = s;
		this.location = l;
	}
	
	//getters and setters
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String i) {
		this.id = i;
	}
	
	public int getMaxNumberOfStudents() {
		return this.maxNumberOfStudents;
	}
	
	public void setMaxNumberOfStudents(int m) {
		this.maxNumberOfStudents = m;
	}
	
	public int getNumberOfStudentsRegistered() {
		return this.numberOfStudentsRegistered;
	}
	
	public void setNumberOfStudentsRegistered(int n) {
		this.numberOfStudentsRegistered = n;
	}
	
	public ArrayList<Student> getRoster() {
		return this.roster;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	public void setInstructor(String i) {
		this.instructor = i;
	}
	
	public int getSection() {
		return this.section;
	}
	
	public void setSection(int s) {
		this.section = s;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String l) {
		this.location = l;
	}
	
	//to make sorting the courses easier later
	@Override
	public int compareTo(Course course) {
        
		int compareSeats = course.getNumberOfStudentsRegistered();
        
        return compareSeats - this.getNumberOfStudentsRegistered();

    }


}

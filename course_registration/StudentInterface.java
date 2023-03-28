import java.util.ArrayList;

public interface StudentInterface {
	
	public abstract void viewAvailableCourses(ArrayList<Course> Courses);
	
	public abstract void registerForCourse(ArrayList<Course> Courses);
	
	public abstract void withdrawFromCourse(ArrayList<Course> Courses);
	
	public abstract void viewRegisteredCourses(ArrayList<Course> Courses);

}

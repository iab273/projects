import java.util.ArrayList;

public interface AdminInterface {
		
	//course management
	public abstract void createCourse(ArrayList<Course> Courses);
	
	public abstract void deleteCourse(ArrayList<Course> Courses);
	
	public abstract void editCourse(ArrayList<Course> Courses, ArrayList<Student> Students);
	
	public abstract void displayCourse(ArrayList<Course> Courses);
	
	public abstract void registerStudent(ArrayList<Student> Students);
	
	
	//reports
	public abstract void viewFullCourses(ArrayList<Course> Courses);
	
	public abstract void saveFullCourses(ArrayList<Course> Courses);
	
	public abstract void viewCourseRoster(ArrayList<Course> Courses);
	
	public abstract void viewCoursesEnrolledByStudent(ArrayList<Course> Courses, ArrayList<Student> Students);
	
	public abstract void sortCourses(ArrayList<Course> Courses);
	
}

import java.util.ArrayList;
import java.util.Formatter;

public class Student extends User implements StudentInterface {
	
	//default constructor
	public Student() {
	}
	
	//constructor that accepts all datafields as arguments
	public Student(String f, String l, String u, String p) {
		this.firstName = f;
		this.lastName = l;
		this.username = u;
		this.password = p;
	}
	
	//methods to be used by student
	
	//method that overrides the abstract method in User class
	public void viewAllCourses(ArrayList<Course> courses) {

		// formatter for printing the courses in a neat table
		Formatter fmt = new Formatter();

		fmt.format("%-41s %-15s %-25s %-9s %-18s \n", "Course Name", "Course Id", "Instructor", "Section", "Location");
		fmt.format("\n");

		for (int i = 0; i < courses.size(); i++) {
			fmt.format("%-41s %-15s %-25s %-9s %-20s \n", courses.get(i).getName(), courses.get(i).getId(),
					courses.get(i).getInstructor(), courses.get(i).getSection(), courses.get(i).getLocation());
		}

		System.out.print(fmt);
	}
	
	
	public void print() {
		System.out.println(this.getFirstName() + " " + this.getLastName());
	}
	

	public void viewAvailableCourses(ArrayList<Course> courses) {
		//formatter for printing the courses in a neat table
		Formatter fmt = new Formatter();

		fmt.format("%-41s %-15s %-25s %-9s %-18s \n", "Course Name", "Course Id", "Instructor", "Section", "Location");  
		fmt.format("\n"); 

		//loop through course list and see which ones aren't full
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getMaxNumberOfStudents() > courses.get(i).getNumberOfStudentsRegistered()) {
				fmt.format("%-41s %-15s %-25s %-9s %-20s \n", courses.get(i).getName(), courses.get(i).getId(), 
						courses.get(i).getInstructor(), courses.get(i).getSection(), courses.get(i).getLocation());
			}
		}

		System.out.println("\nAvailable courses:\n");

		System.out.print(fmt);
	}
	
	
	public void registerForCourse(ArrayList<Course> courses) {
		String id;
		
		this.viewAvailableCourses(courses);
		
		System.out.print("Enter the id for the course you'd like to register for: ");
		id = App.input.nextLine();
		
		//find the course
		for (int i = 0; i < courses.size(); i++) {
			
			if (courses.get(i).getId().equalsIgnoreCase(id)) {
				
				//check to see if the student is already registered for the course
				for (int k = 0; k < courses.get(i).getRoster().size(); k++) {
					if (this.getUsername().equalsIgnoreCase(courses.get(i).getRoster().get(k).getUsername())) {
						System.out.println("You are already enrolled in this course.");
						return;
					}		
				}
				//if not enrolled, check to see if the class is full
				if (courses.get(i).getMaxNumberOfStudents() <= courses.get(i).getNumberOfStudentsRegistered()) {
					System.out.println("This class is full.");
					return;
				}
				//if class is not full, then we add the student to the class roster and update the courses number of students registered
				courses.get(i).getRoster().add(this);
				courses.get(i).setNumberOfStudentsRegistered(courses.get(i).getNumberOfStudentsRegistered() + 1);
				System.out.println("Successfully enrolled.");
				return;
			}
		}
		//if the course id was not found
		System.out.println("That course could not be found.");
	}
	
	
	public void withdrawFromCourse(ArrayList<Course> courses) {
		String id;
		
		this.viewRegisteredCourses(courses);
		
		System.out.print("Enter the id of the course you'd like to withdraw from: ");
		id = App.input.nextLine();
		
		//find the course
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getId().equalsIgnoreCase(id)) {
				
				//check to see if the student is enrolled in the course
				for (int k = 0; k < courses.get(i).getRoster().size(); k++) {
					if (courses.get(i).getRoster().get(k).getUsername().equalsIgnoreCase(this.getUsername())) {
						//if found on the roster, remove the student and update the course's number of students registered
						courses.get(i).getRoster().remove(k);
						courses.get(i).setNumberOfStudentsRegistered(courses.get(i).getNumberOfStudentsRegistered() - 1);
						System.out.println("Successfully withdrawn.");
						return;
					}
				}
				//if the student was not found in the course roster
				System.out.println("You are not enrolled in this course.");
				return;
			}
		}
		//if the course was not found
		System.out.println("This course could not be located.");
	}
	
	
	public void viewRegisteredCourses(ArrayList<Course> courses) {
		
		Formatter fmt = new Formatter();

		fmt.format("\n");
		fmt.format("%-41s %-15s %-25s %-9s %-18s \n", "Course Name", "Course Id", "Instructor", "Section", "Location");  
		fmt.format("\n"); 
		
		//loop through every course
		for (int i = 0; i < courses.size(); i++) {
			
			//see if the student is in the course roster
			for (int k = 0; k < courses.get(i).getRoster().size(); k++) {
				
				if (courses.get(i).getRoster().get(k).getUsername().equalsIgnoreCase(this.getUsername())) {
					//if the student is found on the roster, add the class to the formatter
					fmt.format("%-41s %-15s %-25s %-9s %-20s \n", courses.get(i).getName(), courses.get(i).getId(), 
							courses.get(i).getInstructor(), courses.get(i).getSection(), courses.get(i).getLocation());
					break;
				}
			}
		}
		
		System.out.println("\nCourses you are currently registered in:\n");

		System.out.print(fmt);
	}

	
}

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class App {

	// NOTES
	
	// upon first launch the default admin login is username: "Admin" and password: "Admin001"
	
	// a student's username will be their unique identifier, it is not case sensitive
	
	// a courses code will be its unique identifier, it is not case sensitive
	
	// the roster is tied to the number of students registered for a course, when the roster changes so will the number of students registered, they should always match
	
	// a student is not able to register for a class that is full from their account
	// however an Admin can add a student to a class that is full
	
	// this application does input validation
	
	
	// input validating method
	public static int getIntFromUser(Scanner input) {
		while (!input.hasNextInt()) {
			System.out.print("That is not an integer, try again: ");
			input.next();
		}
		return input.nextInt();
	}
	
	
	// scanner to be used in this and other classes to read input from user
	public static final Scanner input = new Scanner(System.in);

	// overloaded methods to print menu depending on the user
	public static void printMainMenu(Admin admin) {
		System.out.println("\n1) Course Management\n2) Reports\n3) Exit");
	}
	
	public static void printMainMenu(Student student) {
		System.out.println("\n1) View all courses\n2) View all courses that are NOT full\n"
				+ "3) Register for a course\n4) Withdraw from a course\n"
				+ "5) View all courses currently registered for\n6) Exit");
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
		//variables and objects to be used in the main
		Course tempCourse;
		String username;
		String password;
		int option;
		boolean found = false;
		
		// in my code the usernames of students will be their unique identifier
		// course's id will be its unique identifier
		
		// declaring and instantiating an Admin
		Admin admin = new Admin("Admin", "Admin001");
		
		// ArrayList of courses
		ArrayList<Course> courses = new ArrayList<Course>();
		
		// ArrayList of students
		ArrayList<Student> students = new ArrayList<Student>();
		
		
		
		
		// check to see if Students.ser file exists
		// if it does then we deserialize the file back into the Students ArrayList
		try {
			File studentsSerialFile = new File("src/Students.ser");

			// check if file exists; if it doesn't we will create it
			if (studentsSerialFile.createNewFile()) {
			} 
			// otherwise if the file already exists, that means we deserialize it
			else {
				try{
				      FileInputStream fis = new FileInputStream("src/Students.ser");
				      
				      ObjectInputStream ois = new ObjectInputStream(fis);
				      
				      // Cast as STudent ArrayList
				      students = (ArrayList<Student>)ois.readObject();
				      ois.close();
				      fis.close();
				      
				}
				catch(IOException ioe) {
				       ioe.printStackTrace();
				       return;
				}
				catch(ClassNotFoundException cnfe) {
				       cnfe.printStackTrace();
				       return;
				}
			}
		} 
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}


		// checking to see if a Courses.ser file exists
		try {
			File coursesSerialFile = new File("src/Courses.ser");

			// if it doesn't exist we will create it then read from csv file
			if (coursesSerialFile.createNewFile()) {
				
				Scanner reader = new Scanner(new File("src/MyUniversityCourses.csv"));
				reader.useDelimiter(",|\\n");
				
				// read the first list of the csv file since it is not actually data
				for (int i = 0; i < 8; i++) {
					reader.next();
				}
				
				// read the data from the csv file
				while (reader.hasNext()) {
					
					tempCourse = new Course();
					
					// load each piece of data into its respective datafield for the course
					tempCourse.setName(reader.next());
					tempCourse.setId(reader.next());
					tempCourse.setMaxNumberOfStudents(reader.nextInt());
					tempCourse.setNumberOfStudentsRegistered(reader.nextInt());
					
					// skipping the list of students field because it's automatically assigned null in the constructor
					reader.next();
					
				    tempCourse.setInstructor(reader.next());
					tempCourse.setSection(reader.nextInt());
					tempCourse.setLocation(reader.next());
					
					// add the temporary course to our ArrayList Courses
					courses.add(tempCourse);
					
				}
				
				reader.close();
				
			} 
			// otherwise if the file already exists, we deserialize it
			else {
				try{
				      FileInputStream fis = new FileInputStream("src/Courses.ser");
				      
				      ObjectInputStream ois = new ObjectInputStream(fis);
				      
				      // Cast as Course ArrayList
				      courses = (ArrayList<Course>)ois.readObject();
				      ois.close();
				      fis.close();
				      
				}
				catch(IOException ioe) {
				       ioe.printStackTrace();
				       return;
				}
				catch(ClassNotFoundException cnfe) {
				       cnfe.printStackTrace();
				       return;
				}
			}
		} 
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		

		
		
		
		
		
		
		// the user interface running
		
		System.out.println("Welcome to course registration!");
		
		while (true) {
			found = false;
			System.out.print("Login? (Enter yes or no): ");
			if (input.nextLine().equalsIgnoreCase("no")) {
				break;
			}
			
			System.out.print("Enter your username: ");
			username = input.nextLine();
			System.out.print("Enter your password: ");
			password = input.nextLine();
			
			// check to see if it's the admin
			if (username.equalsIgnoreCase(admin.getUsername()) && password.equals(admin.getPassword())) {
				
				System.out.println("Welcome admin.");
				found = true;
				
				while (true) {
					
					printMainMenu(admin);
					System.out.print("\nEnter your choice as a number: ");
					option = getIntFromUser(input);
					input.nextLine();
					
					// admin wants to do course management
					if (option == 1) {
						
						while (true) {
							
							System.out.println("\n1) Create a new course\n2) Delete a course\n3) Edit a course\n"
									+ "4) Display information for a given course\n5) Register a student\n6) Exit");
							System.out.print("\nEnter your choice as a number: ");
							option = getIntFromUser(input);
							input.nextLine();
							
							if (option == 1) {
								admin.createCourse(courses);
							}
							else if (option == 2) {
								admin.deleteCourse(courses);
							}
							else if (option == 3) {
								admin.editCourse(courses, students);
							}
							else if (option == 4) {
								admin.displayCourse(courses);
							}
							else if (option == 5) {
								admin.registerStudent(students);
							}
							else if (option == 6) {
								break;
							}
							else {
								System.out.println("That is not an option.");
							}
						}
					}
					// admin wants to do reports
					else if (option == 2) {
						
						while (true) {
							System.out.println("\n1) View all courses\n2) View all courses that are FULL\n"
									+ "3) Write to a file a list of courses that are full\n"
									+ "4) View the names of the students being registered in a specific course\n"
									+ "5) View the list of courses that a given student is being registered on\n"
									+ "6) Sort courses based on the current number of student registers\n7) Exit");
							System.out.print("\nEnter your choice as a number: ");
							option = getIntFromUser(input);
							input.nextLine();
							
							if (option == 1) {
								admin.viewAllCourses(courses);
							}
							else if (option == 2) {
								admin.viewFullCourses(courses);
							}
							else if (option == 3) {
								admin.saveFullCourses(courses);
							}
							else if (option == 4) {
								admin.viewCourseRoster(courses);
							}
							else if (option == 5) {
								admin.viewCoursesEnrolledByStudent(courses, students);
							}
							else if (option == 6) {
								admin.sortCourses(courses);
							}
							else if (option == 7) {
								break;
							}
							else {
								System.out.println("That is not an option.");
							}
						}
					}
					// admin wants to exit
					else if (option == 3) {
						break;
					}
					else {
						System.out.println("That is not an option.");
					}
				}
				
			}
			// check to see if the username and password matches any of the students
			else {
				for (int i = 0; i < students.size(); i++) {
					if (students.get(i).getUsername().equalsIgnoreCase(username) && students.get(i).getPassword().equals(password)) {
						
						System.out.println("Welcome student.");
						found = true;
						
						while (true) {
							
							printMainMenu(students.get(i));
							System.out.print("\nEnter your choice as a number: ");
							option = getIntFromUser(input);
							input.nextLine();
							
							if (option == 1) {
								students.get(i).viewAllCourses(courses);
							}
							else if (option == 2) {
								students.get(i).viewAvailableCourses(courses);
							}
							else if (option == 3) {
								students.get(i).registerForCourse(courses);
							}
							else if (option == 4) {
								students.get(i).withdrawFromCourse(courses);
							}
							else if (option == 5) {
								students.get(i).viewRegisteredCourses(courses);
							}
							else if (option == 6) {
								break;
							}
							else {
								System.out.println("That is not an option.");
							}
						}
						// stop looping through the students
						break;
					}
				}
			}
			// if there was no user with the username or password found
			if (!found) {
				System.out.println("Sorry, we didn't find a user with that username and password.");
			}
		}
		
		System.out.println("Goodbye!");
		
		// user has finished using the application
		
		
		
		
		
		
		
		
		
	
		// serializing the Student ArrayList so it is stored permanently
		
		try {
			// FileOutput Stream writes data to a file
			FileOutputStream fos = new FileOutputStream("src/Students.ser");

			// ObjectOutputStream writes objects to a stream (A sequence of data)
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// Writes the specific object to the OOS
			oos.writeObject(students);

			// Close both streams
			oos.close();
			fos.close();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}


		// serializing the Course ArrayList so it is stored permanently
		
		try {
			// FileOutput Stream writes data to a file
			FileOutputStream fos = new FileOutputStream("src/Courses.ser");
			
			// ObjectOutputStream writes objects to a stream (A sequence of data)
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// Writes the specific object to the OOS
			oos.writeObject(courses);
			
			// Close both streams
			oos.close();
			fos.close();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		

	}
	
	
}

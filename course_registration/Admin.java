import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.io.*;

public class Admin extends User implements AdminInterface {
	
	//constructor that accepts username and password as arguments
	
	public Admin(String u, String p) {
		this.username = u;
		this.password = p;
	}
	
	//methods to be used by Admin
	
	public void viewAllCourses(ArrayList<Course> courses) {
		
		//formatter for printing the courses in a neat table
		Formatter fmt = new Formatter();
		
		fmt.format("\n");
		fmt.format("%-41s %-15s %-13s %-13s %-25s %-9s %-18s \n", "Course Name", "Course Id", 
				"Total Seats", "Seats Taken", "Instructor", "Section", "Location");  
		fmt.format("\n");  

		for (int i = 0; i < courses.size(); i++) {
			fmt.format("%-41s %-15s %-13s %-13s %-25s %-9s %-20s \n", courses.get(i).getName(), courses.get(i).getId(), 
					courses.get(i).getMaxNumberOfStudents(), courses.get(i).getNumberOfStudentsRegistered(), 
					courses.get(i).getInstructor(), courses.get(i).getSection(), courses.get(i).getLocation());  
		}
		
		System.out.print(fmt);
	}	
	
	
	public void createCourse(ArrayList<Course> courses) {
		
		boolean alreadyExists;
		String id;
		
		//creating a temporary course to be added to the ArrayList
		Course tempCourse = new Course();
		
		System.out.print("Enter the name: ");
		tempCourse.setName(App.input.nextLine());
		
		//make sure the id isn't already taken by another course
		while (true) {

			alreadyExists = false;

			System.out.print("Enter the id: ");
			id = App.input.nextLine();

			for (int i = 0; i < courses.size(); i++) {
				if (courses.get(i).getId().equalsIgnoreCase(id)) {
					alreadyExists = true;
					System.out.println("There is already a class with that id, try again.");
					break;
				}
			}

			if (!alreadyExists) {
				tempCourse.setId(id);
				break;
			}
		}
		
		System.out.print("Enter the maximum number of students allowed: ");
		tempCourse.setMaxNumberOfStudents(App.getIntFromUser(App.input));
		App.input.nextLine();
		
		//want the default when creating a course to be 0 students registered
		tempCourse.setNumberOfStudentsRegistered(0);
		
		System.out.print("Enter instructor name: ");
		tempCourse.setInstructor(App.input.nextLine());
		
		System.out.print("Enter section number: ");
		tempCourse.setSection(App.getIntFromUser(App.input));
		App.input.nextLine();
		
		System.out.print("Enter location: ");
		tempCourse.setLocation(App.input.nextLine() + "\n");
		
		courses.add(tempCourse);
		System.out.println("Course successfully created.");
	}
	
	
	public void deleteCourse(ArrayList<Course> courses) {
		//temporary variables used in this method
		String id;
		
		this.viewAllCourses(courses);
		
		System.out.print("Enter the id of the course you'd like to delete: ");
		id = App.input.nextLine();
		
		//search for the id in the courses ArrayList
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getId().equalsIgnoreCase(id)) {
				courses.remove(i);
				System.out.println("Course successfully deleted.");
				return;
			}
		}
		//in the case that the course wasn't found
		System.out.println("There is no course with that id.");
	}
	
	
	public void editCourse(ArrayList<Course> courses, ArrayList<Student> students) {
		String id;
		int option;
		String tempStr;
		String username;
		int tempInt;
		
		this.viewAllCourses(courses);
		
		System.out.print("\nEnter the course id of the course you'd like to edit: ");
		id = App.input.nextLine();
		
		//search for the course
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getId().equalsIgnoreCase(id)) {
				
				System.out.println("\nCourse found.");
				while (true) {

					System.out.print("\nWhat field would you like to change?\n\n1) Max number of students\n2) List of students registered\n3) Instructor\n4) Section number\n5) Location\n");
					System.out.print("\nEnter your choice as an integer: ");
					option = App.getIntFromUser(App.input);
					App.input.nextLine();

					if (option == 1) {
						
						System.out.print("Enter the new max number of students: ");
						tempInt = App.getIntFromUser(App.input);
						App.input.nextLine();
						
						courses.get(i).setMaxNumberOfStudents(tempInt);
						System.out.println("Course successfully edited.");
						return;
						
					}
					else if (option == 2) {

						//first print out the course roster
						System.out.println("\nStudents registered:\n");
						for (int j = 0; j < courses.get(i).getRoster().size(); j++) {
							System.out.println(courses.get(i).getRoster().get(j).getFirstName() 
									+ " " + courses.get(i).getRoster().get(j).getLastName() + ", username: " 
									+ courses.get(i).getRoster().get(j).getUsername());
						}
						
						while (true) {

							System.out.print("\n1) Add a student\n2) Remove a student\n\nEnter your choice as a number: ");

							option = App.getIntFromUser(App.input);
							App.input.nextLine();

							//to add a student
							if (option == 1) {
								
								//first print out list of all students
								System.out.println("\nList of students:");
								for (int k = 0; k < students.size(); k++) {
									System.out.println(students.get(k).getFirstName() 
											+ " " + students.get(k).getLastName() + ", username: " 
											+ students.get(k).getUsername());
								}
								
								System.out.print("Enter the username of the student you'd like to add: ");
								username = App.input.nextLine();

								//first find the student
								for (int j = 0; j < students.size(); j++) {
									
									if (students.get(j).getUsername().equalsIgnoreCase(username)) {
										
										//then check the roster to see if the student is already registered for the course
										for (int y = 0; y < courses.get(i).getRoster().size(); y++) {
											if (students.get(j).equals(courses.get(i).getRoster().get(y))) {
												System.out.println("That student is already registered.");
												return;
											}
										}
										
										//otherwise add the student to the course roster and update the course's number of students registered
										courses.get(i).getRoster().add(students.get(j));
										courses.get(i).setNumberOfStudentsRegistered(courses.get(i).getNumberOfStudentsRegistered() + 1);
										System.out.println("Course successfully edited.");
										return;
									}
								}
								//otherwise if the student couldn't be found
								System.out.println("That student could not be located.");
								return;
							}
							//to remove a student
							else if (option == 2) {
								
								System.out.print("Enter the username of the student you'd like to remove: ");
								username = App.input.nextLine();

								//check to see if the student is enrolled in the course
								for (int j = 0; j < courses.get(i).getRoster().size(); j++) {
									
									if (courses.get(i).getRoster().get(j).getUsername().equalsIgnoreCase(username)) {
										//if found, remove the student from the roster and update the number of students registered
										courses.get(i).getRoster().remove(j);
										courses.get(i).setNumberOfStudentsRegistered(courses.get(i).getNumberOfStudentsRegistered() - 1);
										System.out.println("Course successfully edited.");
										return;
									}
								}
								//otherwise if the student wasn't on the roster
								System.out.println("That student could not be located.");
								return;
							}
							else {
								System.out.println("That is not an option.");
							}
						}
					}
					else if (option == 3) {
						System.out.print("Enter the new instructor's name: ");
						tempStr = App.input.nextLine();
						courses.get(i).setInstructor(tempStr);
						System.out.println("Course successfully edited.");
						return;
					}
					else if (option == 4) {
						System.out.print("Enter the new section number: ");
						tempInt = App.getIntFromUser(App.input);
						App.input.nextLine();
						courses.get(i).setSection(tempInt);
						System.out.println("Course successfully edited.");
						return;
					}
					else if (option == 5) {
						System.out.print("Enter the new location: ");
						tempStr = App.input.nextLine();
						courses.get(i).setLocation(tempStr + "\n");
						System.out.println("Course successfully edited.");
						return;
					}
					else {
						System.out.println("That is not an option.");
					}
				}
			}
		}
		//otherwise if the course was never found
		System.out.println("There is no course with that id.");
	}
				

	public void displayCourse(ArrayList<Course> courses) {
		String id;
		
		System.out.print("Enter the course id of the course you'd like to view: ");
		id = App.input.nextLine();
		
		//search for the course
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getId().equalsIgnoreCase(id)) {
				
				System.out.println("\nNAME: " + courses.get(i).getName());
				System.out.println();
				System.out.println("ID: " + courses.get(i).getId());
				System.out.println();
				System.out.println("MAX NUMBER OF STUDENTS: " + courses.get(i).getMaxNumberOfStudents());
				System.out.println();
				System.out.println("NUMBER OF STUDENTS ENROLLED: " + courses.get(i).getNumberOfStudentsRegistered());
				System.out.println();
				System.out.println("INSTRUCTOR: " + courses.get(i).getInstructor());
				System.out.println();
				System.out.println("SECTION: " + courses.get(i).getSection());
				System.out.println();
				System.out.println("LOCATION: " + courses.get(i).getLocation());
				
				return;
			}
		}
		//otherwise if the course was never found
		System.out.println("There is no course with that id.");
	}
	
	
	public void registerStudent(ArrayList<Student> students) {
		String username;
		boolean alreadyExists;
		Student tempStudent = new Student();
		
		System.out.print("Enter the student's first name: ");
		tempStudent.setFirstName(App.input.nextLine());
		
		System.out.print("Enter the student's last name: ");
		tempStudent.setLastName(App.input.nextLine());
		
		//don't allow a student to be added if there is already a student with the username
		while (true) {
			
			alreadyExists = false;
			
			System.out.print("Enter a username for the student: ");
			username = App.input.nextLine();
			
			for (int i = 0; i < students.size(); i++) {
				if (students.get(i).getUsername().equalsIgnoreCase(username)) {
					alreadyExists = true;
					System.out.println("There is already a student with that username, try again.");
					break;
				}
			}
			
			if (!alreadyExists) {
				tempStudent.setUsername(username);
				break;
			}
			
		}
		
		System.out.print("Enter a password for the student: ");
		tempStudent.setPassword(App.input.nextLine());
		
		students.add(tempStudent);
		System.out.println("Student succesfully created.");
	}
	
	
	public void viewFullCourses(ArrayList<Course> courses) {
		Formatter fmt = new Formatter();

		fmt.format("%-41s %-15s %-13s %-13s %-25s %-9s %-18s \n", "Course Name", "Course Id", "Total Seats", 
				"Seats Taken", "Instructor", "Section", "Location");  
		fmt.format("\n"); 
		
		//loop through and format the courses that are full
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getMaxNumberOfStudents() <= courses.get(i).getNumberOfStudentsRegistered()) {
				fmt.format("%-41s %-15s %-13s %-13s %-25s %-9s %-20s \n", courses.get(i).getName(), 
						courses.get(i).getId(), courses.get(i).getMaxNumberOfStudents(), 
						courses.get(i).getNumberOfStudentsRegistered(), courses.get(i).getInstructor(), 
						courses.get(i).getSection(), courses.get(i).getLocation());
			}
		}
		
		System.out.println("Full courses:");

		System.out.print(fmt);
	}
	

	public void saveFullCourses(ArrayList<Course> courses) {
		String fullCourses = "Full Courses:\n";
		
		//create the string of full courses 
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getMaxNumberOfStudents() <= courses.get(i).getNumberOfStudentsRegistered()) {
				fullCourses = fullCourses + courses.get(i).getName() + ", " + courses.get(i).getId() + "\n";
			}
		}
		
		//write the string to a file
		try {
			FileWriter writer = new FileWriter("src/fullCourses.txt", false);
			writer.write(fullCourses);
			writer.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		System.out.println("Succesfully written.\n");
	}

	
	public void viewCourseRoster(ArrayList<Course> courses) {
		String id;

		this.viewAllCourses(courses);

		System.out.print("Enter the course id of the course roster you'd like to view: ");
		id = App.input.nextLine();

		//search for the course
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getId().equalsIgnoreCase(id)) {
				System.out.println("Roster: ");
				//print the roster
				for (int j = 0; j < courses.get(i).getRoster().size(); j++) {
					System.out.println(courses.get(i).getRoster().get(j).getFirstName() 
							+ " " + courses.get(i).getRoster().get(j).getLastName() + ", username: " 
							+ courses.get(i).getRoster().get(j).getUsername());
				}
				System.out.println();
				return;

			}
		}
		//if the course was never found
		System.out.println("There is no course with that id.");
	}
	
	
	public void viewCoursesEnrolledByStudent(ArrayList<Course> courses, ArrayList<Student> students) {
		String username;
		
		System.out.println("List of students:");
		for (int i = 0; i < students.size(); i++) {
			System.out.println(students.get(i).getFirstName() + " " + students.get(i).getLastName() 
					+ ", username: " + students.get(i).getUsername());
		}
		
		System.out.print("Enter the username of the student whose course schedule you'd like to view: ");
		username = App.input.nextLine();
		
		//search for the student
		for (int i = 0; i < students.size(); i++) {
			
			if (students.get(i).getUsername().equalsIgnoreCase(username)) {
				//print the student's courses
				System.out.println("\nCourses " + students.get(i).getFirstName() + " " + students.get(i).getLastName() 
						+ " is enrolled in: ");
				//loop through each course and its roster
				for (int j = 0; j < courses.size(); j++) {
					for (int k = 0; k < courses.get(j).getRoster().size(); k++) {
						//if the student is found in the course's roster, print the course
						if (courses.get(j).getRoster().get(k).getUsername().equalsIgnoreCase(username)) {
							System.out.println(courses.get(j).getName() + ", " + courses.get(j).getId() + "\n");
							break;
						}
					}
				}
				return;
			}
		}
		//if the student was never found
		System.out.println("That student could not be located");
	}
	
	
	public void sortCourses(ArrayList<Course> courses) {
		Collections.sort(courses);
		System.out.println("Successfully sorted.\n");
	}
	
	
}

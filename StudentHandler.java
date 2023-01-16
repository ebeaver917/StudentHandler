// Elliot Beaver

import java.util.*;
import java.io.*;

public class StudentHandler {
	
	//create the collection of students
	public ArrayList<Student> students;
	
	//constructor to instantiate the collection 
	public StudentHandler() {
		students = new ArrayList<Student>();
	}
	
	//method to save the students in the collection to the file
	public void saveStudents(Scanner s) {
		try {
			//take in the file name
			String file;
			System.out.print("\nPlease input the filename to save as: ");
			file = s.next();
			
			//serialize the object
			FileOutputStream fos = new FileOutputStream(new File(file));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for(int i = 0; i < students.size(); i++) {
				oos.writeObject(students.get(i));
			}
			
			//close the streams
			fos.close();
			oos.close();
			
		//check for exceptions thrown
		} catch (FileNotFoundException FNFE) {
			System.out.println("Error: Invalid File.");
		} catch (IOException IOE) {
			System.out.println("There was an error.");
		} 
	}
	
	//method to load the students from the file to the collection
	public void loadStudents(Scanner s) {
		//clear out the current collection
		students.removeAll(students);
		try {
			//get the filename
			String file;
			System.out.print("\nPlease input the filename to load from: ");
			file = s.next();
			
			//de-serialize the object
			FileInputStream fis = new FileInputStream(new File(file));
			ObjectInputStream ois = new ObjectInputStream(fis);	
			for(int i = 0; i < Student.getNumStudents(); i++) {
				students.add((Student) ois.readObject());
			}
			//close the streams
			ois.close();
			fis.close();
			
		//check for excpetions thrown 
		} catch (FileNotFoundException FNFE) {
			System.out.println("Error: Invalid File.");
		} catch (IOException IOE) {
			System.out.println("There was an error.");
		} catch (ClassNotFoundException CNFE) {
			System.out.println("There was an error");
		}
	}
	
	//mehtod to add a student to the collection  
	public void addStudent(Scanner s) {
		
		//get the input for the student's names and create the object
		String first, last;
		System.out.print("Please input a first name: ");
		first = s.next();
		System.out.print("\nPlease input a last name: ");
		last = s.next();
		Student newStudent = new Student(first, last);
		
		//loop reading in the test and homework grades 
		double input = 0;
		System.out.println("Please input student homework grades one at a time (negative value to finish): ");
		
		//while the loop isnt broken, check if the input is a double. If no, give an error message and 
		//restart the method
		while(true) {
			if(!s.hasNextDouble()) {
				System.out.println("Invalid input, please try inputting the student again");
				addStudent(new Scanner(System.in));
			}
			else if(s.hasNextDouble()) {
				input = s.nextDouble();
				if(input < 0) {
					break;
				}
				//add the grade to the student object
				newStudent.addHW(input);
			}
		}	

		System.out.println("Please input student test grades one at a time (negative value to finish): ");
		while(true) {
			if(!s.hasNextDouble()) {
				System.out.println("Invalid input, please try inputting the student again");
				addStudent(new Scanner(System.in));
			}
			else if(s.hasNextDouble()) {
				input = s.nextDouble();
				if(input < 0) {
					break;
				}
				//add the grade to the student object
				newStudent.addTest(input);
			}
		}   
		//add the new student object to the ArrayList
		students.add(newStudent);
	}
	
	//use the toString() method in the Student class to print the students
	public void printAllStudents() {
		//sort the objects in the collection by first name
		Comparator<Student> c = (s1, s2) -> s1.getFirst().compareTo(s2.getFirst());
		students.sort(c);
		
		for(int i = 0; i < students.size(); i++) {
			System.out.println(students.get(i));
		}
		System.out.println("\nPrinted " + Student.getNumStudents() + " Student Records");
	}
	
	
	//method to reset the students in the collection
	public void clearAllStudents () {
		students.removeAll(students);
	}
	
	public static void main (String [] args) {
		//create the StudentHandler object
		StudentHandler studentList = new StudentHandler();
		Scanner kb = new Scanner(System.in);
		String input = "0";
		//loop until the input is 6
		while(!input.equals("6")) {
			//print the menu
			System.out.println("\n1: Print out all loaded students");
			System.out.println("2: Add student");
			System.out.println("3: Clear students");
			System.out.println("4: Save students to file");
			System.out.println("5: Load students from file");
			System.out.println("6: Quit");
			
			//prompt the user for input and read it in
			System.out.print("\n\nPlease input the number of your choice: ");			
			input = kb.next();
			
			//check the input for any of the 6 valid inputs
			switch(input) {
				case "1":
					studentList.printAllStudents();
					break;
				case "2":
					studentList.addStudent(new Scanner(System.in));
					break;
				case "3":
					studentList.clearAllStudents();
					break;
				case "4":
					studentList.saveStudents(new Scanner(System.in));
					break;
				case "5":
					studentList.loadStudents(new Scanner(System.in));
					break;
				case "6":
					break;
				//if the input is invalid, loop back to the menu
				default:
					System.out.println("\nInvalid choice, try again.");
			}
		}		
	}
}
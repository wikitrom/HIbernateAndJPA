package com.virtualpairprogrammers.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents a Student enrolled in the college management system (CMS)
 */
@Entity
@Table(name = "TBL_STUDENT")
public class Student {

	// -- Hibernate : we are using PROPERTY ACCESS

	private int id; // -- unique PK

	private String enrollmentID;
	private String name;
	private String tutorName; // This will become a class soon

	private Integer numberOfCourses;

	// -- Hibernate: non-argument constructor required
	public Student() {
	}

	/**
	 * Initialises a student with a particular tutor
	 */
	public Student(String name, String tutorName) {
		this.name = name;
		this.tutorName = tutorName;
	}

	/**
	 * Initialises a student with no pre set tutor
	 */
	public Student(String name) {
		this.name = name;
		this.tutorName = null;
	}

	public double calculateGradePointAverage() {
		// some complex business logic!
		// we won't need this method on the course, BUT it is import
		// to remember that classes aren't just get/set pairs - we expect
		// business logic in here as well.
		return 0;
	}

	// -- getters/setters

	public String getEnrollmentID() {
		return enrollmentID;
	}

	public void setEnrollmentID(String enrollmentID) {
		this.enrollmentID = enrollmentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfCourses() {
		return numberOfCourses;
	}

	@Column(name = "NUM_COURSES") // the mapping could be put in an xml-file
	public void setNumberOfCourses(Integer numberOfCourses) {
		this.numberOfCourses = numberOfCourses;
	}

	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "name: " + this.name + " tutor: " + this.tutorName;
	}

	// this annotation is required or hibernate will create a column called
	// ThisIsNotAColumnInDatabase
	@Transient
	public int getThisIsNotAColumnInDatabase() {
		return 0;
	}
}

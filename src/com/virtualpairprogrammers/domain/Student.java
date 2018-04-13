package com.virtualpairprogrammers.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents a Student enrolled in the college management system (CMS)
 */
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; // -- unique PK

	private String enrollmentID;
	private String name;
	@ManyToOne
	@JoinColumn(name = "TUTOR_FK")
	private Tutor supervisor; // -- foreign key FK

	// -- constructors --

	// -- required by Hibernate
	public Student() {
	}

	/**
	 * Initialize student with a particular tutor
	 */
	public Student(String name, Tutor supervisor) {
		this.name = name;
		this.supervisor = supervisor;
	}

	/**
	 * Initialize student with no pre-set tutor
	 */
	public Student(String name, String enrollmentId) {
		this.name = name;
		this.enrollmentID = enrollmentId;
		this.supervisor = null;
	}

	// -- getters/setters
	
	public String getSupervisorName() {
	 return supervisor.getName();
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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 public Tutor getSupervisor() {
	 return supervisor;
	 }
	 
	 
	// -- other methods --

	@Override
	public String toString() {
		return this.name;
	}

	public double calculateGradePointAverage() {
		// some complex business logic!
		// we won't need this method on the course, BUT it is import
		// to remember that classes aren't just get/set pairs - we expect
		// business logic in here as well.
		return 0;
	}

	 public void allocateSupervisor(Tutor newSupervisor) {
	 this.supervisor = newSupervisor;
	 newSupervisor.getModifiableSupervisionGroup().add(this);
	 }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enrollmentID == null) ? 0 : enrollmentID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (enrollmentID == null) {
			if (other.enrollmentID != null)
				return false;
		} else if (!enrollmentID.equals(other.enrollmentID))
			return false;
		return true;
	}
	 
	 
	 
}

package com.virtualpairprogrammers.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; // -- unique PK

	private String staffId;
	private String name;
	private int salary;

	// -- constructors -->
	
	// required by Hibernate
	public Tutor() {

	}

	// business constructors
	public Tutor(String staffId, String name, int salary) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.salary = salary;
	}

//	public String getStaffId() {
//		return staffId;
//	}

	public String getName() {
		return name;
	}
	
	public int getSalary() {
		return salary;
	}

	
	// -- methods -->
	
}

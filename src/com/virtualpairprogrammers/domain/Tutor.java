package com.virtualpairprogrammers.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; // -- unique PK

	private String staffId;
	private String name;
	private int salary;

	@OneToMany
	@MapKey(name="enrollmentID")  // what field the 'key' should be mapped to
	@JoinColumn(name="TUTOR_FK")
	private Map<String, Student> supervisionGroup;

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
		this.supervisionGroup = new HashMap<>();
	}

	// -- getters/setters -->

	// public String getStaffId() {
	// return staffId;
	// }

	public String getName() {
		return name;
	}

	public int getSalary() {
		return salary;
	}

	public Map<String, Student> getSupervisionGroup() {
		// make sure we return a 'constant' set to get better encapsulation
		Map<String, Student> unmodifiable = Collections.unmodifiableMap(this.supervisionGroup);
		return unmodifiable;
	}

	// -- methods -->

	public void addStudentToSupervisionGroup(Student student) {
		supervisionGroup.put(student.getEnrollmentID(), student);
	}

}

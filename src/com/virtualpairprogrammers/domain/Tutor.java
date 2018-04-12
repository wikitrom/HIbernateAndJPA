package com.virtualpairprogrammers.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; // -- unique PK

	private String staffId;
	private String name;
	private int salary;

	@OneToMany
	// @OrderBy("name")   // force table ordered by field 'name' on table read
	// @OrderColumn(name="id") // force table ordered by columd 'id' on table read
	
	@JoinColumn(name="TUTOR_FK")
	private List<Student> supervisionGroup;

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
		this.supervisionGroup = new ArrayList();
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

	public List<Student> getSupervisionGroup() {
		// make sure we return a 'constant' set to get better encapsulation
		List<Student> unmodifiable = Collections.unmodifiableList(this.supervisionGroup);
		return unmodifiable;
	}

	// -- methods -->

	public void addStudentToSupervisionGroup(Student student) {
		supervisionGroup.add(student);
	}

}

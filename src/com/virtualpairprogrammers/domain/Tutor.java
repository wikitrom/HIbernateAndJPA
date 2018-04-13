package com.virtualpairprogrammers.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Tutor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; // -- unique PK

	private String staffId;
	private String name;
	private int salary;

	@OneToMany(mappedBy = "supervisor") // in Student class
	private Set<Student> supervisionGroup;

	@ManyToMany(mappedBy = "teachers")
	private Collection<Subject> subjectsTaught;

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
		this.supervisionGroup = new HashSet<>();
		this.subjectsTaught = new HashSet<>();
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

	public Set<Student> getSupervisionGroup() {
		// make sure we return a 'constant' set to get better encapsulation
		Set<Student> unmodifiable = Collections.unmodifiableSet(this.supervisionGroup);
		return unmodifiable;
	}

	public Set<Student> getModifiableSupervisionGroup() {
		return this.supervisionGroup;
	}

	public Collection<Subject> getSubjectsTaught() {
		// make sure we return a 'constant' set to get better encapsulation
		Collection<Subject> unmodifiable = Collections.unmodifiableCollection(this.subjectsTaught);
		return unmodifiable;
	}

	public Collection<Subject> getModifiableSubjectsTaught() {
		return this.subjectsTaught;
	}

	// -- other methods -->

	public void addStudentToSupervisionGroup(Student student) {
		supervisionGroup.add(student);
		student.allocateSupervisor(this);
	}

	// - use this or addTutorToTeachers from Subject class to related tutors with
	// subjects (courses)
	public void addSubjectAsSubjectsTaught(Subject newSubject) {
		this.subjectsTaught.add(newSubject);
		newSubject.getModifiableTeachers().add(this);
	}

	public String toString() {
		return this.name + " (" + this.staffId + ")";
	}

}

package com.virtualpairprogrammers.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private int numOfSemesters;
	@ManyToMany
	private Collection<Tutor> teachers;

	// -- constructors --

	// -- required by Hibernate
	public Subject() {

	}

	public Subject(String title, int numOfSemesters) {
		super();
		this.title = title;
		this.numOfSemesters = numOfSemesters;
		this.teachers = new HashSet<>();
	}

	public Subject(String title) {
		this(title, 1);
	}

	// -- getters/setters -->

	public String getTitle() {
		return this.title;
	}

	public int getNumOfSemesters() {
		return this.numOfSemesters;
	}

	public Collection<Tutor> getTeachers() {
		Collection<Tutor> unmodifiable = Collections.unmodifiableCollection(this.teachers);
		return unmodifiable;
	}

	public Collection<Tutor> getModifiableTeachers() {
		return this.teachers;
	}

	// -- other methods --

	// - use this or addSubjectToSubjectsTaught from Tutor class to relate tutors
	// with subjects (courses)
	public void addTutorAsTeacher(Tutor newTeacher) {
		teachers.add(newTeacher);
		newTeacher.getModifiableSubjectsTaught().add(this);

	}

	public String toString() {
		return this.title + " (length: " + this.numOfSemesters + ")";
	}
}

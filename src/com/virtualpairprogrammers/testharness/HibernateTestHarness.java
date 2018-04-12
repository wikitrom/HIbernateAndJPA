package com.virtualpairprogrammers.testharness;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Tutor;

public class HibernateTestHarness {

	private static SessionFactory sessionFactory = null; // -- hibernate

	public static void main(String[] args) {

		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();
		// database access requires a transaction object
		// there is a javax.transaction.Transaction method, NOT used with this code.

		org.hibernate.Transaction tx;

		Student myStudent;
		Tutor newTutor;

		tx = session.beginTransaction();
		try {
			// myStudent = new Student("Rowan Atkinson");
			// newTutor = new Tutor("DEF456", "James Bond", 300000);
			//
			// session.save(myStudent);
			// session.save(newTutor);
			//
			// // make the student be supervised by that tutor
			// // this will trigger hibernate to perform an update of student table entry
			// myStudent.allocateSupervisor(newTutor);
			// // print out the supervisor
			// System.out.println(myStudent.getSupervisorName());

			// Student foundStudent = (Student) session.get(Student.class, 1);
			// System.out.println(foundStudent);
			//
			// Tutor newSup = (Tutor) session.get(Tutor.class, 2);
			// foundStudent.allocateSupervisor(newSup);
			//
			// Student foundStudent2 = (Student) session.get(Student.class, 2);
			// foundStudent2.allocateSupervisor(null);

			// Unidirectional relations
			// Tutor myTutor = (Tutor) session.get(Tutor.class, 2);

			// -> up to now tutor has no injfo about student
			// for now we revert the relationship direction and let Tutor
			// refer to Student using a Set of students

			// test - using collection of students in Tutor

			Tutor thisTutor = new Tutor("DOO007", "James Bond", 3000000);

			Student student1 = new Student("Rowan Atkinson");
			Student student2 = new Student("Baldrik");
			Student student3 = new Student("Mr Bean");

			session.save(student1);
			session.save(student2);
			session.save(student3);
			session.save(thisTutor);

			thisTutor.addStudentToSupervisionGroup(student1);
			thisTutor.addStudentToSupervisionGroup(student2);
			thisTutor.addStudentToSupervisionGroup(student3);

			Set<Student> students = thisTutor.getSupervisionGroup();
			for (Student next : students) {
				System.out.println(next);
			}

			Tutor myTutor = (Tutor) session.get(Tutor.class, 1);
			students = myTutor.getSupervisionGroup();
			for (Student next : students) {
				System.out.println(next);
			}

			Student student4 = new Student("Canyon King");
			session.save(student4);
			myTutor.addStudentToSupervisionGroup(student4);

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			// normally we throw an exception here
			System.out.println(e);
		} finally {
			if (session != null)
				session.close();
		}

	}

	// -- Helper method
	// we need a session factory to be able to work against a database using
	// hibernate. This is a one-time operation.
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

	// -- SLASKTRATT! -->

	// -- save an object to database

	// Student testStudent = new Student("Jessica Ennis", "Toni Minichello");
	// System.out.println("This student id: " + testStudent.getId());
	// Student testStudent = new Student("Ricky Berens", "Eddie Reese");
	// System.out.println(testStudent + "has a grade pont of " +
	// testStudent.calculateGradePointAverage());

	// tx = session.beginTransaction();
	// session.save(testStudent);
	// System.out.println("This student id is now: " + testStudent.getId());
	// tx.commit();

	// -- read an object to database
	// tx = session.beginTransaction();
	// myStudent = (Student) session.get(Student.class, 2);
	// System.out.println(myStudent);
	// tx.commit();

	// -- delete/remove an object from database

	// tx = session.beginTransaction();
	// session.delete(myStudent); // delete from DB, not memory
	// System.out.println(myStudent);
	// tx.commit();

	// -- update an object in a database (dirty-change)

	// Note: In this case we do NOT use the session.update method, instead we rely
	// on the HIBERNATE transaction commit method to perform a 'dirty-change'
	// operation.
	// I.e. if an object field has changed compared to what is in the database the
	// commit method will automagically execute a database entry update.
	// tx = session.beginTransaction();
	// myStudent = (Student) session.get(Student.class, 3);
	// myStudent.setTutorName("Sortoff Baldrick"); // update the object.
	// tx.commit();
	// tx.rollback();

}

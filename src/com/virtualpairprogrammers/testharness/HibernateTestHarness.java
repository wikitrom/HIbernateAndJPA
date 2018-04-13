package com.virtualpairprogrammers.testharness;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.virtualpairprogrammers.domain.Student;
import com.virtualpairprogrammers.domain.Subject;
import com.virtualpairprogrammers.domain.Tutor;

public class HibernateTestHarness {

	private static SessionFactory sessionFactory = null; // -- hibernate

	public static void main(String[] args) {

		SessionFactory sf = getSessionFactory();
		Session session;
		org.hibernate.Transaction tx; // javax.transaction.Transaction is NOT used here

		// -- TEST : No explicit definition of 'equals' method for class 'Student'
		System.out.println("DEBUG: NO 'equals' method has been defined for 'Student' class.");

		// -- create some objects to work with
		Tutor tutor1 = new Tutor("DOO007", "James Bond", 3000000);
		Tutor tutor2 = new Tutor("BAL001", "Sortoff Baldrik", -100);

		Student student1 = new Student("Rowan Atkinson", "1-ROW-2011");
		Student student2 = new Student("Baldrik", "2-BAL-1782");
		Student student3 = new Student("Mr Bean", "3-BEA-2003");

		Subject course1 = new Subject("Black Adder I", 12);
		Subject course2 = new Subject("Black Adder II", 6);
		Subject course3 = new Subject("Black Adder III", 3);

		System.out.println();
		System.out.println("DEBUG: starting first session");

		session = sf.openSession();
		tx = session.beginTransaction();
		try {
			System.out.println();
			System.out.println("DEBUG: starting first transaction");
			System.out.println();

			// test - using Bi-directional many-to-many relationship
			session.save(student1);
			session.save(student2);
			session.save(student3);
			session.save(tutor1);
			session.save(tutor2);

			// -- setup tutor-student relations
			tutor1.addStudentToSupervisionGroup(student1);
			tutor1.addStudentToSupervisionGroup(student2);
			tutor1.addStudentToSupervisionGroup(student3);

			System.out.println("Tutor for " + student1 + " is " + student1.getSupervisor());

			session.save(course1);
			session.save(course2);
			session.save(course3);

			// -- setup subject-teacher relationships
			tutor1.addSubjectAsSubjectsTaught(course1);
			tutor1.addSubjectAsSubjectsTaught(course2);
			tutor1.addSubjectAsSubjectsTaught(course3);
			// alternative way to define the relationship
			course3.addTutorAsTeacher(tutor2);
			// tutor2.addSubjectToSubjectsTaught(course3);

			System.out.println("Teacher for " + course1 + " is " + course1.getTeachers());
			System.out.println("Teacher for " + course2 + " is " + course2.getTeachers());
			System.out.println("Teacher for " + course3 + " is " + course3.getTeachers());

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			// normally we throw an exception here
			System.out.println(e);
		} finally {
			if (tx.wasRolledBack() && session != null) // only close if we rolled back
				session.close();
		}

		// second transaction - fetch students for tutor from DB

		// -- NOTE: We still use the same session, thus the 'contains' check will be
		// successful. See what happen if we create a new session further down

		tx = session.beginTransaction();
		try {
			System.out.println();
			System.out.println("DEBUG: starting second transaction");
			System.out.println("DEBUG: Try to Get data from database.");
			System.out.println();

			Tutor tutorFromDatabase = (Tutor) session.get(Tutor.class, 1);

			Collection<Student> tutor1Students = tutorFromDatabase.getSupervisionGroup();
			System.out.println("Tutor " + tutorFromDatabase + " has students:");
			for (Student next : tutor1Students) {
				System.out.println(next);
			}

			boolean isBladrikInTheSetOfStudents = tutor1Students.contains(student2);
			System.out.println("isBladrikInTheSetOfStudents: " + isBladrikInTheSetOfStudents);

			System.out.println("DEBUG: Note that hibernate does not read from database but use cached data.");
			System.out.println("DEBUG: Note the value if isBladrikInTheSetOfStudents.");

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			// normally we throw an exception here
			System.out.println(e);
		} finally {
			if (session != null)
				System.out.println("Debug: Closing first session");
			session.close();
		}

		System.out.println("DEBUG: starting second session");
		session = sf.openSession();
		tx = session.beginTransaction();
		try {
			System.out.println();
			System.out.println("DEBUG: starting first transaction");
			System.out.println("DEBUG: Try to Get data from database.");
			System.out.println();

			Tutor tutorFromDatabase = (Tutor) session.get(Tutor.class, 1);

			Collection<Student> tutor1Students = tutorFromDatabase.getSupervisionGroup();
			System.out.println("Tutor " + tutorFromDatabase + " has students:");
			for (Student next : tutor1Students) {
				System.out.println(next);
			}

			boolean isBladrikInTheSetOfStudents = tutor1Students.contains(student2);
			System.out.println("isBladrikInTheSetOfStudents: " + isBladrikInTheSetOfStudents);

			System.out.println("DEBUG: Note that hibernate do read from database when we use a separate session.");
			System.out.println("DEBUG: Note the value if isBladrikInTheSetOfStudents.");

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			// normally we throw an exception here
			System.out.println(e);
		} finally {
			if (session != null)
				System.out.println("Closing second session");
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

package com.virtualpairprogrammers.testharness;

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
		Session session = sf.openSession();
		// database access requires a transaction object
		// there is a javax.transaction.Transaction method, NOT used with this code.

		org.hibernate.Transaction tx;

		tx = session.beginTransaction();
		try {
			// test - using Bi-directional many-to-many relationship

			Tutor tutor1 = new Tutor("DOO007", "James Bond", 3000000);
			Tutor tutor2 = new Tutor("BAL001", "Sortoff Baldrik", -100);

			Student student1 = new Student("Rowan Atkinson", "1-ROW-2011");
			Student student2 = new Student("Baldrik", "2-BAL-1782");
			Student student3 = new Student("Mr Bean", "3-BEA-2003");

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

			Subject course1 = new Subject("Black Adder I", 12);
			Subject course2 = new Subject("Black Adder II", 6);
			Subject course3 = new Subject("Black Adder III", 3);

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

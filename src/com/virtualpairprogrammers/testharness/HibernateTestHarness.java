package com.virtualpairprogrammers.testharness;

import org.apache.derby.tools.sysinfo;
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

			Student foundStudent = (Student) session.get(Student.class, 1);
			System.out.println(foundStudent);

			Tutor newSup = (Tutor) session.get(Tutor.class, 2);
			foundStudent.allocateSupervisor(newSup);

			Student foundStudent2 = (Student) session.get(Student.class, 2);
			foundStudent2.allocateSupervisor(null);

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

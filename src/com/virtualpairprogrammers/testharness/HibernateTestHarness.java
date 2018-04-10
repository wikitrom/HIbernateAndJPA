package com.virtualpairprogrammers.testharness;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.virtualpairprogrammers.domain.Student;

public class HibernateTestHarness {

	private static SessionFactory sessionFactory = null; // -- hibernate

	public static void main(String[] args) {

		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();
		Student myStudent;

		// Student testStudent = new Student("Jessica Ennis", "Toni Minichello");
		// System.out.println(testStudent + "has a grade pont of " + testStudent.calculateGradePointAverage());

		// db access requires a transaction object
		// there is a javax.transaction.Transaction method, NOT used with this code.
		org.hibernate.Transaction tx;

		// -- save an object to database

		// Student testStudent = new Student("Jessica Ennis", "Toni Minichello");
		// System.out.println("This student id: " + testStudent.getId());
		// tx = session.beginTransaction();
		// session.save(testStudent);
		// System.out.println("This student id is now: " + testStudent.getId());
		// tx.commit();

		// -- read an object to database
		tx = session.beginTransaction();
		myStudent = (Student) session.get(Student.class, 2);
		System.out.println(myStudent);
		tx.commit();

		// -- delete/remove an object from database

		tx = session.beginTransaction();
		session.delete(myStudent); // delete from DB, not memory
		System.out.println(myStudent);
		tx.commit();

		tx = session.beginTransaction();
		session.delete(myStudent); // delete from DB, not memory
		System.out.println(myStudent);
		tx.commit();

		session.close();

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

}

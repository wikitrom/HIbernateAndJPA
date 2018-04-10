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

		Student testStudent = new Student("Jessica Ennis", "Toni Minichello");
		System.out.println(testStudent + "has a grade pont of " + testStudent.calculateGradePointAverage());

		// save the student to the database -- using hibernate
		SessionFactory sf = getSessionFactory();
		Session session = sf.openSession();

		// db access -- requires a transaction object
		// there is a javax.transaction.Transaction method, NOT used with this code.
		org.hibernate.Transaction tx = session.beginTransaction();
		session.save(testStudent);
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

package com.jxyj.train.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class JDBCUtilsHbm {
	private static Session session=null;
	private static Transaction transaction=null;
	
	public static Session getSession(){
		Configuration config = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();

		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		session = sessionFactory.getCurrentSession();
		transaction = session.beginTransaction();
		return session;
	}
	
	public static void commitTran(){
		transaction.commit();
	}
}

package ambodenis.main.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ambodenis.main.entity.Contact;

@Repository
public class ContactDAOImpl implements ContactDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<Contact> getContactList() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Contact> query = session.createQuery("from Contact order by id DESC");
		
		return query.getResultList();
	}

	@Transactional
	@Override
	public void saveContact(Contact contact) {

		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(contact);
		
	}

	@Transactional
	@Override
	public void deleteContact(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Contact contact = session.get(Contact.class, id);
		
		session.delete(contact);
		
	}

	@Transactional
	@Override
	public Contact getContactById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Contact contact = session.get(Contact.class, id);
		
		return contact;
	}

}

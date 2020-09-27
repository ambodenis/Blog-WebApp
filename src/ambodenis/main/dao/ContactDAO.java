package ambodenis.main.dao;

import java.util.List;

import ambodenis.main.entity.Contact;

public interface ContactDAO {
	
	public List<Contact> getContactList();
	
	public void saveContact(Contact contact);
	
	public void deleteContact(int id);
	
	public Contact getContactById(int id);

}

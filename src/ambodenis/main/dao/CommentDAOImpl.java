package ambodenis.main.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ambodenis.main.entity.Blog;
import ambodenis.main.entity.Comment;

@Repository
public class CommentDAOImpl implements CommentDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public List<Comment> getCommentList() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Comment> query = session.createQuery("from Comment");
			
		return query.getResultList();
	}

	@Transactional
	@Override
	public void saveComment(Comment comment) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(comment);	
	}

	@Transactional
	@Override
	public void deleteComment(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Comment comment = session.get(Comment.class, id);
		
		session.delete(comment);
		
	}

	@Transactional
	@Override
	public Comment getCommentById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Comment comment = session.get(Comment.class, id);
		
		return comment;
	}

	@Transactional
	@Override
	public List<Comment> getCommentListByBlog(Blog blog) {
		
		Session session = sessionFactory.getCurrentSession();
		
		List<Comment> list = blog.getComments();
		
		return list;
	}

}

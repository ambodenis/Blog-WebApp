package ambodenis.main.dao;

import java.util.List;

import ambodenis.main.entity.Blog;
import ambodenis.main.entity.Comment;

public interface CommentDAO {
	
	public List<Comment> getCommentList();
	
	public void saveComment(Comment comment);
	
	public void deleteComment(int id);
	
	public Comment getCommentById(int id);
	
	public List<Comment> getCommentListByBlog(Blog blog);


}

package ambodenis.main.dao;

import java.util.List;

import ambodenis.main.entity.Blog;

public interface BlogDAO {
	
	public List<Blog> getBlogList();
	
	public Blog getBlog(int id);
	
	public void saveBlog(Blog blog);
	
	public void deleteBlog(int id);
	
	public List<Blog> blogListByCategory(int id, int page);
	
	public List<Blog> getBlogListByAuthor(String username, int page);
	
	public List<Blog> getThreeImportantBlogs();
	
	public List<List<Blog>> getTwelveBlogsForMainPage();
		
	public List<Blog> getMostReviewedBlogs();
		
	public List<Blog> getTwelveBlogsBySearch(String word, int page);
	
	public List<Blog> blogListForPagination(int page);
	
	public long getCountBlogs();
	
	public long getCountBlogsForSearch(String word);
	
	public long getCountBlogsForCategory(int id);
	
	public long getCountBlogByAuthor(String username);
	
	public Blog getBlogLeft(int id);
	
	public Blog getBlogRight(int id);
	
	public List<Blog> getBlogListOrder(int order);
	
	public List<Blog> getBlogListSearchBack(String word);
	
	public List<Blog> getPreviousAndNext(int id);

}

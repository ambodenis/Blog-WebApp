package ambodenis.main.dao;

import java.util.List;

import ambodenis.main.entity.Blog;
import ambodenis.main.entity.Tag;

public interface TagDAO {
	
	public List<Tag> getTagList();
	
	public void saveTag(Tag tag);
	
	public Tag getTag(int id);
	
	public void deleteTag(int id);
	
	public List<Tag> getTagListByIds(List<Integer> ids);
	
	public List<Tag> getTagsForFilter();

	public List<List<Blog>> getTagWithBlogs(int id);

}

package ambodenis.main.dao;

import java.util.List;

import ambodenis.main.entity.Slide;

public interface SlideDAO {
	
	public List<Slide> slideList();
	
	public void saveSlide(Slide slide);
	
	public void deleteSlide(int id);
	
	public Slide getSlideById(int id);
	
	public List<Slide> getSlideListForMainPage();

}

package ambodenis.main;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ambodenis.main.dao.BlogDAO;
import ambodenis.main.dao.CategoryDAO;
import ambodenis.main.dao.CommentDAO;
import ambodenis.main.dao.ContactDAO;
import ambodenis.main.dao.SlideDAO;
import ambodenis.main.dao.TagDAO;
import ambodenis.main.dao.UserDAO;
import ambodenis.main.entity.Blog;
import ambodenis.main.entity.Category;
import ambodenis.main.entity.ChangePassword;
import ambodenis.main.entity.Comment;
import ambodenis.main.entity.Contact;
import ambodenis.main.entity.Slide;
import ambodenis.main.entity.Tag;
import ambodenis.main.entity.User;

@Controller
@RequestMapping("/administration")
public class AdministrationController {
	
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private BlogDAO blogDAO;
	@Autowired
	private TagDAO tagDAO;
	@Autowired
	private SlideDAO slideDAO;
	@Autowired
	private ContactDAO contactDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private UserDAO userDAO;
	
	//CATEGORY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	
	@RequestMapping("/category-list")
	public String getCategoryList(Model model) {

		List<Category> list = categoryDAO.getCategoryList();

		model.addAttribute("categoryList", list);

		return "category-list";
	}
	
	@RequestMapping("/category-form")
	public String getCategoryForm(Model model) {

		Category category = new Category();

		model.addAttribute("category", category);

		return "category-form";
	}
	
	@RequestMapping("/category-form-update")
	public String getCategoryUpdateForm(@RequestParam int id, Model model) {

		Category category = categoryDAO.getCategory(id);

		model.addAttribute("category", category);

		return "category-form";
	}
	
	@RequestMapping("/category-save")
	public String saveCategory(@ModelAttribute Category category) {

		categoryDAO.saveCategory(category);

		return "redirect:/administration/category-list";
	}
	
	@RequestMapping("/category-delete")
	public String deleteCategory(@RequestParam int id) {

		categoryDAO.deleteCategory(id);

		return "redirect:/administration/category-list";
		
	}
		
	@RequestMapping("/go-front")
	public String goFront(Model model) throws ParseException {
			
		List<Blog> blogList = blogDAO.getThreeImportantBlogs();

		getDateTimeDifference(blogList);	

		model.addAttribute("slideList", slideDAO.getSlideListForMainPage());
		model.addAttribute("blogList", blogList);
		model.addAttribute("twelveList", blogDAO.getTwelveBlogsForMainPage());			
		model.addAttribute("categoryList", categoryDAO.getCategoryList());
			
		return "front/index-page";
		}
	
	//BLOG POST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	@RequestMapping("/blog-form")
	public String getBlogForm(Model model) {
		
		Blog blog = new Blog();
		
		List<Category> categoryList = categoryDAO.getCategoryList();
		List<Tag> tagList = tagDAO.getTagList();
		
		model.addAttribute("blog", blog);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("tagList", tagList);

		return "blog-form";
	}
	
	@RequestMapping("/blog-save")
	public String saveBlog(@Valid @ModelAttribute Blog blog, Principal principal, Model model, BindingResult result) {
		
		if(result.hasErrors()) {
			
			List<Category> categoryList = categoryDAO.getCategoryList();
			List<Tag> tagList = tagDAO.getTagList();
			
			model.addAttribute("categoryList", categoryList);
			model.addAttribute("tagList", tagList);
			
			return "blog-form";

		}
	
		blog.setDate();

		if (blog.getCategory().getId() != 0) {
			Category category = categoryDAO.getCategory(blog.getCategory().getId());
			blog.setCategory(category);
		} else {
			blog.setCategory(null);

		}
		
		User user = userDAO.getUserByUsername(principal.getName());
		blog.setUser(user);		

		List<Integer> ids = new ArrayList<Integer>();

		for (Tag tag : blog.getTags()) {
			ids.add(Integer.parseInt(tag.getName()));
		}
		List<Tag> tags = tagDAO.getTagListByIds(ids);	
		
		blog.setTags(tags);

		blogDAO.saveBlog(blog);

		return "redirect:/administration/blog-list";
	
	}
	
	@RequestMapping("/blog-list")
	public String getBlogList(Principal principal,Model model) {

		List<Blog> list = blogDAO.getBlogList();
		model.addAttribute("blogList", list);
		model.addAttribute("user", userDAO.getUserByUsername(principal.getName()));

		return "blog-list";
	}

	@RequestMapping("/blog-comments-list")
	public String getCommentsForBlog(@RequestParam int id, Model model) {

		model.addAttribute("commentList", commentDAO.getCommentListByBlog(blogDAO.getBlog(id)));

		return "comment-list";
	}

	@RequestMapping("/blog-form-update")
	public String updateBlog(@RequestParam int id, Model model) {

		model.addAttribute("blog", blogDAO.getBlog(id));
		model.addAttribute("tagList", tagDAO.getTagList());
		model.addAttribute("categoryList", categoryDAO.getCategoryList());

		return "blog-form";
	}

	@RequestMapping("blog-delete")
	public String deleteBlog(@RequestParam int id) {

		blogDAO.deleteBlog(id);
		return "redirect: blog-list";

	}
	
	@RequestMapping("/blog-important-change")
	public String changeImportant(@RequestParam int id) {
		
		Blog blog = blogDAO.getBlog(id);
		
		blog.setImportant(!blog.getImportant());
		
		blogDAO.saveBlog(blog);
		
		return "redirect: blog-list";
		
	}
	
	@RequestMapping("/blog-list-sort")
	public String getBlogListOrded(@RequestParam int order,Principal principal, Model model) {
		
		model.addAttribute("blogList", blogDAO.getBlogListOrder(order));
		model.addAttribute("user", userDAO.getUserByUsername(principal.getName()));
		
		return "blog-list";
	}
	
	@RequestMapping("/blog-list-search")
	public String searchBlog(@RequestParam String word,Principal principal, Model model) {
		
		model.addAttribute("blogList", blogDAO.getBlogListSearchBack(word));
		model.addAttribute("user", userDAO.getUserByUsername(principal.getName()));
		
		return "blog-list";
	}
		
	
	//TAG!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@RequestMapping("/tag-list")
	public String getTagList(Model model) {

		List<Tag> tagList = tagDAO.getTagList();

		model.addAttribute("tagList", tagList);

		return "tag-list";
	}

	
	@RequestMapping("/tag-save")
	public String saveTag(@ModelAttribute Tag tag) {

		tagDAO.saveTag(tag);

		return "redirect:/administration/tag-list";
	}
	
	@RequestMapping("tag-delete")
	public String deleteTag(@RequestParam int id) {

		tagDAO.deleteTag(id);

		return "redirect:/administration/tag-list";
	}
	
	@RequestMapping("/tag-form")
	public String getTagForm(Model model) {

		Tag tag = new Tag();
		model.addAttribute("tag", tag);
		return "tag-form";
	}

	@RequestMapping("/tag-form-update")
	public String getTagFormUpdate(@RequestParam int id, Model model) {

		Tag tag = tagDAO.getTag(id);

		model.addAttribute("tag", tag);

		return "tag-form";
	}
	
	//SLIDE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@RequestMapping("/slide-form")
	public String getSlideForm(Model model) {

		model.addAttribute("slide", new Slide());

		return "slide-form";
	}

	@RequestMapping("/slide-save")
	public String saveSlide(@ModelAttribute Slide slide) {

		slideDAO.saveSlide(slide);

		return "redirect: slide-list";
	}
	
	@RequestMapping("/slide-delete")
	public String deleteSlide(@RequestParam int id) {

		slideDAO.deleteSlide(id);

		return "redirect: slide-list";
	}
	
	@RequestMapping("/slide-list")
	public String getSlideList(Model model) {

		model.addAttribute("slideList", slideDAO.slideList());

		return "slide-list";
	}
	
	@RequestMapping("/slide-form-update")
	public String updateSlide(@RequestParam int id, Model model) {

		slideDAO.getSlideById(id);

		model.addAttribute("slide", slideDAO.getSlideById(id));

		return "slide-form";
	}

	@RequestMapping("/slide-visible")
	public String changeVisible(@RequestParam int id) {

		Slide slide = slideDAO.getSlideById(id);

		slide.setIsVisible(!slide.getIsVisible());

		slideDAO.saveSlide(slide);

		return "redirect: slide-list";
	}
	
	//CONTACT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	@RequestMapping("/contact-list")
	public String getContactList(Model model) {

		model.addAttribute("contactList", contactDAO.getContactList());

		return "contact-list";
	}

	@RequestMapping("/contact-seen")
	public String contactIsSeen(@RequestParam int id) {

		Contact contact = contactDAO.getContactById(id);

		contact.setIsSeen(true);

		contactDAO.saveContact(contact);

		return "redirect: contact-list";
	}

	@RequestMapping("/contact-delete")
	public String deleteContact(@RequestParam int id) {

		contactDAO.deleteContact(id);

		return "redirect: contact-list";
	}
	
	
	//COMMENT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	@RequestMapping("/comment-list")
	public String getCommentList(Model model) {

		model.addAttribute("commentList", commentDAO.getCommentList());

		return "comment-list";
	}
	
	@RequestMapping("/comment-delete")
	public String deleteComment(@RequestParam int id) {

		commentDAO.deleteComment(id);

		return "redirect: comment-list";
	}

	@RequestMapping("/comment-isOnPage")
	public String changeShowOnPage(@RequestParam int id) {

		Comment comment = commentDAO.getCommentById(id);

		comment.setIsOnPage(!comment.getIsOnPage());

		commentDAO.saveComment(comment);

		return "redirect: comment-list";
	}

	//USER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@RequestMapping("/user-list")
	public String getUserList(Principal principal,Model model) {

		model.addAttribute("userList", userDAO.getUserList());
		model.addAttribute("user", userDAO.getUserByUsername(principal.getName()));

		return "user-list";
	}
	
	@RequestMapping("/user-form")
	public String getUserForm(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("roles", userDAO.getRols());

		return "user-form";
	}

	@RequestMapping("/user-save")
	public String saveUser(@ModelAttribute User user, Model model) {

		String passwordEncode = new BCryptPasswordEncoder().encode(user.getPassword());

		user.setEnabled(true);
		user.setPassword("{bcrypt}" + passwordEncode);

		userDAO.saveUser(user);

		return "redirect: user-list";
	}

	@RequestMapping("/user-delete")
	public String saveUser(String username) {

		userDAO.deleteUser(username);

		return "redirect: user-list";
	}

	@RequestMapping("/user-edit-profile")
	public String getUserEditProfile(Principal principal, Model model) {

		model.addAttribute("user", userDAO.getUserByUsername(principal.getName()));

		return "user-edit-profile";
	}

	@RequestMapping("/user-edit")
	public String getUserEdit(@ModelAttribute User user) {

		userDAO.saveUser(user);

		return "redirect: user-list";
	}
	
	@RequestMapping("/user-enable")
	public String enableUser(@RequestParam String username) {

		userDAO.enableUser(username);

		return "redirect: user-list";
	}

	@RequestMapping("/user-change-password")
	public String getUserChangePassword(Principal principal, Model model) {

		model.addAttribute("changePassword", new ChangePassword());

		return "user-change-password";
	}

	@RequestMapping("/user-change-password-action")
	public String getUserChangePasswordAction(@ModelAttribute ChangePassword password, Principal principal,
			Model model) {

		if (password.getNewPassword().equalsIgnoreCase(password.getConfirmPassword())) {

			User user = userDAO.getUserByUsername(principal.getName());

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(password.getOldPassword(), user.getPassword().replace("{bcrypt}", ""))) {

				user.setPassword("{bcrypt}" + encoder.encode(password.getNewPassword()));

				userDAO.saveUser(user);

			} else {

				return "user-change-password";
			}
		} else {
			
			return "user-change-password";
		}

		return "redirect: user-list";
	}
	
	public static void getDateTimeDifference(List<Blog> blogList) throws ParseException {
		
		for (Blog blog : blogList) {
						
			Date dateNow = new Date();		 
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
			String now = simpleDateFormat.format(dateNow);
			
			Date dateObj1 = simpleDateFormat.parse(blog.getDate());
			Date dateObj2 = simpleDateFormat.parse(now);

			long diff = dateObj2.getTime() - dateObj1.getTime();
			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			
			blog.setDateDifference(diffDays);
		}		
	}
	
}



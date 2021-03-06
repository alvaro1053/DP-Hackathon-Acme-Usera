package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AdminService;
import services.CourseService;
import controllers.AbstractController;
import domain.Admin;
import domain.Course;

@Controller
@RequestMapping("/course/admin")
public class CourseAdminController extends AbstractController{

	// Services

			@Autowired
			private CourseService	courseService;
			
			@Autowired
			private AdminService	adminService;
	
	
			// Constructors

			public CourseAdminController() {
				super();
			}
			
			// Creation ---------------------------------------------------------------


	
	//Delete
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam final int courseId, final RedirectAttributes redir) {
		ModelAndView result;
	
			try {
				Course course = this.courseService.findOne(courseId);
				Admin admin = this.adminService.findByPrincipal();
				Assert.notNull(admin);
				this.courseService.deleteByAdmin(course);
				result = new ModelAndView("redirect:/course/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "course.commit.error";
				result = new ModelAndView("redirect:/course/list.do");
				redir.addFlashAttribute("message",errorMessage);
			}

		return result;
	}
	
	
	}
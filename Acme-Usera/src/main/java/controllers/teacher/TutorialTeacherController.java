package controllers.teacher;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.TeacherService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Teacher;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/teacher")
public class TutorialTeacherController extends AbstractController{
	
	// Services

	@Autowired
	private TutorialService	tutorialService;
	
	@Autowired
	private TeacherService	teacherService;

	// Constructors

	public TutorialTeacherController() {
		super();
	}
	
	
	@RequestMapping(value = "/refuse", method = RequestMethod.GET)
	public ModelAndView refuse(@RequestParam Integer tutorialId, RedirectAttributes redir) {
		ModelAndView result;
		try{
			Teacher principal = this.teacherService.findByPrincipal();
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.isTrue(principal.getTutorials().contains(tutorial));
			Date now = new Date();
			Assert.isTrue(tutorial.getStartTime().after(now));
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:/tutorial/list.do");	
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/tutorial/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}

		return result;
	}
	
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam Integer tutorialId, RedirectAttributes redir) {
		ModelAndView result;
		try{
			Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Teacher principal;
			principal = this.teacherService.findByPrincipal();
			if(principal.getId() != tutorial.getTeacher().getId()){
				result = new ModelAndView("redirect:/tutorial/list.do");
				redir.addFlashAttribute("message", "course.permision"); 
				return result;
			}
			
			Date now = new Date();
			Assert.isTrue(tutorial.getStartTime().after(now));
			if(tutorial.getStudent().getTutorials().contains(tutorial)){
				result = new ModelAndView("redirect:/tutorial/list.do");
				redir.addFlashAttribute("message", "course.permision"); 
				return result;
			}
			
			this.tutorialService.saveTutorialForStudent(tutorial);
			result = new ModelAndView("redirect:/tutorial/list.do");	
		} catch(Throwable oops){
			result = new ModelAndView("redirect:/tutorial/list.do");	
			redir.addFlashAttribute("message", "course.permision"); 
		}

		return result;
	}
}
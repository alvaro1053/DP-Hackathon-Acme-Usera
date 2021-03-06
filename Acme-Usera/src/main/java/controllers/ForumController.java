package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.ForumService;
import services.StudentService;
import domain.Actor;
import domain.Forum;
import domain.Question;
import domain.Student;
import domain.Teacher;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	
	// Services

		@Autowired
		private ForumService	forumService;
		
		@Autowired
		private StudentService	studentService;
		
		@Autowired
		private ActorService	actorService;
		
		// Constructors

		public ForumController() {
			super();
		}

		
		//Display
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int forumId, RedirectAttributes redir) {
			ModelAndView result;
			Forum forum;
			Collection<Question> questions;
			Actor principal;

			try{
				forum = this.forumService.findOne(forumId);
				questions = forum.getQuestions();
				principal = this.actorService.findByPrincipal();
				if(principal instanceof Student){
				String subscription = this.studentService.checkSubscription(forum.getCourse());
				Assert.isTrue(subscription.equals("STANDARD") || subscription.equals("PREMIUM"));
					
				} else if (principal instanceof Teacher){
				Teacher	teacher = (Teacher) principal;
					Assert.isTrue(teacher.getCoursesJoined().contains(forum.getCourse()));
				}

				result = new ModelAndView("forum/display");
				result.addObject("questions", questions);
				result.addObject("forum", forum);
				result.addObject("principal", principal);
				}catch (Throwable oops){
					result = new ModelAndView("redirect:/course/list.do");	
					redir.addFlashAttribute("message", "forum.permission"); 
			}
			return result;
		}
		

}

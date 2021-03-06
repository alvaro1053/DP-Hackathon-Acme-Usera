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
import services.AdvertisementService;
import services.CourseService;
import services.LessonService;
import domain.Actor;
import domain.Advertisement;
import domain.Course;
import domain.Lesson;
import domain.Sponsor;
import domain.Student;
import domain.Teacher;

@Controller
@RequestMapping("/lesson")
public class LessonController extends AbstractController{
	
	
	// Services

			@Autowired
			private LessonService	lessonService;
			
			@Autowired
			private ActorService	actorService;
			
			@Autowired
			private CourseService	courseService;
			
			@Autowired
			private AdvertisementService advertisementService;
		
			// Constructors

			public LessonController() {
				super();
			}
			
			
			
			//Display
			@RequestMapping(value = "/display", method = RequestMethod.GET)
			public ModelAndView display(@RequestParam final int lessonId, RedirectAttributes redir) {
				ModelAndView result = new ModelAndView();
				Lesson lesson;
				Advertisement advertChoosen;
				Actor principal;

				try{
				lesson = this.lessonService.findOne(lessonId);
				principal = this.actorService.findByPrincipal();
				advertChoosen = this.advertisementService.findRandomAdvertisement(lesson.getCourse());
				result = new ModelAndView("lesson/display");
				
				if (principal instanceof Student) {
					Collection<Course> subscribed = this.courseService.selectCoursesSubscriptedByUser(principal.getId()); 
					Assert.isTrue(subscribed.contains(lesson.getCourse()));
					Collection<Course> subFree = this.courseService.findCoursesSubscribedFreeByUser(principal.getId());
					if (subFree.contains(lesson.getCourse())){
						result.addObject("advert", advertChoosen);
					}
				} else {
					result.addObject("advert", advertChoosen);	
				}
				if(principal instanceof Teacher){
					Teacher principalT = (Teacher) principal;
					Assert.isTrue(principalT.getCoursesJoined().contains(lesson.getCourse()));
		} 
		 else if (principal instanceof Sponsor){
			Collection<Course> coursesWithAds = this.courseService.findCoursesWithAdsPlacedBySponsor();
			Assert.isTrue(coursesWithAds.contains(lesson.getCourse()));
		} else {
			
		}
				result.addObject("lesson", lesson);
				result.addObject("principal", principal);
			
				}catch (Throwable oops){
					result = new ModelAndView("redirect:/course/list.do");	
					redir.addFlashAttribute("message", "lesson.permision"); 
				}
				
				return result;

		}

}


package controllers.teacher;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Teacher;

@Controller
@RequestMapping("/curriculum/teacher")
public class CurriculumTeacherController extends AbstractController {

	// Services

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private TeacherService			teacherService;

	// Constructors

	public CurriculumTeacherController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView result;
		final Teacher principal;
		Curriculum curriculum;

		principal = this.teacherService.findByPrincipal();
		curriculum = principal.getCurriculum();

		if (curriculum != null)
			result = new ModelAndView("redirect:display.do");
		else
			result = new ModelAndView("curriculum/noCurriculum");
		return result;

	}

	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Curriculum curriculum;
		final Teacher principal;

		curriculum = this.curriculumService.findCurriculumByPrincipal();
		principal = this.teacherService.findByPrincipal();

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("principal", principal);

		return result;

	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.create();

		result = this.createEditModelAndView(curriculum);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		result = this.createEditModelAndView(curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		final ModelAndView result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("principal", principal);

		result.addObject("message", messageCode);

		return result;

	}

}

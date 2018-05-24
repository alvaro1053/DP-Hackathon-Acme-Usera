
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
import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/teacher")
public class ProfessionalRecordTeacherController extends AbstractController {

	// Services

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	// Constructors

	public ProfessionalRecordTeacherController() {
		super();
	}

	// Listing

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		ProfessionalRecord pr;

		pr = this.professionalRecordService.create();

		result = this.createEditModelAndView(pr);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord pr;

		pr = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(pr);
		result = this.createEditModelAndView(pr);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:/curriculum/teacher/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, "pr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:/curriculum/teacher/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "pr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final ProfessionalRecord pr) {
		ModelAndView result;

		result = this.createEditModelAndView(pr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord pr, final String messageCode) {
		final ModelAndView result;
		Curriculum curriculum;
		Boolean permission = false;

		curriculum = this.curriculumService.findCurriculumByPrincipal();

		if (pr.getId() == 0)
			permission = true;
		else
			for (final ProfessionalRecord profRec : curriculum.getProfessionalRecords())
				if (pr.getId() == profRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", pr);
		result.addObject("curriculum", curriculum);
		result.addObject("permission", permission);

		result.addObject("message", messageCode);

		return result;

	}

}

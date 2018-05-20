
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/teacher")
public class MiscellaneousRecordTeacherController extends AbstractController {

	// Services

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors

	public MiscellaneousRecordTeacherController() {
		super();
	}

	// Listing

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		MiscellaneousRecord mr;

		mr = this.miscellaneousRecordService.create();

		result = this.createEditModelAndView(mr);

		return result;

	}
	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		final MiscellaneousRecord mr;

		mr = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(mr);
		result = this.createEditModelAndView(mr);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/curriculum/teacher/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "mr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/curriculum/teacher/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "mr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord mr) {
		ModelAndView result;

		result = this.createEditModelAndView(mr, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord mr, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Curriculum curriculum;

		curriculum = this.curriculumService.findCurriculumByPrincipal();

		if (mr.getId() == 0)
			permission = true;
		else
			for (final MiscellaneousRecord miscRec : curriculum.getMiscellaneousRecord())
				if (mr.getId() == miscRec.getId()) {
					permission = true;
					break;
				}

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", mr);
		result.addObject("permission", permission);
		result.addObject("curriculum", curriculum);

		result.addObject("message", messageCode);

		return result;

	}

}

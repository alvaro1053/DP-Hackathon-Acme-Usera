package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import domain.Admin;
import forms.EditActorForm;

@Controller
@RequestMapping("/admin")
public class AdminProfileController {
	
	@Autowired
	AdminService		adminService;
	
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(){
		ModelAndView result;
		Admin principal;
		
		principal = this.adminService.findByPrincipal();
		
		result = new ModelAndView("actor/display");
		result.addObject("actor", principal);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		EditActorForm editActorForm;
		Admin principal;
		
		editActorForm = new EditActorForm();
		principal = this.adminService.findByPrincipal();
			
		editActorForm = this.adminService.construct(editActorForm, principal);
		
		result = this.createEditModelAndView(editActorForm);
		
		return result;
		
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView edit(final EditActorForm editActorForm, BindingResult binding){
		ModelAndView result;
		Admin admin;
				
		admin = this.adminService.reconstruct(editActorForm, binding);
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(editActorForm);
		}else{
			try{
				this.adminService.save(admin);
				result = new ModelAndView("redirect:/admin/display.do");
			}catch (Throwable oops){
				result = this.createEditModelAndView(editActorForm);
			}
		}
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(EditActorForm editActorForm) {
		ModelAndView result;
		
		result = this.createEditModelAndView(editActorForm, null);
		
		return result;
	}

	protected ModelAndView createEditModelAndView(EditActorForm editActorForm,
			String message) {
		ModelAndView result;
		String requestURI;
		Admin principal;
		Boolean permiso;
		
		permiso = false;
		
		principal = this.adminService.findByPrincipal();
		
		if(principal.getId() == editActorForm.getId()){
			permiso = true;
		}
		
		requestURI = "student/student/editProfile.do";
		
		result = new ModelAndView("actor/edit");
		result.addObject("editActorForm", editActorForm);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("permiso", permiso);
		
		
		return result;
	}
	
	

}

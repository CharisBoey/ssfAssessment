package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Register;

@Controller
@RequestMapping("/events")
public class RegistrationController {
    

    // TODO: Task 6
    @GetMapping("/register{email}")
	public String registerPage(Model model){

        Register register = new Register();
        model.addAttribute("register", register);

		return "eventregister";
	}

    @PostMapping("/register")
    // public ModelAndView registerProcessing(@Valid @ModelAttribute(register) Register register, BindingResult bindings, HttpSession sess,@RequestParam String username, @RequestBody String body) {
    public ModelAndView registerProcessing(@Valid @ModelAttribute("register") Register register, BindingResult bindings, @RequestBody String body) {

        ModelAndView mav = new ModelAndView("eventregister");
        
        if (bindings.hasErrors()) {
        mav.setStatus(HttpStatusCode.valueOf(400));
        return mav;
      }

      mav.setStatus(HttpStatusCode.valueOf(200));
      return mav;
    }


    @PostMapping("/success")
    public String success(){
        return "success";
    }

    // TODO: Task 7
}

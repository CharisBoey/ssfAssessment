package vttp.ssf.assessment.eventmanagement.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Register;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping("/events")
public class RegistrationController {
    
    @Autowired
    RedisRepository redisRepo;

    @Autowired
    DatabaseService databaseSvc;


    // TODO: Task 6
    @GetMapping("/register/{eventId}")
	public String registerPage(Model model, @PathVariable("eventId") String eventId, HttpSession sess){
        Register register = new Register();

        model.addAttribute("register", register);
        int indexInRedis = Integer.parseInt(eventId)-1;
        Event chosenEvent = redisRepo.getEvent(indexInRedis);
        model.addAttribute("chosenEvent", chosenEvent);
        model.addAttribute("chosenEventName", chosenEvent.getEventName());
        model.addAttribute("chosenEventDate", chosenEvent.getEventDate());
        sess.setAttribute("indexInRedis", indexInRedis);
		return "eventregister";
	}

    @PostMapping("/register/{eventId}")
    public String registerProcessing(@Valid @ModelAttribute("register") Register register, BindingResult bindings) {
        
        if (bindings.hasErrors()) {
        return "eventregister";
      }
    
      return "redirect:/events/registration/register";
    }

    

    // TODO: Task 7
    @PostMapping("/registration/register")
    public String success(Model model, HttpSession sess, @ModelAttribute ("register") Register register){

        Integer indexRedis = (Integer) sess.getAttribute("indexInRedis");
        Event chosenEvent = redisRepo.getEvent(indexRedis);

        String chosenEventName = chosenEvent.getEventName();
        Long chosenEventDate = chosenEvent.getEventDate();

        model.addAttribute("chosenEventName", chosenEventName);
        model.addAttribute("chosenEventDate", chosenEventDate);

        Integer chosenEventSize = chosenEvent.getEventSize();
        Integer chosenEventParticipant = chosenEvent.getParticipants();
        Integer chosenEventAvailability = chosenEventSize - chosenEventParticipant;

        Register registered = register;

        if(databaseSvc.ageRequirement(registered) && databaseSvc.sizeRequirement(chosenEventAvailability, registered.getNoOfTicketsRequested())){
            Event savedEvent = chosenEvent;
            int totalTickets = chosenEventAvailability + registered.getNoOfTicketsRequested();
            savedEvent.setParticipants(totalTickets);
            redisRepo.replaceEvent(indexRedis, savedEvent);
            return "SuccessRegistration";
        } else {

            if(!databaseSvc.ageRequirement(registered)){
                String reason = "You did not meet the minimum age requirement.";
                model.addAttribute("reason", reason);
                return "ErrorRegistration";
            }
            if(!databaseSvc.sizeRequirement(chosenEventSize, chosenEventParticipant)){
                String reason = "Your request for tickets exceeded the event size.";
                model.addAttribute("reason", reason);
                return "ErrorRegistration";
            }
        } 
        

        String reason = "Unknown Failure";
        model.addAttribute("reason", reason);
        return "ErrorRegistration";
    }
}

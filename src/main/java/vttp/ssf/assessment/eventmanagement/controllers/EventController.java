package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Controller
@RequestMapping("/events")
public class EventController {

	//TODO: Task 5

	@Autowired
	RedisRepository redisRepo;


	@GetMapping("/listing")
	public String displayEvents(Model model){
		//, MultiValueMap<String, String> mvm, HttpSession sess
		int noOfEvents = redisRepo.getNumberOfEvents();
		//gg by id*
		List<Event> eventList = new LinkedList<>();
		for(int i=0; i<noOfEvents; i++){
			Event event = new Event();
			event = redisRepo.getEvent(i);
			eventList.add(event);
		}
		
		model.addAttribute("eventList", eventList);
		// String eventIdSelected = mvm.getFirst("eventId");
		// sess.setAttribute("eventIdSelected", eventIdSelected);
		return "view0";
	}

}

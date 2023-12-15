package vttp.ssf.assessment.eventmanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner{

	@Autowired
	DatabaseService databaseSvc;

	@Autowired
	RedisRepository redisRepo;

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	
	// TODO: Task 1
	@Override
	public void run(String... args) throws Exception{
		String pathFileName = "C:\\Users\\chari\\Desktop\\ssfAssessment\\";
		String fileName = "events.json";
		pathFileName = pathFileName+fileName;

		//TO CHECK THAT DATA HAS BEEN PASSED!!!
		/* List<Event> checkList = databaseSvc.readFile(pathFileName);
		
		for(Event event: checkList){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!()");
			System.out.println(event.getEventId());
			System.out.println(event.getEventName());
			System.out.println(event.getEventSize());
			System.out.println(event.getEventDate());
			System.out.println(event.getParticipants());
			
		} */

		List<Event> eventListFromFile = databaseSvc.readFile(pathFileName);

		for(Event event : eventListFromFile){
			redisRepo.saveRecord(event);
		}

		// Event event1 = redisRepo.getEvent(0);
		// System.out.println("!!!!!!!!!!!!!!"+ event1.getEventName());

		// Event event1 = redisRepo.getEvent(1);
		// System.out.println("!!!!"+ event1.getEventName());

	}

}

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

		List<Event> eventListFromFile = databaseSvc.readFile(pathFileName);

		int largestIndex = eventListFromFile.size()-1;
		//prevent dupicates 
		redisRepo.deleteRedis();
		for (int i=largestIndex; i>=0; i--){
			redisRepo.saveRecord(eventListFromFile.get(i));
		}

	}

}

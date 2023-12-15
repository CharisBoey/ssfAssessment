package vttp.ssf.assessment.eventmanagement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired @Qualifier("redis")
    private RedisTemplate<String, String> template;

	// TODO: Task 2
	public void saveRecord(Event event){
		ListOperations<String, String> savedEvent = template.opsForList();

		String eventInString = "%d,%s,%d,%d,%d".formatted(event.getEventId(), event.getEventName(), event.getEventSize(), event.getEventDate(), event.getParticipants());

		savedEvent.leftPush("event", eventInString);
	}

	// TODO: Task 3

	public Integer getNumberOfEvents(){
		ListOperations<String, String> redisOutput = template.opsForList();
		
		Long number = redisOutput.size("event");
		int noOfEvents = number.intValue();
		return noOfEvents;
	}
	// TODO: Task 4

	//DONT NEED TO CHANGE INDEX(+1), CAUSE RETRN AN EVENT OBJECT AT THE PARTICULAR INDEX FROM THE EVENT LIST IN REDIS
	public Event getEvent(Integer index){
		ListOperations<String, String> redisOutputList = template.opsForList();
		Long indexLong = index.longValue();
		String redisOutput = redisOutputList.index("event", indexLong);
		Event event = new Event();
		String[] terms = redisOutput.split(",");
		event.setEventId(Integer.parseInt(terms[0]));
		event.setEventName(terms[1]);
		event.setEventSize(Integer.parseInt(terms[2]));
		event.setEventDate(Long.parseLong(terms[3]));
		event.setParticipants(Integer.parseInt(terms[4]));
		return event;
	}

	public void deleteRedis(){
		template.delete("event");
	}

	public void replaceEvent(Integer index, Event newEvent){
		ListOperations<String, String> redisOutputList = template.opsForList();
		Long indexLong = index.longValue();

		String newEventString = "%d,%s,%d,%d,%d".formatted(newEvent.getEventId(), newEvent.getEventName(), newEvent.getEventSize(), newEvent.getEventDate(), newEvent.getParticipants());
		redisOutputList.set("event", indexLong, newEventString);
	
	}

	
}
package vttp.ssf.assessment.eventmanagement.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired @Qualifier("redis")
    private RedisTemplate<String, String> template;

	// TODO: Task 2

	//SAVE AS INDIV EVENT
	/* public void saveRecord(Event event){
		ValueOperations<String, String> savedEvent = template.opsForValue();

		String eventInString = "%d,%s,%d,%d,%d".formatted(event.getEventId(), event.getEventName(), event.getEventSize(), event.getEventDate(), event.getParticipants());

		savedEvent.set(event.getEventId().toString(), eventInString);
	} */

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

	/* public Event getEvent(Integer index){
		String redisOutput = template.opsForValue().get(index.toString());
		// String eventId = index.toString();
		// Long size = redisOutput.size(eventId);
		Event event = new Event();
		String[] terms = redisOutput.split(",");
		event.setEventId(Integer.parseInt(terms[0]));
		event.setEventName(terms[1]);
		event.setEventSize(Integer.parseInt(terms[2]));
		event.setEventDate(Long.parseLong(terms[3]));
		event.setParticipants(Integer.parseInt(terms[4]));
		return event;
	}
 */

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
}
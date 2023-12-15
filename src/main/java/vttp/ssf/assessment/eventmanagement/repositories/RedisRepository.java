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

	public void saveRecord(Event event){
		ValueOperations<String, String> savedEvent = template.opsForValue();

		String eventInString = "%d,%s,%d,%d,%d".formatted(event.getEventId(), event.getEventName(), event.getEventSize(), event.getEventDate(), event.getParticipants());

		savedEvent.set(event.getEventId().toString(), eventInString);
	}

	// TODO: Task 3

	public Integer getNumberOfEvents(){
		// ListOperations<String, String> redisOutput = template.opsForList();

		// redisOutput.lastIndexOf(null, null);
		return 4;
	}
	// TODO: Task 4

	public Event getEvent(Integer index){
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

}

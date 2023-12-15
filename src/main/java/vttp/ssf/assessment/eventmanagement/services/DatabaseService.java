package vttp.ssf.assessment.eventmanagement.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Service
public class DatabaseService {
    
    // TODO: Task 1 
    public List<Event> readFile(String fileName) throws Exception{
    
        File file = new File(fileName);
        InputStream is = new FileInputStream(file);
        StringBuilder resultsfromFile = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line = "";
            while((line = br.readLine())!=null){
                resultsfromFile.append(line);
            }
        }

        String data = resultsfromFile.toString();
        System.out.println(data);

        List<Event> eventListFromFile = new LinkedList<>();

        JsonReader jsonReader = Json.createReader(new StringReader(data));
        JsonArray eventArray = jsonReader.readArray();
        for(int i=0; i<eventArray.size(); i++){
            JsonObject indivEventContents = eventArray.get(i).asJsonObject();

            Event retrievedEvent = new Event();
            retrievedEvent.setEventId(indivEventContents.getInt("eventId"));
            retrievedEvent.setEventName(indivEventContents.getString("eventName"));
            retrievedEvent.setEventSize(indivEventContents.getInt("eventSize"));
            
            JsonNumber num = indivEventContents.getJsonNumber("eventDate");

            Long longNum = num.longValue();

            // Long date = Long.parseLong(indivEventContents.getString("eventDate"));
            // retrievedEvent.setEventDate(date);
            retrievedEvent.setEventDate(longNum);
            retrievedEvent.setParticipants(indivEventContents.getInt("participants"));

            eventListFromFile.add(retrievedEvent);
        }
        return eventListFromFile;
    }
}
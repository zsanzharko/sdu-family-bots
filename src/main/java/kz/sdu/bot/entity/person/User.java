package kz.sdu.bot.entity.person;

import kz.sdu.bot.eventsBot.service.EventService;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class User {

    private String name;        // user name
    private String surname;     // user surname
    private String studentID;   // student id

    private EventService eventService = new EventService(); // service that works with events

    public User() {}

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(String name, String surname, String studentID, EventService eventService) {
        this.name = name;
        this.surname = surname;
        this.studentID = studentID;
        this.eventService = eventService;
    }

    public static boolean studentIDChecking(String studentID) {
        Pattern pattern = Pattern.compile("^[0-9]{9}$");
        Matcher matcher = pattern.matcher(studentID);
        return matcher.find();
    }


}

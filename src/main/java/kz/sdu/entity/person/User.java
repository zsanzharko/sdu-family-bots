package kz.sdu.entity.person;

import kz.sdu.bot.eventsBot.service.EventService;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name; // user name

    @Column(name = "surname")
    private String surname;     // user surname

    @Column(name = "student_id")
    private String studentID;   // student id

    @Transient
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

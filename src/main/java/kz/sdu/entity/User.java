package kz.sdu.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
@Entity(name = "User")
@Table(name = "Users")
public class User extends AbstractBaseEntity {

    @Column(name = "name")
    private String name; // user name

    @Column(name = "surname")
    private String surname;     // user surname

    @Column(name = "student_id")
    private String studentID;   // student id

    @Column(name = "email")
    private String email; // email

    @Column(name = "phone_number")
    private String phoneNumber; // phone number

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "telegram_id")
    private TelegramAccount telegramAccount;

    @Transient
    private List<Event> favoriteEvents;
    @Transient
    private List<Event> registeredEvents;

    @Transient
    private List<LostItem> lostItems;
    @Transient
    private List<LostItem> foundedItems;

    public User() {}

    public User(TelegramAccount telegramAccount, String name, String surname) {
        this.telegramAccount = telegramAccount;
        this.name = name;
        this.surname = surname;
    }

    public User(TelegramAccount telegramAccount, String name, String surname, String studentID) {
        this.telegramAccount = telegramAccount;
        this.name = name;
        this.surname = surname;
        this.studentID = studentID;
    }

    public User(TelegramAccount telegramAccount) {
        this.telegramAccount = telegramAccount;
    }

    public User(String name, String surname, String studentID, String email, String phoneNumber,
                TelegramAccount telegramAccount, List<Event> favoriteEvents,
                List<Event> registeredEvents, List<LostItem> lostItems, List<LostItem> foundedItems) {
        this.name = name;
        this.surname = surname;
        this.studentID = studentID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.telegramAccount = telegramAccount;
        this.favoriteEvents = favoriteEvents;
        this.registeredEvents = registeredEvents;
        this.lostItems = lostItems;
        this.foundedItems = foundedItems;
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

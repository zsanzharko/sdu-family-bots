package kz.sdu.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "User")
@Table(name = "Users")
public class User extends AbstractBaseEntity {

    @Column(name = "name")
    private String name; // user name

    @Column(name = "surname")
    private String surname;     // user surname

    @Column(name = "bio")
    private String bio;

    @Column(name = "email")
    private String email; // email

    @Column(name = "phone_number")
    private String phoneNumber; // phone number

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "telegram_id", referencedColumnName = "telegram_id")
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

    public User(TelegramAccount telegramAccount, String name, String surname, Student student) {
        this.telegramAccount = telegramAccount;
        this.name = name;
        this.surname = surname;
        this.student = student;
    }

    public User(TelegramAccount telegramAccount) {
        this.telegramAccount = telegramAccount;
    }

    public User(String name, String surname, Student student, String email, String phoneNumber,
                TelegramAccount telegramAccount, List<Event> favoriteEvents,
                List<Event> registeredEvents, List<LostItem> lostItems, List<LostItem> foundedItems) {
        this.name = name;
        this.surname = surname;
        this.student = student;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.telegramAccount = telegramAccount;
        this.favoriteEvents = favoriteEvents;
        this.registeredEvents = registeredEvents;
        this.lostItems = lostItems;
        this.foundedItems = foundedItems;
    }
}

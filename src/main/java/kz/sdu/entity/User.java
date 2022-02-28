package kz.sdu.entity;

import kz.sdu.entity.abstractBase.AbstractBaseEntity;
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

    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false)
    @JoinColumn(name = "telegram_id", referencedColumnName = "telegram_id")
    private TelegramAccount telegramAccount;

    @Transient
    private List<Event> favoriteEvents;
    @Transient
    private List<Event> registeredEvents;

    @Transient
    private List<LostAbstractItem> lostItems;
    @Transient
    private List<LostAbstractItem> foundedItems;

    @OneToMany(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<Product> sellingProduct;

    public User() {
    }

    public User(TelegramAccount telegramAccount, String name, String surname) {
        this.telegramAccount = telegramAccount;
        this.name = name;
        this.surname = surname;

        this.student = new Student();
    }

    public User(TelegramAccount telegramAccount, String name, String surname, Student student) {
        this.telegramAccount = telegramAccount;
        this.name = name;
        this.surname = surname;
        this.student = student == null ? new Student() : student;
    }

    public User(TelegramAccount telegramAccount) {
        this.telegramAccount = telegramAccount;
    }

    public User(String name, String surname, Student student, String email, String phoneNumber,
                TelegramAccount telegramAccount, List<Event> favoriteEvents,
                List<Event> registeredEvents, List<LostAbstractItem> lostItems, List<LostAbstractItem> foundedItems) {
        this.name = name;
        this.surname = surname;
        this.student = student == null ? new Student() : student;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.telegramAccount = telegramAccount;
        this.favoriteEvents = favoriteEvents;
        this.registeredEvents = registeredEvents;
        this.lostItems = lostItems;
        this.foundedItems = foundedItems;
    }

    public void addTelegramAccount(TelegramAccount account) {
        if (account != null) {
            this.telegramAccount = account;
        }
    }
}

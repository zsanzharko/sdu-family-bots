package kz.sdu.bot.entity.person;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.ChatLocation;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote
 * Class Account creates. It will connect with database and other bots to realizing(ecosystem)
 * Account is main class, with all information about user and personal information;
 * Class give information to work in bots.
 * {@link #chatId} chat id in Telegram
 * {@link #activity} gives latest activity in telegram Bot
 *
 *
 * @version 1.0
 * @author Sanzhar Zhanibekov
 */

@Data
public class Account {
    private static List<Account> accountList = new ArrayList<>();

    private final User user;        // user with all communication
    private final String chatId;    // chat ID bot to account

    @NotNull
    private final Long ID;          // unify telegram id account
    private String username;        // telegram username

    private ChatLocation chatLocation;                       // when account was the latest
    private AccountActivity activity = new AccountActivity();   // latest account activity with messages

    public Account(Long ID, String chatId, String username, String name, String surname) {
        this.ID = ID;
        this.username = username;
        this.chatId = chatId;
        this.user = new User(name, surname);
    }

    public Account(Long ID, String chatId) {
        this.ID = ID;
        this.username = "null";
        this.chatId = chatId;
        this.user = new User();
    }

    public Account(Long ID, String chatId, String username, ChatLocation chatLocation, String name, String surname) {
        this.ID = ID;
        this.username = username;
        this.chatLocation = chatLocation;
        this.chatId = chatId;
        this.user = new User(name, surname);
    }

    // This constructor will use when we wil add database
    public Account(Long ID, String username, ChatLocation chatLocation, String chatId,
                   AccountActivity activity, String name, String surname) {
        this.ID = ID;
        this.username = username;
        this.user = new User(name, surname);
        this.chatLocation = chatLocation;
        this.chatId = chatId;
        this.activity = activity;
    }

    public String getInformationAccount() {
        return "Name: " + getUser().getName() + "\n" +
                "Surname: " + getUser().getSurname() + "\n" +
                "student ID: " + getUser().getStudentID() + "\n" +
                "Liked Event: " + getUser().getEventService().getFavoriteEvent().size();
    }

    public static List<Account> getAccountList() {
        return accountList;
    }

    public static void setAccountList(List<Account> accountList) {
        Account.accountList = accountList;
    }

    public boolean setInfoEdit(String command, String text) {
        text = text.trim();
        switch (command) {
            case "/edit_account_name" -> getUser().setName(text);
            case "/edit_account_surname" -> getUser().setSurname(text);
            case "/edit_account_student_id" -> {
                if (User.studentIDChecking(text))
                    getUser().setStudentID(text);
                else return false;
            }
        }
        return true;
    }
}

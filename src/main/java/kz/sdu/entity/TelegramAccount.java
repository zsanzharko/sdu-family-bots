package kz.sdu.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.telegram.telegrambots.meta.api.objects.ChatLocation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @apiNote
 * Class TelegramAccount creates. It will connect with database and other bots to realizing(ecosystem)
 * TelegramAccount is main class, with all information about user and personal information;
 * Class give information to work in bots.
 * {@link #chatId} chat id in Telegram
 * {@link #activity} gives latest activity in telegram Bot
 *
 *
 * @version 1.0
 * @author Sanzhar Zhanibekov
 */

@Getter
@Setter
@ToString
@Entity (name = "TelegramAccount")
@Table(name = "telegram_accounts")
public class TelegramAccount extends AbstractBaseEntity {
    @OneToOne(mappedBy = "telegramAccount")
    private User user;

    private String chatId;          // chat ID bot to account

    @NotNull
    private Long id;                // unify telegram id account
    private String username;        // telegram username

    @Transient
    private ChatLocation chatLocation;                          // when account was the latest
    @Transient
    private TelegramAccountActivity activity = new TelegramAccountActivity();   // latest account activity with messages

    public TelegramAccount(Long id, String chatId, String username) {
        this.id = id;
        this.username = username;
        this.chatId = chatId;
    }

    public TelegramAccount(Long id, String chatId) {
        this.id = id;
        this.username = "null";
        this.chatId = chatId;
    }

    public TelegramAccount(Long id, String chatId, String username, ChatLocation chatLocation) {
        this.id = id;
        this.username = username;
        this.chatLocation = chatLocation;
        this.chatId = chatId;
    }

    public TelegramAccount() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TelegramAccount telegramAccount = (TelegramAccount) o;
        return id != null && Objects.equals(id, telegramAccount.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

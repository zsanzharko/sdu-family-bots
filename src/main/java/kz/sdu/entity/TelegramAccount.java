package kz.sdu.entity;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.ChatLocation;

import javax.persistence.*;

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
@Entity
@Table(name = "telegram_accounts")
public class TelegramAccount extends AbstractBaseEntity {

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "telegramAccount")
    private User user;

    @Column(name = "chat_id")
    private String chatId;          // chat ID bot to account

    @Column(name = "username")
    private String username;        // telegram username

    @Column(name = "telegram_id")
    private Long telegramId;


    @Transient
    private ChatLocation chatLocation;                          // when account was the latest
    @Transient
    private TelegramAccountActivity activity = new TelegramAccountActivity();   // latest account activity with messages

    public TelegramAccount(Long id, String chatId, String username) {
        this.id = id;
        this.username = username;
        this.chatId = chatId;
    }

    public TelegramAccount() {

    }
}

package kz.sdu.entity;

import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

@Data
public class TelegramAccountActivity {
    private SendMessage latestMessage;
    private SendPhoto latestPhoto;
    private SendDocument latestDocument;
    private SendAnimation latestAnimation;
    private SendAudio latestAudio;
    private DeleteMessage deleteMessage;

    private Integer latestMessageId;

    public TelegramAccountActivity() {}

// --Commented out by Inspection START (2/5/2022 7:18 PM):
//    public TelegramAccountActivity(SendMessage latestMessage,
//                           SendPhoto latestPhoto, SendDocument latestDocument,
//                           SendAnimation latestAnimation, SendAudio latestAudio) {
//        this.latestMessage = latestMessage;
//        this.latestPhoto = latestPhoto;
//        this.latestDocument = latestDocument;
//        this.latestAnimation = latestAnimation;
//        this.latestAudio = latestAudio;
//    }
// --Commented out by Inspection STOP (2/5/2022 7:18 PM)
}

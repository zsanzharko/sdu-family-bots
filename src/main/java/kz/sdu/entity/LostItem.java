package kz.sdu.entity;

import kz.sdu.repository.ILostItem;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="lost_items")
@AttributeOverrides({
        @AttributeOverride(name="name", column = @Column(name = "name")),
        @AttributeOverride(name="description", column = @Column(name = "description"))
})
public class LostItem extends Item implements ILostItem {
    @Transient
    private User user;

    @Transient
    private List<InputFile> photos;

    @Column(name = "is_lost")
    private Boolean isLost = true;

    public LostItem(User user, List<InputFile> photos, String name, String description) {
        super(name, description);
        this.user = user;
        this.isLost = true;

        if (photos.isEmpty())
            this.photos = null;
        else this.photos = photos;
    }

    public LostItem(User user, InputFile photo, String name, String description) {
        super(name, description);
        this.user = user;
        this.isLost = true;

        if (photos == null)
            this.photos = null;
        else {
            this.photos = new ArrayList<>();
            this.photos.add(photo);
        }
    }

    public LostItem() {
    }

    @Override
    public void foundItemUser(TelegramAccount telegramAccount) {

    }

    @Override
    public String getDetails() {
        return "Telegram telegramAccountModel id: " + user.getTelegramAccount().getId() + "\n" +
                "Telegram username: " + user.getTelegramAccount().getUsername() + "\n" +
                "Losing item name: " + getName() + "\n" +
                "Losing item description" + getDescription() + "\n" +
                "Founded losing user phone number: " + user.getPhoneNumber();

    }
}

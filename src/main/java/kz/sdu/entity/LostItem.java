package kz.sdu.entity;

import kz.sdu.impl.LostedItem;
import kz.sdu.entity.person.Account;
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
public class LostItem extends Item implements LostedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Transient
    private Account account;

    @Transient
    private List<InputFile> photos;

    @Column(name = "is_lost")
    private Boolean isLost = true;

    public LostItem(Account account, List<InputFile> photos, String name, String description) {
        super(name, description);
        this.account = account;
        this.isLost = true;

        if (photos.isEmpty())
            this.photos = null;
        else this.photos = photos;
    }

    public LostItem(Account account, InputFile photo, String name, String description) {
        super(name, description);
        this.account = account;
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
    public void foundItemUser(Account account) {

    }

    @Override
    public String getDetails() {
        return "Telegram account id: " + account.getId() + "\n" +
                "Telegram username: " + account.getUsername() + "\n" +
                "Losing item name: " + getName() + "\n" +
                "Losing item description" + getDescription() + "\n";
//                "Founded losing user phone number: " + account.getUser().getPhoneNumber();

    }
}

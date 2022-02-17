package kz.sdu.service;

import kz.sdu.entity.TelegramAccount;
import kz.sdu.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class TelegramAccountService {

    private TelegramAccount accountModel;
    private User user;

    public TelegramAccountService() {
    }

    public TelegramAccountService(TelegramAccount accountModel, User user) {
        this.accountModel = accountModel;
        this.user = user;
    }

    public String getInformationAccount() {
        return "Name: " + getUser().getName() + "\n" +
                "Surname: " + getUser().getSurname() + "\n" +
                "student ID: " + getUser().getStudentID() + "\n" +
                "Liked Event: " + getUser().getFavoriteEvents().size();

    }
}
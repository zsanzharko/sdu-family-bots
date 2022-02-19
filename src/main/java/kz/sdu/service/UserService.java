package kz.sdu.service;

import kz.sdu.entity.User;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.util.List;

@Service
@Getter
@Setter
public class UserService {

    private User user;

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

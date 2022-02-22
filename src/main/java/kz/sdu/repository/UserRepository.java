package kz.sdu.repository;

import kz.sdu.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByTelegramAccount_TelegramId(Long telegramId);

    void deleteUserByTelegramAccount_TelegramId(Long telegramId);

    List<User> findAllByEmail(String email);
}

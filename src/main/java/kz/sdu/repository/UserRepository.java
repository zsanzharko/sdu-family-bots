package kz.sdu.repository;

import kz.sdu.entity.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Lock(LockModeType.READ)
    List<User> findAllByStudentID(String studentId);

    @Lock(LockModeType.READ)
    User findUserByStudentID(String studentId);

    @Lock(LockModeType.READ)
    List<Long> findAllByTelegramId(Long telegramId);

    @Lock(LockModeType.READ)
    User findUserByTelegramId(Long telegramId);
}

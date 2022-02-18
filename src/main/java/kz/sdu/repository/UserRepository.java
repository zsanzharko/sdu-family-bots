package kz.sdu.repository;

import kz.sdu.entity.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Lock(LockModeType.READ)
    List<User> findAllByStudentID(String studentId);

    @Lock(LockModeType.READ)
    User findUserByStudentID(String studentId);

    @Lock(LockModeType.READ)
    User findUserByTelegramAccount_Id(Long telegramId);
}

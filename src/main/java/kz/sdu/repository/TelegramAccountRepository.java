package kz.sdu.repository;

import kz.sdu.entity.TelegramAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramAccountRepository extends CrudRepository<TelegramAccount, Long> {
    TelegramAccount findTelegramAccountModelById(Long id);

}

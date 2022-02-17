package kz.sdu.repository;

import kz.sdu.entity.TelegramAccount;
import org.springframework.data.repository.CrudRepository;

public interface TelegramAccountRepository extends CrudRepository<TelegramAccount, Long> {
    TelegramAccount findTelegramAccountModelById(Long id);

}

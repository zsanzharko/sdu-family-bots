package kz.sdu.repository;

import kz.sdu.entity.TelegramAccount;

public interface LostedItem {

    void foundItemUser(TelegramAccount telegramAccount);

    String getDetails();
}

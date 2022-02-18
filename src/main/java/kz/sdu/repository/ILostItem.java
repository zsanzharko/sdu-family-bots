package kz.sdu.repository;

import kz.sdu.entity.TelegramAccount;

public interface ILostItem {

    void foundItemUser(TelegramAccount telegramAccount);

    String getDetails();
}

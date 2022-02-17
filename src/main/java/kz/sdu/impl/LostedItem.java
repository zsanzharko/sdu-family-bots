package kz.sdu.impl;

import kz.sdu.entity.person.Account;

public interface LostedItem {

    void foundItemUser(Account account);

    String getDetails();
}

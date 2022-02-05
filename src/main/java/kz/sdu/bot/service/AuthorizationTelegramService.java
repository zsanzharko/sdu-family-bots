package kz.sdu.bot.service;

import kz.sdu.entity.person.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorizationTelegramService {

    public static Account authLogPerson(Long ID, String chatId, String username, String name, String surname) {
        int index = binary_search(Account.getAccountList(), ID);
        if (index != -1)
            return Account.getAccountList().get(index);
        addAccount(new Account(ID, chatId, username, name, surname));
        return Account.getAccountList().get(Account.getAccountList().size() - 1);
    }

    public static Account authLogPerson(Long ID, String chatId) {
        int index = binary_search(Account.getAccountList(), ID);
        if (index != -1)
            return Account.getAccountList().get(index);
        addAccount(new Account(ID, chatId));
        return Account.getAccountList().get(Account.getAccountList().size() - 1);
    }

    /**
     * I use selection sort in this method.
     * Big O(n^2)
     * @param account new account when we have to add in account List
     */
    private static void addAccount(Account account) {
        Account.getAccountList().add(account);

        List<Account> new_account = new ArrayList<>();

        for (int i = 0; i < Account.getAccountList().size(); i++) {
            int smallest_index = 0;
            Account smallest_account = Account.getAccountList().get(smallest_index);
            for (int j = 1; j < Account.getAccountList().size(); j++) {
                if(Account.getAccountList().get(j).getId() < smallest_account.getId()) {
                    smallest_account = Account.getAccountList().get(j);
                    smallest_index = j;
                }
            }
            new_account.add(Account.getAccountList().get(smallest_index));
        }
        Account.setAccountList(new_account);
    }

    private static int binary_search(List<Account> accounts, Long ID) {
        int low = 0;
        int high = accounts.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Long guess = accounts.get(mid).getId();
            if (Objects.equals(guess, ID)) {
                return mid;
            } if (guess > ID) {
                high = mid - 1;
            } else low = mid + 1;
        }
        return -1;
    }
}

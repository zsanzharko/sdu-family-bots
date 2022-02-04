package kz.sdu.entity;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;

@Data
public class Ticket {
    private final Long ID;
    private Document documentTicket;
    private File fileTicket;
    private boolean paid = false;
    private double cost; // KZT. 450 tg min cost
    private int countPeople;

    public Ticket(long ID, int count) {
        this.ID = ID;
        this.countPeople = count;
    }

    public void paying(boolean paid, double cost) {
        setPaid(paid);
        setCost(cost);
    }

    private void setCost(double cost) {
        if (paid && cost > 450.0 && cost < 4_369_647.00)
            this.cost = cost;
        else this.cost = 0;
    }
}

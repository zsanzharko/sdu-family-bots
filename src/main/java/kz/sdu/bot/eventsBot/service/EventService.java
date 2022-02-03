package kz.sdu.bot.eventsBot.service;

import kz.sdu.bot.entity.Event;
import kz.sdu.bot.entity.person.Account;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class EventService {

    private final List<Event> events = new ArrayList<>();
    private final List<Event> favoriteEvent = new ArrayList<>();

    public void removeEvent(Long ID) {
        int index;
        if ((index = binary_search(events, ID)) != 1)
            events.remove(index);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addLikeEvent(Event event) {
        favoriteEvent.add(event);
    }

    public void addFavoriteEvent(Long ID) {
        Event event = Event.getRecentEvents().get(binary_search(Event.getRecentEvents(), ID));
        favoriteEvent.add(event);
    }

    public void removeFavoriteEvent(Long ID) {
        int index = binary_search(favoriteEvent, ID);
        if (index != -1)
            favoriteEvent.remove(index);
    }

    public static int removeFavoriteEventWithNextIndex(int index, Account account) {
        account.getUser().getEventService().getFavoriteEvent().remove(index);

        if (index == account.getUser().getEventService().getFavoriteEvent().size() - 1) {
            return 0;
        } else return index + 1;
    }

    private static int binary_search(List<Event> events, Long ID) {
        int low = 0;
        int high = events.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Long guess = events.get(mid).getID();
            if (Objects.equals(guess, ID)) {
                return mid;
            } if (guess > ID) {
                high = mid - 1;
            } else low = mid + 1;
        }
        return -1;
    }
}

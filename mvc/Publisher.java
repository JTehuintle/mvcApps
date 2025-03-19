package mvc;

import java.util.*;

public class Publisher {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber s) {
        if (!subscribers.contains(s)) subscribers.add(s);
    }

    public void unsubscribe(Subscriber s) {
        subscribers.remove(s);
    }

    public void changed() {
        for (Subscriber s : subscribers) s.update();
    }
}

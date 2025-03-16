package me.frandma.sausage.event;

import java.util.*;
import java.util.function.Consumer;

public final class EventManager {
  private static final Map<Class<? extends Event>, Set<Consumer<? extends Event>>> consumerMap = new HashMap<>();
  public static <T extends Event> void register(Class<T> eventType, Consumer<T> listener) {
    consumerMap.computeIfAbsent(eventType, k -> new HashSet<>()).add(listener);
  }
  public static boolean trigger(Event event) {
    Set<Consumer<? extends Event>> listeners = consumerMap.get(event.getClass());
    if (listeners == null) return false;
    for (Consumer<? extends Event> listener : listeners) {
      @SuppressWarnings("unchecked")
      Consumer<Event> eventListener = (Consumer<Event>) listener;
      eventListener.accept(event);
    }
    if (event instanceof Cancellable) return ((Cancellable)event).isCancelled();
    return false;
  }
}

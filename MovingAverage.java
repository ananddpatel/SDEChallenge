public interface MovingAverage<T> {
    Iterable<T> getItems();
    void add(T item);
    boolean isEmpty();
}



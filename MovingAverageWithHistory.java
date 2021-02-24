public interface MovingAverageWithHistory<T> extends MovingAverage<T> {
    T getMovingAverage(int window);
    T getMovingAverage(int window, int offsetIndex);
}

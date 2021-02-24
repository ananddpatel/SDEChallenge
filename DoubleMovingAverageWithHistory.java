import java.util.Iterator;
import java.util.LinkedList;

public class DoubleMovingAverageWithHistory implements MovingAverageWithHistory<Double> {

    private final LinkedList<Double> list = new LinkedList<>();

    @Override
    public Iterable<Double> getItems() {
        return list;
    }

    @Override
    public void add(Double item) {
        list.addFirst(item);
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

    @Override
    public Double getMovingAverage(int window) {
        if (isEmpty()) {
            return 0.0;
        }

        Iterator<Double> iter = list.iterator();
        Double total = 0.0;

        int adjustedWindow = Math.min(list.size(), window);
        for (int i = 0; i < adjustedWindow; i++) {
            total += iter.next();
        }

        return total/adjustedWindow;
    }

    @Override
    public Double getMovingAverage(int window, int offsetIndex) {
        if (isEmpty()) {
            return 0.0;
        }

        Iterator<Double> iter = list.iterator();
        for (int i = 0; i < offsetIndex; i++) {
            iter.next();
        }

        Double total = 0.0;
        // size - offsetIndex will give you remaining items in the list to consider
        int remainingItemSize = list.size() - offsetIndex;
        int adjustedWindow = Math.min(remainingItemSize, window);
        for (int i = 0; i < adjustedWindow; i++) {
            total += iter.next();
        }

        return total/adjustedWindow;
    }

    public static void main(String[] args) {

        MovingAverageWithHistory<Double> ma = new DoubleMovingAverageWithHistory();

        System.out.println("Basic");

        System.out.println(ma.getMovingAverage(3)); // 0
        ma.add(5.0);
        ma.add(10.0);
        System.out.println(ma.getMovingAverage(3)); // (5.0 + 10.0) / 2 = 7.5
        ma.add(15.0);
        System.out.println(ma.getMovingAverage(3)); // (5.0 + 10. + 15.0) / 3 = 10.0
        ma.add(20.0);
        ma.add(25.0);
        System.out.println(ma.getMovingAverage(5)); // (5.0 + 10. + 15.0 + 20.0 + 25.0) / 5 = 15.0
        System.out.println(ma.getMovingAverage(3)); // (15.0 + 20.0 + 25.0) / 5 = 20.0

        System.out.println("With Offset");
        ma.add(30.0);
        ma.add(35.0);
        ma.add(40.0);
        ma.add(45.0);
        ma.add(50.0);

        System.out.println(ma.getMovingAverage(3, 2)); //  (40 + 35 + 30) / 3 = 35
        System.out.println(ma.getMovingAverage(3, 3)); //  (35 + 30 + 25) / 3 = 30
        System.out.println(ma.getMovingAverage(7, 5)); // (25 + 20 + 15 + 10 + 5) / 5 = 15

        System.out.println(ma.getItems());

    }
}

import java.util.LinkedList;

public class StaticDoubleMovingAverage implements StaticMovingAverage<Double> {
    private final LinkedList<Double> list = new LinkedList<>();
    private final int window;
    private double total = 0.0;

    public StaticDoubleMovingAverage(int window) {
        this.window = window;
    }

    @Override
    public Iterable<Double> getItems() {
        return list;
    }

    @Override
    public void add(Double item) {
        if (list.size() == window) {
            total -= list.removeFirst();
        }
        list.add(item);
        total += item;
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    };

    @Override
    public Double getMovingAverage() {
        if (isEmpty()) {
            return total;
        }
        int adjustedWindow = Math.min(list.size(), window);
        return total/adjustedWindow;
    }

    public static void main(String[] args) {
        StaticMovingAverage<Double> ma = new StaticDoubleMovingAverage(3);
        System.out.println(ma.getMovingAverage()); // 0
        ma.add(5.0);
        ma.add(10.0);
        System.out.println(ma.getMovingAverage()); // (5.0 + 10.0) / 2 = 7.5
        ma.add(15.0);
        System.out.println(ma.getMovingAverage()); // (5.0 + 10.0 + 15.0) / 3 = 10.0
        ma.add(20.0);
        System.out.println(ma.getMovingAverage()); // (10.0 + 15.0 + 20.0) / 3 = 15.0
        ma.add(25.0);
        System.out.println(ma.getMovingAverage()); // (15.0 + 20.0 + 25.0) / 3 = 20.0

        System.out.println(ma.getItems());
    }
}

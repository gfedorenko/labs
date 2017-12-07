import java.util.Comparator;

public class Sorting implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Gun b1 = (Gun) o1;
        Gun b2 = (Gun) o2;
        return  b1.getOrigin().compareTo(b2.getOrigin());
    }
}
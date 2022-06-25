import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            String[] testString = new String[]{"1-4", "2"};
            Port port = new Port(testString);
            NumberShaper numberShaper = new NumberShaper(port.getIndexes());
            numberShaper.make();
            var result = numberShaper.get();
            for (var r :  result)
                System.out.println(Arrays.toString(r));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

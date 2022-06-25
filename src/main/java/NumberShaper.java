import java.util.*;

public class NumberShaper {
    private final String[] lines;
    private Integer[][] numberLines;
    private ArrayList<Tuple<Integer, Integer>> intervals;
    private int numberLinesCount;

    public NumberShaper(String[] lines) {
        this.lines = new String[lines.length];
        for(int i = 0; i < lines.length; i++)
            this.lines[i] = lines[i].replaceAll("\\s+", "");
    }

    public void make() throws Exception {
        intervals = toNumeric(lines);
        if (intervals.size() == 0)
            throw new Exception("No items to make.");
        int total = countNumberLines(intervals);
        allocateNumberLines(total);
        makeArrays(0, intervals.get(0).first);
        Arrays.sort(numberLines, Arrays::compare);
    }

    public SortedSet<Integer[]> get() {
        SortedSet<Integer[]> set = new TreeSet((o1, o2) -> Arrays.compare((Integer[])o1, (Integer[])o2));
        Collections.addAll(set, numberLines);
        return set;
    }

    private void allocateNumberLines(int total) {
        numberLines = new Integer[total][];
        for (int i = 0; i < numberLines.length; i++) {
            numberLines[i] = new Integer[intervals.size()];
        }
    }

    private static int countNumberLines(ArrayList<Tuple<Integer, Integer>> intervals) {
        if (intervals.size() == 0)
            return 0;
        int total = 1;
        for (var interval : intervals)
            total *= interval.second - interval.first + 1;
        return total;
    }

    private void makeArrays(int i, int value) {
        while (value <= intervals.get(i).second) {
            numberLines[numberLinesCount][i] = value;
            if (i + 1 < intervals.size())
                makeArrays(i + 1, intervals.get(i + 1).first);
            else {
                numberLinesCount++;
                if (numberLinesCount < numberLines.length)
                    System.arraycopy(numberLines[numberLinesCount - 1], 0, numberLines[numberLinesCount], 0, i);
            }
            value++;
        }
    }

    static private Tuple<Integer, Integer> makeTuple(String min, String max) throws Exception {
        try {
            Integer minValue = Integer.parseInt(min);
            Integer maxValue = Integer.parseInt(max);
            if (maxValue < minValue)
                throw new Exception("Min value more than max value.");
            return new Tuple<>(minValue, maxValue);
        } catch (NumberFormatException numberFormatException) {
            throw new Exception("Illegal numeric format. " + numberFormatException.getMessage() + ".");
        }
    }

    static public ArrayList<Tuple<Integer, Integer>> toNumeric(String[] lines) throws Exception {
        ArrayList<Tuple<Integer, Integer>> limits = new ArrayList<>();
        for (String line : lines) {
            try {
                String[] items = line.split(",");
                for (String item : items) {
                    String[] interval = item.split("-");
                    switch (interval.length) {
                        case 1:
                            limits.add(makeTuple(interval[0], interval[0]));
                            break;
                        case 2:
                            limits.add(makeTuple(interval[0], interval[1]));
                            break;
                        default:
                            throw new Exception("Illegal syntax.");
                    }
                }
            } catch (Exception exception) {
                throw new Exception(exception.getMessage() + " Line: " + line);
            }
        }
        return limits;
    }
}
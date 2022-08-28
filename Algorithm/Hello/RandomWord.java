public class RandomWord {
    public static void main(String[] args) {
        float i = 1;
        String champion = "None";
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            boolean b = StdRandom.bernoulli(1 / i);
            StdOut.println(b);
            if (b) {
                champion = s;
            }
            i = i + 1;
        }
        StdOut.println(champion);
    }
}

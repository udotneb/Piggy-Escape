package byog.lab6;

public class Test {

    public static void main(String[] args) {
        String x = "";
        for (int i = 0; i < 26; i++) {
            x = x + Character.toString((char) (i + 'a'));
        }
        System.out.println(x);
    }
}

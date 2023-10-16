import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        Collections.addAll(a, 0, 1, 2, 3, 4, 5, 6);
        ArrayList<Integer> b = new ArrayList<>();
        Collections.addAll(b, 10, 20, 30, 40);

//        ArrayList<Integer> tmp = new ArrayList<>();
//        tmp = a;
////        tmp.add(5);
////        System.out.println(a);
//        a = b;
//        b = tmp;
//
//
//        System.out.println(a.toString());
//        System.out.println(b.toString());
        a.add(5, (Integer) 100);
        System.out.println(a);


    }
}

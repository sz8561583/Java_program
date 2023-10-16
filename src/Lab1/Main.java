package Lab1;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> stu = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            stu.add(i);
        }
        int m = sc.nextInt();
        while (m-- > 0) {
            //学号为p的学生移动q位，q > 0向后移动， q < 0向前移动
            int p = sc.nextInt();
            int q = sc.nextInt();
            int index = stu.indexOf((Integer) p) + q;
            stu.remove((Integer) p);
            stu.add(index, (Integer) p);
//            System.out.println(stu.toString());
        }

        for (int i = 0; i < stu.size(); i++) {
            System.out.print(stu.get(i));
            if(i != stu.size() - 1)
                System.out.print(" ");
            else
                System.out.println();
        }
    }
}

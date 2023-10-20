package Lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        //1. 判定条件写错了 60
        //2. 不移除英雄，只移除随从 60 -> 80
        //3. 一方英雄死亡则停止后续操作
        //4. 输出写错了，没有考虑随从数 80 -> 100

        //日志初始化
        Logger log = Logger.getLogger("实验二日志");
//        log.setLevel(Level.INFO);
        FileHandler fileHandler = new FileHandler("Lab2.log");
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new SimpleFormatter());
        log.addHandler(fileHandler);

        Player p1 = new Player();
        Player p2 = new Player();
        p1.turn = true;
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();//读取多余的换行符
        Player ATK = new Player();
        Player DEF = new Player();
        int cnt = 1;
        while (n-- > 0) {
            log.log(Level.INFO, "--------第" + (cnt) + "回合开始--------");
            log.info("先手玩家 英雄状态： 血量->" + p1.heroes.get(0).HP + ", 攻击力->" + p1.heroes.get(0).ATK);
            log.info("先手玩家 随从状态： 随从数量->" + (p1.heroes.size() - 1));
            for (int i = 1; i < p1.heroes.size(); i++) {
                log.info("先手玩家 随从状态： 血量->" + p1.heroes.get(i).HP + ", 攻击力->" + p1.heroes.get(i).ATK);
            }
            log.info("后手玩家 英雄状态： 血量->" + p2.heroes.get(0).HP + ", 攻击力->" + p2.heroes.get(0).ATK);
            log.info("后手玩家 随从状态： 随从数量->" + (p2.heroes.size() - 1));
            for (int i = 1; i < p2.heroes.size(); i++) {
                log.info("后手玩家 随从" + i + "状态： 血量->" + p2.heroes.get(i).HP + ", 攻击力->" + p2.heroes.get(i).ATK);
            }

            if (p1.turn && !p2.turn) {
                ATK = p1;
                DEF = p2;
            } else if (!p1.turn && p2.turn) {
                ATK = p2;
                DEF = p1;
            }
            String turn = sc.nextLine(); //一个回合的操作
            String[] cmd = turn.split(" ");

            if (cmd[0].equals("summon")) {
                summon(ATK, Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
            } else if (cmd[0].equals("attack")) {
                attack(ATK, DEF, Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
                //判断是否已分出胜负
                if (whoIsWinner(p1, p2) != 0)
                    break;
            } else if (cmd[0].equals("end")) {
                ATK.turn = false;
                DEF.turn = true;
            }

            log.log(Level.INFO, "--------第" + (cnt++) + "回合结束--------");
            log.info("先手玩家 英雄状态： 血量->" + p1.heroes.get(0).HP + ", 攻击力->" + p1.heroes.get(0).ATK);
            log.info("先手玩家 随从状态： 随从数量->" + (p1.heroes.size() - 1));
            for (int i = 1; i < p1.heroes.size(); i++) {
                log.info("先手玩家 随从状态： 血量->" + p1.heroes.get(i).HP + ", 攻击力->" + p1.heroes.get(i).ATK);
            }
            log.info("后手玩家 英雄状态： 血量->" + p2.heroes.get(0).HP + ", 攻击力->" + p2.heroes.get(0).ATK);
            log.info("后手玩家 随从状态： 随从数量->" + (p2.heroes.size() - 1));
            for (int i = 1; i < p2.heroes.size(); i++) {
                log.info("后手玩家 随从" + i + "状态： 血量->" + p2.heroes.get(i).HP + ", 攻击力->" + p2.heroes.get(i).ATK);
            }

        }

        System.out.println(whoIsWinner(p1, p2));
        displayInfo(p1);
        displayInfo(p2);
    }


    public static void summon(Player player, int POS, int ATK, int HP) {
        Hero Minion = new Hero(ATK, HP);
        player.heroes.add(POS, Minion);
    }

    public static void attack(Player ATK, Player DEF, int ATK_POS, int DEF_POS) {
        ATK.heroes.get(ATK_POS).HP -= DEF.heroes.get(DEF_POS).ATK;
        DEF.heroes.get(DEF_POS).HP -= ATK.heroes.get(ATK_POS).ATK;
        //死亡判定，不移除英雄，只移除随从
        if (ATK.heroes.get(ATK_POS).HP <= 0 && ATK_POS != 0)
            ATK.heroes.remove(ATK_POS);
        if (DEF.heroes.get(DEF_POS).HP <= 0 && DEF_POS != 0)
            DEF.heroes.remove(DEF_POS);
    }

    private static int whoIsWinner(Player p1, Player p2) {
        if (p1.heroes.get(0).HP > 0 && p2.heroes.get(0).HP <= 0)  //p2英雄死亡，则p1获胜
            return 1;
        else if (p2.heroes.get(0).HP > 0 && p1.heroes.get(0).HP <= 0) //p1英雄死亡，则p2获胜
            return -1;
        else
            return 0;
    }

    private static void displayInfo(Player player) {
        System.out.println(player.heroes.get(0).HP); //英雄的血量
        System.out.print(player.heroes.size() - 1); //存活随从的个数
        if ((player.heroes.size() - 1) != 0)
            System.out.print(" ");
        else
            System.out.println();
        for (int i = 1; i < player.heroes.size(); i++) {
            System.out.print(player.heroes.get(i).HP); //随从的血量
            if (i != player.heroes.size() - 1)
                System.out.print(" ");
            else
                System.out.println();
        }
    }

    static class Hero {
        int HP;     //生命值
        int ATK;    //攻击力

        Hero(int ATK, int HP) {
            this.ATK = ATK;
            this.HP = HP;
        }
    }

    static class Player {
        boolean turn = false;
        ArrayList<Hero> heroes = new ArrayList<>();

        Player() {
            //初始英雄的血量为30，攻击力为0
            heroes.add(new Hero(0, 30));
        }
    }
}
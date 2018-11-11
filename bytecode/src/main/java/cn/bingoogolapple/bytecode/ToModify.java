package cn.bingoogolapple.bytecode;

public class ToModify {
    public void concat(int p1, String p2) {
        System.out.println("========start=========");
        System.out.println(p1);
        System.out.println(p2);
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < p1; i++) {
            resultSb.append(p2).append(i);
        }
        System.out.println(resultSb.toString());
        System.out.println("========end=========");
    }
}

package cn.bingoogolapple.bytecode;

import java.util.List;
import java.util.Map;

//「version=52」「access=1537」「name=cn/bingoogolapple/bytecode/SyntaxSuperInterface」「signature=null」「superName=java/lang/Object」「interfaces=[]」
public interface SyntaxSuperInterface {
    //「access=1025」「name=test1」「desc=()V」「signature=null」「exceptions=null」
    void test1();

    //「access=1025」「name=test2」「desc=(ZBCSIJFD)I」「signature=null」「exceptions=[java/lang/IllegalArgumentException]」
    int test2(boolean p1, byte p2, char p3, short p4, int p5, long p6, float p7, double p8) throws IllegalArgumentException;

    //「access=1025」「name=test3」「desc=([Z[B[C[S[I[J[F[D)[I」「signature=null」「exceptions=[java/lang/IllegalArgumentException, java/lang/IllegalStateException]」
    int[] test3(boolean[] p1, byte[] p2, char[] p3, short[] p4, int[] p5, long[] p6, float[] p7, double[] p8) throws IllegalArgumentException, IllegalStateException;

    //「access=1025」「name=test4」「desc=(Ljava/lang/Boolean;Ljava/lang/Byte;Ljava/lang/Character;Ljava/lang/Short;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Float;Ljava/lang/Double;)Ljava/lang/Integer;」「signature=null」「exceptions=[java/lang/IllegalArgumentException]」
    Integer test4(Boolean p1, Byte p2, Character p3, Short p4, Integer p5, Long p6, Float p7, Double p8) throws IllegalArgumentException;

    //「access=1025」「name=test5」「desc=([Ljava/lang/Boolean;[Ljava/lang/Byte;[Ljava/lang/Character;[Ljava/lang/Short;[Ljava/lang/Integer;[Ljava/lang/Long;[Ljava/lang/Float;[Ljava/lang/Double;)[Ljava/lang/Integer;」「signature=null」「exceptions=[java/lang/IllegalArgumentException, java/lang/IllegalStateException]」
    Integer[] test5(Boolean[] p1, Byte[] p2, Character[] p3, Short[] p4, Integer[] p5, Long[] p6, Float[] p7, Double[] p8) throws IllegalArgumentException, IllegalStateException;

    //「access=1025」「name=test61」「desc=(Ljava/util/List;)Ljava/util/List;」「signature=(Ljava/util/List<Ljava/lang/String;>;)Ljava/util/List<Ljava/lang/String;>;」「exceptions=null」
    List<String> test61(List<String> p1);

    //「access=1025」「name=test62」「desc=(Ljava/util/List;)Ljava/util/List;」「signature=null」「exceptions=null」
    List test62(List p1);

    //「access=1025」「name=test63」「desc=(Ljava/util/List;Ljava/lang/String;)Ljava/util/List;」「signature=(Ljava/util/List<Ljava/lang/String;>;Ljava/lang/String;)Ljava/util/List<Ljava/lang/String;>;」「exceptions=null」
    List<String> test63(List<String> p1, String p2);

    //「access=1025」「name=test64」「desc=(Ljava/util/List;)Ljava/util/List;」「signature=(Ljava/util/List<Ljava/util/List<Ljava/lang/String;>;>;)Ljava/util/List<Ljava/util/List<Ljava/lang/String;>;>;」「exceptions=null」
    List<List<String>> test64(List<List<String>> p1);

    //「access=1025」「name=test71」「desc=(Ljava/util/Map;)Ljava/util/Map;」「signature=(Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;)Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;」「exceptions=null」
    Map<String, String> test71(Map<String, String> p1);

    //「access=1025」「name=test72」「desc=(Ljava/util/Map;)Ljava/util/Map;」「signature=(Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;)Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;」「exceptions=null」
    Map<String, List<String>> test72(Map<String, List<String>> p1);
}
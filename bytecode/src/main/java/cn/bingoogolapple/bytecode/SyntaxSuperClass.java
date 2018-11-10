package cn.bingoogolapple.bytecode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//「version=52」「access=33」「name=cn/bingoogolapple/bytecode/SyntaxSuperClass」「signature=Ljava/util/ArrayList<Ljava/lang/String;>;Ljava/lang/Comparable<Ljava/lang/Integer;>;」「superName=java/util/ArrayList」「interfaces=[java/lang/Comparable]」
//public class SyntaxSuperClass extends ArrayList<String> ilmplements Comparable<Integer> {

//「version=52」「access=1057」「name=cn/bingoogolapple/bytecode/SyntaxSuperClass」「signature=Ljava/util/ArrayList<Ljava/lang/String;>;Ljava/lang/Comparable<Ljava/lang/Integer;>;」「superName=java/util/ArrayList」「interfaces=[java/lang/Comparable]」
//public abstract class SyntaxSuperClass extends ArrayList<String> implements Comparable<Integer> {

//「version=52」「access=1057」「name=cn/bingoogolapple/bytecode/SyntaxSuperClass」「signature=<T:Ljava/lang/Object;>Ljava/util/ArrayList<Ljava/lang/String;>;Ljava/lang/Comparable<Ljava/lang/Integer;>;」「superName=java/util/ArrayList」「interfaces=[java/lang/Comparable]」
public abstract class SyntaxSuperClass<T> extends ArrayList<String> implements Comparable<Integer> {
    //「access=26」「name=A」「desc=I」「signature=null」「value=1」
    private static final int A = 1;
    //「access=26」「name=B」「desc=Ljava/lang/String;」「signature=null」「value=2」
    private static final String B = "2";
    //「access=18」「name=c」「desc=Ljava/lang/String;」「signature=null」「value=3」
    private final String c = "3";
    //「access=20」「name=d」「desc=Ljava/lang/String;」「signature=null」「value=4」
    protected final String d = "4";
    //「access=17」「name=e」「desc=Ljava/lang/String;」「signature=null」「value=5」
    public final String e = "5";
    //「access=1」「name=f」「desc=Ljava/util/List;」「signature=Ljava/util/List<Ljava/lang/String;>;」「value=null」
    public List<String> f = new ArrayList<>();
    //「access=1」「name=g」「desc=Ljava/util/Map;」「signature=Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;」「value=null」
    public Map<String, String> g = new HashMap<>();
    //「access=1」「name=h」「desc=Ljava/lang/Object;」「signature=TT;」「value=null」
    public T h;
    //「access=1」「name=i」「desc=Ljava/util/List;」「signature=Ljava/util/List<TT;>;」「value=null」
    public List<T> i;
    //「access=0」「name=j」「desc=Ljava/lang/String;」「signature=null」「value=null」
    String j;

    //「access=1」「name=<init>」「desc=()V」「signature=null」「exceptions=null」
    public SyntaxSuperClass() {
    }

    //「access=1」「name=<init>」「desc=(Ljava/lang/String;)V」「signature=null」「exceptions=null」
    public SyntaxSuperClass(String p1) {
    }

    /**
     * @see org.objectweb.asm.Opcodes.ACC_PUBLIC
     */
    //「access=1」「name=test1」「desc=()V」「signature=null」「exceptions=null」
    public void test1() {
    }

    /**
     * @see org.objectweb.asm.Opcodes.ACC_PUBLIC
     * +
     * @see org.objectweb.asm.Opcodes.ACC_ABSTRACT
     */
    //「access=1025」「name=test11」「desc=()V」「signature=null」「exceptions=null」
    public abstract void test11();

    //「access=4」「name=test2」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    protected String test2(String p1) {
        return p1;
    }

    //「access=1028」「name=test21」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    protected abstract String test21(String p1);

    //「access=0」「name=test4」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    String test4(String p1) {
        return p1;
    }

    //「access=1024」「name=test41」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    abstract String test41(String p1);

    //「access=2」「name=test3」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    private String test3(String p1) {
        return p1;
    }

    //「access=1」「name=compareTo」「desc=(Ljava/lang/Integer;)I」「signature=null」「exceptions=null」
    //「access=4161」「name=compareTo」「desc=(Ljava/lang/Object;)I」「signature=null」「exceptions=null」
    @Override
    public int compareTo(@NotNull Integer o) {
        return 0;
    }
}

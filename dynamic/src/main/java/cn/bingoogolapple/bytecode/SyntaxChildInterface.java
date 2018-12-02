package cn.bingoogolapple.bytecode;

import java.util.List;
import java.util.Map;

//「version=52」「access=1537」「name=cn/bingoogolapple/bytecode/SyntaxChildInterface」「signature=<T:Ljava/lang/Object;>Ljava/lang/Object;」「superName=java/lang/Object」「interfaces=[]」
//public interface SyntaxChildInterface<T> {

//「version=52」「access=1537」「name=cn/bingoogolapple/bytecode/SyntaxChildInterface」「signature=<T:Ljava/lang/String;>Ljava/lang/Object;Lcn/bingoogolapple/bytecode/SyntaxSuperInterface;」「superName=java/lang/Object」「interfaces=[cn/bingoogolapple/bytecode/SyntaxSuperInterface]」
//public interface SyntaxChildInterface<T extends String> extends SyntaxSuperInterface {

//「version=52」「access=1537」「name=cn/bingoogolapple/bytecode/SyntaxChildInterface」「signature=<T:Ljava/lang/Object;>Ljava/lang/Object;Lcn/bingoogolapple/bytecode/SyntaxSuperInterface;」「superName=java/lang/Object」「interfaces=[cn/bingoogolapple/bytecode/SyntaxSuperInterface]」
public interface SyntaxChildInterface<T> extends SyntaxSuperInterface {

    //「access=1025」「name=test81」「desc=(Ljava/util/List;)Ljava/util/List;」「signature=(Ljava/util/List<Ljava/lang/String;>;)Ljava/util/List<Ljava/lang/String;>;」「exceptions=null」
    List<String> test81(List<String> p1);

    //「access=1025」「name=test82」「desc=(Ljava/util/List;)Ljava/util/List;」「signature=(Ljava/util/List<TT;>;)Ljava/util/List<TT;>;」「exceptions=null」
    List<T> test82(List<T> p1);

    //「access=1025」「name=test9」「desc=(ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;」「signature=(ILjava/lang/Integer;Ljava/lang/String;TT;)TT;」「exceptions=[java/lang/IllegalArgumentException]」
    T test9(int p1, Integer p2, String p3, T p4) throws IllegalArgumentException;

    //「access=1025」「name=test10」「desc=(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/List;)Ljava/util/List;」「signature=(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/List<TT;>;)Ljava/util/List<TT;>;」「exceptions=[java/lang/IllegalArgumentException]」
    List<T> test10(int p1, Integer p2, String p3, List<T> p4) throws IllegalArgumentException;

    //「access=1025」「name=test11」「desc=(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/Map;)Ljava/util/Map;」「signature=(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/Map<Ljava/lang/String;TT;>;)Ljava/util/Map<Ljava/lang/String;TT;>;」「exceptions=[java/lang/IllegalArgumentException]」
    Map<String, T> test11(int p1, Integer p2, String p3, Map<String, T> p4) throws IllegalArgumentException;

    //「access=1025」「name=test12」「desc=(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/Map;)Ljava/util/Map;」「signature=(ILjava/lang/Integer;Ljava/lang/String;Ljava/util/Map<Ljava/lang/String;Ljava/util/List<TT;>;>;)Ljava/util/Map<Ljava/lang/String;Ljava/util/List<TT;>;>;」「exceptions=[java/lang/IllegalArgumentException]」
    Map<String, List<T>> test12(int p1, Integer p2, String p3, Map<String, List<T>> p4) throws IllegalArgumentException;
}

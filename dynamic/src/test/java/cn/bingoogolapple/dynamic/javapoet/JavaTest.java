package cn.bingoogolapple.dynamic.javapoet;

import com.squareup.javapoet.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * $T 是类型替换，一般用于 ("$T foo", List.class) => List foo。$T 的好处在于 JavaPoet 会自动帮你补全文件开头的 import，如果直接写 ("List foo") 虽然也能生成 List foo， 但是最终的 java 文件就不会自动帮你添加 import java.util.List
 * $L 是字面量替换，比如 ("abc$L123", "FOO") => abcFOO123. 也就是直接替换
 * $S 是字符串替换，比如: ("$S.length()", "foo") => "foo".length()，注意 $S 是将参数替换为了一个带双引号的字符串，免去了手写 "\"foo\".length()" 中转义 (\") 的麻烦
 * $N 是名称替换，比如你之前定义了一个函数 MethodSpec methodSpec = MethodSpec.methodBuilder("foo").build()，现在你可以通过 $N 获取这个函数的名称 ("$N", methodSpec) => foo
 */
@RunWith(JUnit4.class)
public class JavaTest {

    @Test
    public void helloWorld() throws IOException {
        MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, Java!")
                .build();

        TypeSpec helloJava = TypeSpec.classBuilder("HelloJava")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mainMethod)
                .build();

        JavaFile javaFile = JavaFile.builder("cn.bingoogolapple.dynamic", helloJava)
                .build();

        javaFile.writeTo(System.out);
    }

    @Test
    public void codeAndControlFlow() throws IOException {
        MethodSpec codeMethod = MethodSpec.methodBuilder("codeMethod")
                .addCode("int total = 0;\n" +
                        "for (int i = 0; i < 10; i++) {\n" +
                        "  if (i % 4 == 0) {\n" +
                        "    total += 4;\n" +
                        "  } else if (i % 3 == 0) {\n" +
                        "    total += 3;\n" +
                        "  } else {\n" +
                        "    total += i;\n" +
                        "  }\n" +
                        "}\n" +
                        "while(total > 0) {\n" +
                        "  System.out.println(total)\n" +
                        "  total--\n" +
                        "}\n")
                .build();
        System.out.println(codeMethod.toString());

        MethodSpec controlFlowMethod = MethodSpec.methodBuilder("controlFlowMethod")
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .beginControlFlow("if (i % 4 == 0)")
                .addStatement("total += 4")
                .nextControlFlow("else if (i % 3 == 0)")
                .addStatement("total += 3")
                .nextControlFlow("else")
                .addStatement("total += i")
                .endControlFlow()
                .addComment("if else 结束")
                .endControlFlow()
                .addComment("for 循环结束")
                .beginControlFlow("while(total > 0)")
                .addStatement("$T.out.println(total)", System.class)
                .addStatement("total--")
                .endControlFlow()
                .build();
        System.out.println(controlFlowMethod.toString());
    }

    @Test
    public void testParameter() {
        System.out.println(computeRangeOne("multiply10to20", 10, 20, "*").toString());
        System.out.println(computeRangeTwo("multiply10to20", 10, 20, "*").toString());
        System.out.println(whatsMyName("BGA"));
    }

    private MethodSpec computeRangeOne(String name, int from, int to, String op) {
        return MethodSpec.methodBuilder(name)
                .returns(int.class)
                .addStatement("int result = 0")
                .beginControlFlow("for (int i = " + from + "; i < " + to + "; i++)")
                .addStatement("result = result " + op + " i")
                .endControlFlow()
                .addStatement("return result")
                .build();
    }

    private MethodSpec computeRangeTwo(String name, int from, int to, String op) {
        return MethodSpec.methodBuilder(name)
                .returns(int.class)
                .addStatement("int result = 0")
                .beginControlFlow("for (int i = $L; i < $L; i++)", from, to)
                .addStatement("result = result $L i", op) // Literals 直接写在输出代码中，没有转义。它的类型可以是字符串、primitives 和一些 JavaPoet 类型
                .endControlFlow()
                .addStatement("return result")
                .build();
    }

    private static MethodSpec whatsMyName(String name) {
        return MethodSpec.methodBuilder(name)
                .returns(String.class)
                .addStatement("return $S", name) // 会自动加上双引号
                .build();
    }

    @Test
    public void typeParameter() throws IOException {
        MethodSpec method1 = MethodSpec.methodBuilder("method1")
                .returns(Date.class)
                .addStatement("return new $T()", Date.class) // 自动完成 import 声明
                .build();

        // 即使应用了一个不存在的类，也为自动 import
        ClassName haHaClassName = ClassName.get("cn.bingoogolapple.dynamic.notexist", "HaHa");
        MethodSpec method2 = MethodSpec.methodBuilder("method2")
                .returns(haHaClassName)
                .addStatement("return new $T()", haHaClassName)
                .build();


        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listOfHaHa = ParameterizedTypeName.get(list, haHaClassName);

        MethodSpec method3 = MethodSpec.methodBuilder("method3")
                .returns(listOfHaHa)
                .addStatement("$T result = new $T<>()", listOfHaHa, arrayList)
                .addStatement("result.add(new $T())", haHaClassName)
                .addStatement("result.add(new $T())", haHaClassName)
                .addStatement("result.add(new $T())", haHaClassName)
                .addStatement("return result")
                .build();

        TypeSpec Hello = TypeSpec.classBuilder("Hello")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(method1)
                .addMethod(method2)
                .addMethod(method3)
                .build();

        JavaFile.builder("cn.bingoogolapple.dynamic", Hello)
                .build()
                .writeTo(System.out);
    }

    @Test
    public void staticImport() throws IOException {
        ClassName haHaClassName = ClassName.get("cn.bingoogolapple.dynamic.notexist", "HaHa");
        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listOfHaHa = ParameterizedTypeName.get(list, haHaClassName);
        ClassName heHeClassName = ClassName.get("cn.bingoogolapple.dynamic.notexist", "HaHa", "HeHe");

        MethodSpec method1 = MethodSpec.methodBuilder("method1")
                .returns(listOfHaHa)
                .addStatement("$T result = new $T<>()", listOfHaHa, arrayList)
                .addStatement("result.add($T.create(2000))", haHaClassName)
                .addStatement("result.add($T.create($T.XYZ))", haHaClassName, heHeClassName)
                .addStatement("$T.sort(result)", Collections.class)
                .addStatement("return result.isEmpty() ? $T.emptyList() : result", Collections.class)
                .build();

        TypeSpec hello = TypeSpec.classBuilder("Hello")
                .addMethod(method1)
                .build();

        JavaFile.builder("cn.bingoogolapple.dynamic", hello)
                .addStaticImport(haHaClassName, "create")
                .addStaticImport(heHeClassName, "*")
                .addStaticImport(Collections.class, "*")
                .build()
                .writeTo(System.out);
    }

    @Test
    public void methodParameter() throws IOException {
        MethodSpec hexDigit = MethodSpec.methodBuilder("hexDigit")
                .addParameter(int.class, "i")
                .returns(char.class)
                .addStatement("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
                .build();

        MethodSpec byteToHex = MethodSpec.methodBuilder("byteToHex")
                .addParameter(int.class, "b")
                .returns(String.class)
                .addStatement("char[] result = new char[2]")
                .addStatement("result[0] = $N((b >>> 4) & 0xf)", hexDigit) // 使用 $N 可以引用另外一个通过名字生成的声明
                .addStatement("result[1] = $N(b & 0xf)", hexDigit)
                .addStatement("return new String(result)")
                .build();

        JavaFile.builder("cn.bingoogolapple.dynamic", TypeSpec.classBuilder("Hello")
                .addMethod(hexDigit)
                .addMethod(byteToHex)
                .build()
        ).build().writeTo(System.out);
    }

    @Test
    public void abstractMethod() throws IOException {
        MethodSpec constructorMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "greeting")
                .addStatement("this.$N = $N", "greeting", "greeting")
                .build();

        ParameterSpec androidParameter = ParameterSpec.builder(String.class, "android")
                .addModifiers(Modifier.FINAL)
                .build();
        MethodSpec method1 = MethodSpec.methodBuilder("method1")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(String.class, "greeting")
                .addParameter(androidParameter)
                .addStatement("this.$N = $N", "greeting", "greeting")
                .addStatement("return greeting")
                .build();

        MethodSpec absMethod1 = MethodSpec.methodBuilder("absMethod1")
                .addParameter(int.class, "i")
                .returns(int.class)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .build();

        FieldSpec androidField = FieldSpec.builder(String.class, "android")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("$S + $L", "Lollipop v.", 5.0)
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder("Hello")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(androidField)
                .addField(String.class, "greeting", Modifier.PRIVATE)
                .addMethod(method1)
                .addMethod(absMethod1)
                .addMethod(constructorMethod) // 当生成代码时，构造函数会先于其他方法生成
                .build();
        JavaFile.builder("cn.bingoogolapple.dynamic", typeSpec).build().writeTo(System.out);
    }

    @Test
    public void interfaceMethod() throws IOException {
        TypeSpec typeSpec = TypeSpec.interfaceBuilder("Hello")
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(String.class, "FIELD_ONE")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", "FieldOneValue")
                        .build())
                .addMethod(MethodSpec.methodBuilder("method1")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        JavaFile.builder("cn.bingoogolapple.dynamic", typeSpec).build().writeTo(System.out);
    }

    @Test
    public void enumTest() throws IOException {
        TypeSpec helloTypeSpec = TypeSpec.enumBuilder("Hello")
                .addModifiers(Modifier.PUBLIC)
                .addEnumConstant("ONE")
                .addEnumConstant("TWO")
                .addEnumConstant("THREE")
                .build();
        JavaFile.builder("cn.bingoogolapple.dynamic", helloTypeSpec).build().writeTo(System.out);

        TypeSpec worldTypeSpec = TypeSpec.enumBuilder("World")
                .addModifiers(Modifier.PUBLIC)
                .addEnumConstant("FIRST", TypeSpec.anonymousClassBuilder("$S", "one")
                        .addMethod(MethodSpec.methodBuilder("toString")
                                .addAnnotation(Override.class)
                                .addModifiers(Modifier.PUBLIC)
                                .addStatement("return $S", "HaHa")
                                .build())
                        .build())
                .addEnumConstant("SECOND", TypeSpec.anonymousClassBuilder("$S", "two").build())
                .addEnumConstant("THREE", TypeSpec.anonymousClassBuilder("$S", "three").build())
                .addField(String.class, "greeting", Modifier.PRIVATE, Modifier.FINAL)
                .addMethod(MethodSpec.constructorBuilder()
                        .addParameter(String.class, "greeting")
                        .addStatement("this.$N = $N", "greeting", "greeting")
                        .build())
                .build();
        JavaFile.builder("cn.bingoogolapple.dynamic", worldTypeSpec).build().writeTo(System.out);
    }

    @Test
    public void anonymousInnerClass() throws IOException {
        TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(Comparator.class, String.class))
                .addMethod(MethodSpec.methodBuilder("compare")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, "a")
                        .addParameter(String.class, "b")
                        .returns(int.class)
                        .addStatement("return $N.length() - $N.length()", "a", "b")
                        .build())
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("Hello")
                .addMethod(MethodSpec.methodBuilder("sortByLength")
                        .addParameter(ParameterizedTypeName.get(List.class, String.class), "strings")
                        .addStatement("$T.sort($N, $L)", Collections.class, "strings", comparator)
                        .build())
                .build();
        JavaFile.builder("cn.bingoogolapple.dynamic", typeSpec).build().writeTo(System.out);
    }

    @Test
    public void annotationTest() throws IOException {
        TypeSpec headerType = TypeSpec.annotationBuilder("Header")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(MethodSpec.methodBuilder("accept").addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).defaultValue("$S", "application/json; charset=utf-8").returns(String.class).build())
                .addMethod(MethodSpec.methodBuilder("userAgent").addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).returns(String.class).build())
                .build();
        JavaFile.builder("cn.bingoogolapple.dynamic", headerType).build().writeTo(System.out);

        TypeSpec headerListType = TypeSpec.annotationBuilder("HeaderList")
                .addModifiers(Modifier.PUBLIC)
                .build();
        JavaFile.builder("cn.bingoogolapple.dynamic", headerListType).build().writeTo(System.out);

        AnnotationSpec headerAnnotation = AnnotationSpec.builder(ClassName.get("cn.bingoogolapple.dynamic", "Header"))
                .addMember("accept", "$S", "application/txt; charset=utf-8")
                .addMember("userAgent", "$S", "BGA")
                .build();

        MethodSpec method1 = MethodSpec.methodBuilder("method1")
                .addJavadoc("多行注释\n")
                .addJavadoc("多行注释\n")
                .addAnnotation(headerAnnotation)
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(String.class, "p1")
                .addComment("单行注释")
                .addStatement("return p1")
                .build();

        AnnotationSpec headerListAnnotation = AnnotationSpec.builder(ClassName.get("cn.bingoogolapple.dynamic", "HeaderList"))
                .addMember("value", "$L", headerAnnotation)
                .addMember("value", "$L", headerAnnotation)
                .build();

        TypeSpec hello = TypeSpec.classBuilder("Hello")
                .addJavadoc("Hides {@code message} from the caller's history. Other\n"
                        + "participants in the conversation will continue to see the\n"
                        + "message in their own history unless they also delete it.\n")
                .addJavadoc("\n")
                .addJavadoc("<p>Use {@link #delete($T)} to delete the entire\n"
                        + "conversation for all participants.</p>\n", List.class)
                .addAnnotation(headerListAnnotation)
                .addMethod(method1)
                .build();

        JavaFile.builder("cn.bingoogolapple.dynamic", hello).build().writeTo(System.out);
    }

    @Test
    public void typeTest() throws IOException {
        JavaFile.builder("cn.bingoogolapple.dynamic", TypeSpec.classBuilder("Hello")
                .addTypeVariable(TypeVariableName.get("T"))
                .addField(int.class, "a", Modifier.PRIVATE) // 最终调的也是 TypeName.INT
                .addField(TypeName.INT, "b", Modifier.PRIVATE)
                .addField(Integer.class, "c", Modifier.PRIVATE)
                .addField(ClassName.get("java.lang", "Integer"), "d", Modifier.PRIVATE)
                .addField(int[].class, "e", Modifier.PRIVATE)
                .addField(ArrayTypeName.of(int.class), "f", Modifier.PRIVATE)
                .addField(ArrayTypeName.of(Integer.class), "g", Modifier.PRIVATE)
                .addField(ArrayTypeName.of(ClassName.get("java.lang", "Integer")), "h", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(List.class, String.class), "i", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(ClassName.get("java.util", "List"), ClassName.get("java.lang", "String")), "j", Modifier.PRIVATE)
                .addField(TypeVariableName.get("T"), "k", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(ClassName.get("java.util", "List"), TypeVariableName.get("T")), "l", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(ClassName.get("java.util", "List"), WildcardTypeName.subtypeOf(String.class)), "m", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(Map.class, String.class, File.class), "n", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(ClassName.get("java.util", "Map"), ClassName.get("java.lang", "String"), ClassName.get("java.io", "File")), "o", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(ClassName.get("java.util", "Map"), TypeVariableName.get("T"), WildcardTypeName.subtypeOf(String.class)), "p", Modifier.PRIVATE)
                .addField(ParameterizedTypeName.get(ClassName.get("java.util", "Map"), TypeVariableName.get("T"), WildcardTypeName.subtypeOf(ClassName.get("java.lang", "String"))), "q", Modifier.PRIVATE)
                .build()
        ).build().writeTo(System.out);
    }

    @Test
    public void codeBlockTest() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("food", "tacos");
        map.put("count", 3);

        System.out.println(CodeBlock.builder()
                .add("I ate $L $L\n", 3, "tacos") // 相对参数
                .addStatement("I ate $L $L", 3, "tacos") // 相对参数
                .add("I ate $2L $1L\n", "tacos", 3) // 位置参数
                .addStatement("I ate $2L $1L", "tacos", 3) // 位置参数
                .addNamed("I ate $count:L $food:L", map) // 名称参数
                .build().toString());
    }

    @Test
    public void universalTest() throws IOException {
        TypeSpec clazz = clazz(
                builtinTypeField(),          // int
                arrayTypeField(),            // int[]
                refTypeField(),              // File
                typeField(),                 // T
                parameterizedTypeField(),    // List<String>
                wildcardTypeField(),         // List<? extends String>
                constructor(),               // 构造函数
                method(code()));             // 普通方法
        JavaFile.builder("cn.bingoogolapple.dynamic", clazz).build().writeTo(System.out);
    }

    /**
     * `public abstract class Clazz<T> extends String implements Serializable, Comparable<String>, Comparable<? extends String> {
     * ...
     * }`
     */
    private static TypeSpec clazz(FieldSpec builtinTypeField, FieldSpec arrayTypeField, FieldSpec refTypeField,
                                  FieldSpec typeField, FieldSpec parameterizedTypeField, FieldSpec wildcardTypeField,
                                  MethodSpec constructor, MethodSpec methodSpec) {
        return TypeSpec.classBuilder("Clazz")
                // 限定符
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                // 泛型
                .addTypeVariable(TypeVariableName.get("T"))

                // 继承与接口
                .superclass(String.class)
                .addSuperinterface(Serializable.class)
                .addSuperinterface(ParameterizedTypeName.get(Comparable.class, String.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Map.class), TypeVariableName.get("T"), WildcardTypeName.subtypeOf(String.class)))

                // 初始化块
                .addStaticBlock(CodeBlock.builder().build())
                .addInitializerBlock(CodeBlock.builder().build())

                // 属性
                .addField(builtinTypeField)
                .addField(arrayTypeField)
                .addField(refTypeField)
                .addField(typeField)
                .addField(parameterizedTypeField)
                .addField(wildcardTypeField)

                // 方法 （构造函数也在此定义）
                .addMethod(constructor)
                .addMethod(methodSpec)

                // 内部类
                .addType(TypeSpec.classBuilder("InnerClass").build())

                .build();
    }

    /**
     * 内置类型
     */
    private static FieldSpec builtinTypeField() {
        // private int mInt;
        return FieldSpec.builder(int.class, "mInt", Modifier.PRIVATE).build();
    }

    /**
     * 数组类型
     */
    private static FieldSpec arrayTypeField() {
        // private int[] mArr;
        return FieldSpec.builder(int[].class, "mArr", Modifier.PRIVATE).build();
    }

    /**
     * 需要导入 import 的类型
     */
    private static FieldSpec refTypeField() {
        // private File mRef;
        return FieldSpec.builder(File.class, "mRef", Modifier.PRIVATE).build();
    }

    /**
     * 泛型
     */
    private static FieldSpec typeField() {
        // private File mT;
        return FieldSpec.builder(TypeVariableName.get("T"), "mT", Modifier.PRIVATE).build();
    }

    /**
     * 参数化类型
     */
    private static FieldSpec parameterizedTypeField() {
        // private List<String> mParameterizedField;
        return FieldSpec.builder(ParameterizedTypeName.get(List.class, String.class), "mParameterizedField", Modifier.PRIVATE).build();
    }

    /**
     * 通配符参数化类型
     */
    private static FieldSpec wildcardTypeField() {
        // private List<? extends String> mWildcardField;
        return FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), WildcardTypeName.subtypeOf(String.class)), "mWildcardField", Modifier.PRIVATE).build();
    }

    /**
     * 构造函数
     */
    private static MethodSpec constructor() {
        return MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();
    }

    /**
     * `@Override
     * public <T> Integer method(String string, T t, Map<Integer, ? extends T> map) throws IOException, RuntimeException {
     * ...
     * }`
     */
    private static MethodSpec method(CodeBlock codeBlock) {
        return MethodSpec.methodBuilder("method")
                .addAnnotation(Override.class)
                .addTypeVariable(TypeVariableName.get("T"))
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addParameter(String.class, "string")
                .addParameter(TypeVariableName.get("T"), "t")
                .addParameter(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(Integer.class), WildcardTypeName.subtypeOf(TypeVariableName.get("T"))), "map")
                .addException(IOException.class)
                .addException(RuntimeException.class)
                .addCode(codeBlock)
                .build();
    }

    /**
     * ‘method’ 方法中的具体语句
     */
    private static CodeBlock code() {
        return CodeBlock.builder()
                .addStatement("int foo = 1")
                .addStatement("$T bar = $S", String.class, "a string")

                // Object obj = new HashMap<Integer, ? extends T>(5);
                .addStatement("$T obj = new $T(5)", Object.class, ParameterizedTypeName.get(ClassName.get(HashMap.class), ClassName.get(Integer.class), WildcardTypeName.subtypeOf(TypeVariableName.get("T"))))

                // method(new Runnable(String param) {
                //   @Override
                //   void run() {
                //   }
                // });
                .addStatement("baz($L)", TypeSpec.anonymousClassBuilder("$T param", String.class)
                        .superclass(Runnable.class)
                        .addMethod(MethodSpec.methodBuilder("run")
                                .addAnnotation(Override.class)
                                .returns(TypeName.VOID)
                                .build())
                        .build())

                // for
                .beginControlFlow("for (int i = 0; i < 5; i++)")
                .endControlFlow()

                // while
                .beginControlFlow("while (false)")
                .endControlFlow()

                // do... while
                .beginControlFlow("do")
                .endControlFlow("while (false)")

                // if... else if... else...
                .beginControlFlow("if (false)")
                .nextControlFlow("else if (false)")
                .nextControlFlow("else")
                .endControlFlow()

                // try... catch... finally
                .beginControlFlow("try")
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("e.printStackTrace()")
                .nextControlFlow("finally")
                .endControlFlow()

                .addStatement("return 0")
                .build();
    }
}

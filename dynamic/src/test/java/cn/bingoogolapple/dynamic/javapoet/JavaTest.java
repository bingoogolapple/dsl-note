package cn.bingoogolapple.dynamic.javapoet;

import com.squareup.javapoet.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
}

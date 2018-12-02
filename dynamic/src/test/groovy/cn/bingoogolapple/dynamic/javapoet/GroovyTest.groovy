package cn.bingoogolapple.dynamic.javapoet

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import javax.lang.model.element.Modifier

@RunWith(JUnit4.class)
class GroovyTest {

    @Test
    void helloWorld() throws IOException {
        MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement('$T.out.println($S)', System.class, "Hello, Java!")
                .build()

        TypeSpec helloJava = TypeSpec.classBuilder("HelloGroovy")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mainMethod)
                .build()

        JavaFile javaFile = JavaFile.builder("cn.bingoogolapple.dynamic", helloJava)
                .build()

        javaFile.writeTo(System.out)
    }
}

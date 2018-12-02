package cn.bingoogolapple.dynamic.javapoet

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import javax.lang.model.element.Modifier

@RunWith(JUnit4::class)
class KotlinTest {

    @Test
    @Throws(IOException::class)
    fun helloWorld() {
        val mainMethod = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Void.TYPE)
                .addParameter(Array<String>::class.java, "args")
                .addStatement("\$T.out.println(\$S)", System::class.java, "Hello, Java!")
                .build()

        val helloJava = TypeSpec.classBuilder("HelloKotlin")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mainMethod)
                .build()

        val javaFile = JavaFile.builder("cn.bingoogolapple.dynamic", helloJava)
                .build()

        javaFile.writeTo(System.out)
    }
}
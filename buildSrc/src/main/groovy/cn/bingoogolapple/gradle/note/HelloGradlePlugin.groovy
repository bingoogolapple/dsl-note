package cn.bingoogolapple.gradle.note

import cn.bingoogolapple.gradle.note.extension.InnerExtension
import cn.bingoogolapple.gradle.note.extension.OuterExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main

class HelloGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "$project.name 应用了 HelloGradlePlugin"

        // 通过 project.extensions.create 创建外层扩展。第三个参数为可变长参数，表示模型的构造参数
        project.extensions.create('outer', OuterExtension, project)
        // 创建好外层扩展 outer 后，通过 project.outer.extensions.create 创建嵌套内层扩展
        project.outer.extensions.create('innerThree', InnerExtension)

        // 创建自定义任务
        project.task('helloGradleTask', type: HelloGradleTask)

        // 这里是配置阶段，取不到扩展属性的值
        println "HelloGradlePlugin 配置阶段 outer $project.outer"
        println "HelloGradlePlugin 配置阶段 innerOne $project.outer.oneInnerExtension"
        println "HelloGradlePlugin 配置阶段 innerTwo $project.outer.twoInnerExtension"
        println "HelloGradlePlugin 配置阶段 innerThree $project.outer.innerThree"

        project.afterEvaluate { Project target ->
            // 需要在配置阶段结束后才能取到扩展属性的值
            println "HelloGradlePlugin 配置阶段结束后 outer $target.outer"
            println "HelloGradlePlugin 配置阶段结束后 innerOne $target.outer.oneInnerExtension"
            println "HelloGradlePlugin 配置阶段结束后 innerTwo $target.outer.twoInnerExtension"
            println "HelloGradlePlugin 配置阶段结束后 innerThree $target.outer.innerThree"
        }

        aspectj(project)
    }

    void aspectj(Project project) {
        if (!hasAndroidPlugin(project)) {
            return
        }

        project.dependencies {
            debugImplementation 'org.aspectj:aspectjrt:1.9.2'
        }

        DomainObjectSet<BaseVariant> variants
        if (hasAppPlugin(project)) {
            variants = project.extensions.findByType(AppExtension).applicationVariants
//            variants = ((AppExtension) project.plugins.getPlugin(AppPlugin).extension).applicationVariants
        } else {
            variants = project.extensions.findByType(LibraryExtension).libraryVariants
//            variants = ((LibraryExtension) project.plugins.getPlugin(LibraryPlugin).extension).libraryVariants
        }

        variants.all { BaseVariant variant ->
            if (!variant.buildType.debuggable) {
                project.logger.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
                return
            }

            JavaCompile javaCompileTask = variant.javaCompile
            javaCompileTask.doLast {
                String[] args = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", javaCompileTask.destinationDir.toString(),
                                 "-aspectpath", javaCompileTask.classpath.asPath,
                                 "-d", javaCompileTask.destinationDir.toString(),
                                 "-classpath", javaCompileTask.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                project.logger.debug "aspectj args:" + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true)
                new Main().run(args, handler)
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            project.logger.error message.message, message.thrown
                            break
                        case IMessage.WARNING:
                            project.logger.warn message.message, message.thrown
                            break
                        case IMessage.INFO:
                            project.logger.info message.message, message.thrown
                            break
                        case IMessage.DEBUG:
                            project.logger.debug message.message, message.thrown
                            break
                    }
                }
            }
        }
    }

    static boolean hasAndroidPlugin(Project project) {
        return hasAppPlugin(project) || hasLibraryPlugin(project)
    }

    static boolean hasAppPlugin(Project project) {
        return project.plugins.hasPlugin(AppPlugin)
    }

    static boolean hasLibraryPlugin(Project project) {
        return project.plugins.hasPlugin(LibraryPlugin)
    }
}

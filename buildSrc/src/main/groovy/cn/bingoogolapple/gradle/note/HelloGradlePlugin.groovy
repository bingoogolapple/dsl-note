package cn.bingoogolapple.gradle.note

import cn.bingoogolapple.gradle.note.extension.InnerExtension
import cn.bingoogolapple.gradle.note.extension.OuterExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

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
    }
}

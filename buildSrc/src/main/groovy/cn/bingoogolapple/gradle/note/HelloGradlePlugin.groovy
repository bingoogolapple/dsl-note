package cn.bingoogolapple.gradle.note

import cn.bingoogolapple.gradle.note.extension.InnerExtension
import cn.bingoogolapple.gradle.note.extension.OuterExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "$project.name 应用了 HelloGradlePlugin"

        // 添加扩展
        project.extensions.create('outer', OuterExtension)
        project.outer.extensions.create('innerThree', InnerExtension)

        project.task('customTask', type: CustomTask)

        // 这里取不到值，需要在配置阶段结束后才能取到值
        println "HelloGradlePlugin 配置阶段 outer $project.outer"
        println "HelloGradlePlugin 配置阶段 innerOne $project.outer.oneInnerExtension"
        println "HelloGradlePlugin 配置阶段 innerTwo $project.outer.twoInnerExtension"
        println "HelloGradlePlugin 配置阶段 innerThree $project.outer.innerThree"

        project.afterEvaluate { Project target ->
            println "HelloGradlePlugin 配置阶段结束后 outer $target.outer"
            println "HelloGradlePlugin 配置阶段结束后 innerOne $target.outer.oneInnerExtension"
            println "HelloGradlePlugin 配置阶段结束后 innerTwo $target.outer.twoInnerExtension"
            println "HelloGradlePlugin 配置阶段结束后 innerThree $target.outer.innerThree"
        }
    }
}

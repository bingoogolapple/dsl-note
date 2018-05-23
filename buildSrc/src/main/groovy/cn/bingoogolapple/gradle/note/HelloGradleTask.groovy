package cn.bingoogolapple.gradle.note

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class HelloGradleTask extends DefaultTask {

    HelloGradleTask() {
        group = 'first-gradle-plugin'
        description = '演示自定义 Task'
    }

    @TaskAction
    void run() {
        // 执行阶段能取到扩展属性的值
        println "HelloGradleTask action outer $project.outer"
        println "HelloGradleTask action innerOne $project.outer.oneInnerExtension"
        println "HelloGradleTask action innerTwo $project.outer.twoInnerExtension"
        println "HelloGradleTask action innerThree $project.outer.innerThree"
    }
}

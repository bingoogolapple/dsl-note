package cn.bingoogolapple.gradle.note

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CustomTask extends DefaultTask {

    CustomTask() {
        group = 'first-gradle-plugin'
        description = '演示自定义 Task'
    }

    @TaskAction
    void run() {
        println "CustomTask action outer $project.outer"
        println "CustomTask action innerOne $project.outer.oneInnerExtension"
        println "CustomTask action innerTwo $project.outer.twoInnerExtension"
        println "CustomTask action innerThree $project.outer.innerThree"
    }
}

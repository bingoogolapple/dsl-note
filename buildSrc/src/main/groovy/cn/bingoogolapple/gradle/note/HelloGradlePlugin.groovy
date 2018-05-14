package cn.bingoogolapple.gradle.note;

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "project.name 应用了 HelloGradlePlugin"
    }
}

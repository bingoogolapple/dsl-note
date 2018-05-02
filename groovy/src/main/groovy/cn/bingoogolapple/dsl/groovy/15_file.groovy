package cn.bingoogolapple.dsl.groovy

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder


def test1() {
    def file = new File("WanAndroidBanner.json")
//file.eachLine {
//    println it
//}

//println file.readLines()

    println file.text
}
//test1()

def test2() {
    def file = new File("WanAndroidBanner.json")
    // 读取文件部分内容。Groovy 已经在内部关闭了流，开发者不需要在外面再次关闭
    def reader = file.withReader { reader ->
        char[] buffer = new char[100]
        reader.read(buffer)
        return buffer
    }
    println reader
}
//test2()

def copy(String sourcePath, String destPath) {
    try {
        def destFile = new File(destPath)
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        new File(sourcePath).withReader { reader ->
            def lines = reader.readLines()
            destFile.withWriter { writer ->
                lines.each { line ->
                    writer.append(line + "\r\n")
                }
            }
        }
        return true
    } catch (Exception e) {
        e.printStackTrace()
    }
    return false
}

def test3() {
    copy('WanAndroidBanner.json', 'WanAndroidBanner2.json')
    println new File('WanAndroidBanner2.json').text
    new File('WanAndroidBanner2.json').deleteOnExit()
}
//test3()

def saveObject(Object obj, String destPath) {
    try {
        def destFile = new File(destPath)
        if (!destFile.exists()) {
            destFile.createNewFile()
        }
        destFile.withObjectOutputStream { ObjectOutputStream out ->
            out.writeObject(obj)
        }
        return true
    } catch (Exception e) {
        e.printStackTrace()
        return false
    }
    return false
}

def readObject(String path) {
    def obj = null
    try {
        def file = new File(path)
        if (file == null || !file.exists()) return null
        file.withObjectInputStream { input ->
            obj = input.readObject()
        }
    } catch (Exception e) {
        e.printStackTrace()
    }
    return obj
}

def test4() {
    def filePath = 'person.bin'
    def person = new Person(name: 'BGA', age: 26)
    saveObject(person, filePath)

    def result = (Person) readObject(filePath)
    println "the name is ${result.name} and the age is ${result.age}"

    new File(filePath).deleteOnExit()
}
//test4()

def writeXml() {
    def responseText = 'http://www.wanandroid.com/banner/json'.toURL().text
    def responseJson = new JsonSlurper().parseText(responseText)

    new File('WanAndroidBanner.xml').withWriter { writer ->
        // 用来生成 xml 数据的核心类
        def xmlBuilder = new MarkupBuilder(writer)
        //根结点 ResponseData 创建成功
        xmlBuilder.ResponseData() {
            errorCode(responseJson.errorCode)
            errorMsg(responseJson.errorMsg)
            data() {
                responseJson.data.each {
                    banner(desc: it.desc, id: it.id, title: it.title, it.imagePath)
                }
            }
        }
    }
}

writeXml()
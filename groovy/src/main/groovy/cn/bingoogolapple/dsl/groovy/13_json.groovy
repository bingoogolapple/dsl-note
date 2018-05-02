package cn.bingoogolapple.dsl.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def list = [new Person(name: 'BGA', age: 26),
            new Person(name: 'bingoogolapple', age: 26)]
def jsonStr = JsonOutput.toJson(list)
println JsonOutput.prettyPrint(jsonStr)

def jsonSlurper = new JsonSlurper()
println jsonSlurper.parseText(jsonStr)

def responseText = 'http://www.wanandroid.com/banner/json'.toURL().text
def responseJson = new JsonSlurper().parseText(responseText)
println responseJson.data[0].desc
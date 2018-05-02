package cn.bingoogolapple.dsl.groovy

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def list = [new Person(name: 'BGA', age: 26),
            new Person(name: 'bingoogolapple', age: 26)]
def jsonStr = JsonOutput.toJson(list)
println JsonOutput.prettyPrint(jsonStr)

def jsonSlurper = new JsonSlurper()
println jsonSlurper.parseText(jsonStr)

static def getNetworkData(String url) {
    def connection = new URL(url).openConnection()
    connection.setRequestMethod('GET')
    connection.connect()
    def resp = connection.content.text
    def jsonSlurper = new JsonSlurper()
    return jsonSlurper.parseText(resp)
}
def response = getNetworkData('http://www.wanandroid.com/banner/json')
println response.data[0].desc
package cn.bingoogolapple.dsl.groovy

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

static def getNetworkData(String url) {
    def connection = new URL(url).openConnection()
    connection.setRequestMethod('GET')
    connection.connect()
    def responseText = connection.content.text
    def jsonSlurper = new JsonSlurper()
    return jsonSlurper.parseText(responseText)
}

static def writeXml(responseData) {
    def stringWriter = new StringWriter()
    // 用来生成 xml 数据的核心类
    def xmlBuilder = new MarkupBuilder(stringWriter)
    //根结点 ResponseData 创建成功
    xmlBuilder.ResponseData() {
        errorCode(responseData.errorCode)
        errorMsg(responseData.errorMsg)
        data() {
            responseData.data.each {
                banner(desc: it.desc, id: it.id, title: it.title, it.imagePath)
            }
        }
    }
    println stringWriter
}

def netResponse = getNetworkData('http://www.wanandroid.com/banner/json')
writeXml(netResponse)
println '------------------------------------------------------------------------'

def xmlStr = '''\
<ResponseData>
  <errorCode>0</errorCode>
  <errorMsg></errorMsg>
  <data>
    <banner desc='最新项目上线啦~' id='13' title='最新项目上线啦~'>http://www.wanandroid.com/blogimgs/5ae04af4-72b9-4696-81cb-1644cdcd2d29.jpg</banner>
    <banner desc='' id='6' title='我们新增了一个常用导航Tab~'>http://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png</banner>
    <banner desc='一起来做个App吧' id='10' title='一起来做个App吧'>http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png</banner>
    <banner desc='' id='7' title='送你一个暖心的Mock API工具'>http://www.wanandroid.com/blogimgs/ffb61454-e0d2-46e7-bc9b-4f359061ae20.png</banner>
    <banner desc='' id='4' title='看看别人的面经，搞定面试~'>http://www.wanandroid.com/blogimgs/ab17e8f9-6b79-450b-8079-0f2287eb6f0f.png</banner>
    <banner desc='' id='3' title='兄弟，要不要挑个项目学习下?'>http://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png</banner>
    <banner desc='加个友情链接吧~' id='11' title='加个友情链接吧~'>http://www.wanandroid.com/blogimgs/84810df6-adf1-45bc-b3e2-294fa4e95005.png</banner>
    <banner desc='' id='2' title='JSON工具'>http://www.wanandroid.com/blogimgs/90cf8c40-9489-4f9d-8936-02c9ebae31f0.png</banner>
    <banner desc='' id='5' title='微信文章合集'>http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png</banner>
  </data>
</ResponseData>\
'''
def xmlSluper = new XmlSlurper()
// 返回的就是根节点 ResponseData
def xmlResponse = xmlSluper.parseText(xmlStr)
println xmlResponse.getClass() // class groovy.util.slurpersupport.NodeChild
println xmlResponse.errorCode
println xmlResponse.data.banner[0].@desc // 取属性用 @ 开头
println xmlResponse.data.banner[0].text() // 取值用 text()
println '------------------------------------------------------------------'

def titleList = []
xmlResponse.data.banner.each { banner ->
    if (banner.@id.text().contains('3')) {
        titleList.add(banner.@title.text())
    }
}
println titleList.toListString()

println '------------------ 深度遍历 xml 数据 ------------------'
// 不 collect 时拿到的是最里面子结点的文本
def contentList = xmlResponse.depthFirst().findAll { banner -> banner.@id.text().contains('3') }
println contentList.toListString()
// 单引号里两个星号也表示深度遍历
println xmlResponse.'**'.findAll { banner -> banner.@id.text().contains('3') }
println xmlResponse.'**'.find { banner -> banner.@id.text().contains('3') }.@title

titleList = xmlResponse.depthFirst().findAll { banner ->
    banner.@id.text().contains('3')
}.collect { banner ->
    return banner.@title.text()
}
println titleList

println '------------------ 广度遍历 xml 数据 ------------------'
titleList = xmlResponse.data.children().findAll { banner ->
    banner.@id.text().contains('3')
}.collect { banner ->
    return banner.@title.text()
}
println titleList
// 单引号里两个星号也表示广度遍历
println xmlResponse.data.'*'.findAll { banner ->
    banner.@id.text().contains('3')
}.collect { banner ->
    return banner.@title.text()
}


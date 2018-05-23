package cn.bingoogolapple.dsl.groovy

import groovy.swing.SwingBuilder

System.in.withReader {
    print '> 请输入用户名:'
    String username = it.readLine()
    println "用户名为：$username"
    print '> 请输入密码:'
    String password = it.readLine()
    println "密码为：$password"
}

// 有写上门的 System.in.withReader 的话这里的 console 就会为空
Console console = System.console()
if (console != null) {
    println "console 不为空"
    print '> 请输入用户名:'
    String username = console.readLine()
    println "用户名为：$username"
    print '> 请输入密码:'
    String password = console.readPassword().toString()
    println "密码为：$password"
} else {
    println "console 为空"
    def readUsername = javax.swing.JOptionPane.&showInputDialog
    String username = readUsername '请输入用户名'
    println "用户名为：$username"
    def readPassword = javax.swing.JOptionPane.&showInputDialog
    String password = readPassword '请输入密码'
    println "密码为：$password"
}


String username
String password
new SwingBuilder().edt {
    dialog(modal: true, title: '用户登录', alwaysOnTop: true, resizable: false,
            locationRelativeTo: null, pack: false, size: [200, 150], show: true
    ) {
        vbox {
            label text: '请输入用户名:'
            textField id: 'usernameTf', input = textField()
            label text: '请输入密码:'
            textField id: 'passwordTf', input = passwordField()
            button defaultButton: true, text: 'OK', actionPerformed: {
                username = usernameTf.text
                password = passwordTf.text
                dispose()
            }
        }
    }
}
println "用户名为 $username"
println "密码为 $password"
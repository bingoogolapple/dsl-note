package cn.bingoogolapple.dsl.moduleone;

import cn.bingoogolapple.dsl.moduletwo.ModuleTwo;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:2018/5/3
 * 描述:
 */
public final class ModuleOne {
    private ModuleOne() {
    }

    public static String getString() {
        return "ModuleOne " + ModuleTwo.getString();
    }
}

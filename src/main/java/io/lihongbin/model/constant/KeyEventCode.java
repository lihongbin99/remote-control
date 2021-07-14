package io.lihongbin.model.constant;

public enum KeyEventCode {

    /**
     * 测试
     */
    TEST(187),

    /**
     * 打开屏幕
     */
    WAKEUP(224),
    /**
     * 关闭屏幕
     */
    POWER(26),

    /**
     * 主页
     */
    HOME(3),
    /**
     * 返回
     */
    BACK(4),
    /**
     * 菜单(解锁)
     */
    MENU(82),
    /**
     * 任务列表
     */
    APP_SWITCH(187),
    ;

    public int code;

    KeyEventCode(int code) {
        this.code = code;
    }
}

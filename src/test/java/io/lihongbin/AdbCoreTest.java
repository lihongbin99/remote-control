package io.lihongbin;

import io.lihongbin.config.GlobalConfig;
import io.lihongbin.core.AdbCore;
import io.lihongbin.core.ImageCore;
import io.lihongbin.model.constant.KeyEventCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AdbCoreTest {
    private static GlobalConfig globalConfig;
    private static AdbCore adbCore;

    @BeforeAll
    public static void before() throws IOException, InterruptedException {
        globalConfig = new GlobalConfig();
        globalConfig.afterPropertiesSet();

        ImageCore imageCore = new ImageCore(globalConfig);

        adbCore = new AdbCore(globalConfig, imageCore);
        adbCore.afterPropertiesSet();
    }

    @Test
    public void getSizeTest() {
        System.out.println("X = " + adbCore.getX());
        System.out.println("Y = " + adbCore.getY());
    }

    @Test
    public void keyEventTest() throws IOException, InterruptedException {
        adbCore.keyevent(KeyEventCode.TEST);
    }

}

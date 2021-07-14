package io.lihongbin.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Data
@Configuration
@ConfigurationProperties("remote.control")
public class GlobalConfig implements InitializingBean {

    /**
     * ImageMagick 的安装路径
     */
    private String searchPath = "D:\\software\\ImageMagick";

    /**
     * 保存临时文件的文件夹
     */
    private String tempDirectory = "D:\\temp";

    /**
     * adb的路径
     */
    private String adb = "D:\\software\\platform-tools\\adb.exe";

    /**
     * 手机设备
     */
    private String devices = "192.168.1.182:5555";

    @Override
    public void afterPropertiesSet() {
        adb     += " ";
        devices = " " + devices + " ";
        if (!tempDirectory.endsWith(File.separator)) {
            tempDirectory += File.separator;
        }
    }
}

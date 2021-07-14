package io.lihongbin.core;

import io.lihongbin.config.GlobalConfig;
import io.lihongbin.model.constant.KeyEventCode;
import lombok.Data;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Data
@Component
public class AdbCore implements InitializingBean {
    private final static Runtime runtime = Runtime.getRuntime();

    /**
     * 全局配置
     */
    private final GlobalConfig globalConfig;

    /**
     * 图片处理类
     */
    private final ImageCore imageCore;

    public AdbCore(GlobalConfig globalConfig, ImageCore imageCore) {
        this.globalConfig = globalConfig;
        this.imageCore = imageCore;
    }

    private String srcTempFile;
    private String targetTempFile;
    private Integer x;
    private Integer y;

    @Override
    public void afterPropertiesSet() throws IOException, InterruptedException {
        this.srcTempFile = globalConfig.getTempDirectory() + "temp.png";
        this.targetTempFile = globalConfig.getTempDirectory() + "temp.jpg";
        // 获取手机分辨率
        link();
        String vmSize = adbExec("shell wm size").trim();
        vmSize = vmSize.substring(vmSize.lastIndexOf(" ") + 1);
        String[] xy = vmSize.split("x");
        this.x = Integer.valueOf(xy[0]);
        this.y = Integer.valueOf(xy[1]);
    }

    public void link() throws IOException, InterruptedException {
        exec(globalConfig.getAdb() + "connect" + globalConfig.getDevices());
    }

    public String screencap() throws IOException, InterruptedException, IM4JavaException {
        link();// 连接手机
        adbExec("exec-out screencap -p > " + srcTempFile);// 生成截图
        imageCore.imageMagick(srcTempFile, targetTempFile);// 压缩图片
        return targetTempFile;// 返回压缩后的截图
    }

    public void tap(int x, int y) throws IOException, InterruptedException {
        link();
        adbExec("shell input tap " + x + " " + y);
    }

    public void swipe(int sx, int sy, int ex, int ey) throws IOException, InterruptedException {
        link();
        adbExec("shell input swipe " + sx + " " + sy + " " + ex + " " + ey);
    }

    public void keyevent(KeyEventCode keyEventCode) throws IOException, InterruptedException {
        link();
        adbExec("shell input keyevent " + keyEventCode.code);
    }


    /******************************************************************************************************************/

    private String adbExec(String cmd) throws IOException, InterruptedException {
        return exec(globalConfig.getAdb() + "-s" + globalConfig.getDevices() + cmd);
    }

    private String exec(String cmd) throws IOException, InterruptedException {
        String result;
        System.out.println("--------------------------------------start----------------------------------------------");
        System.out.println("exec: " + cmd);
        Process process = runtime.exec("cmd /c " + cmd);
        int exitValue = process.waitFor();
        System.out.println("result: " + exitValue);

        if (exitValue == 0) {
            InputStream inputStream = process.getInputStream();
            byte[] bytes = new byte[inputStream.available()];
            int len = inputStream.read(bytes);
            result = new String(bytes, 0, len);
            System.out.println(result);
        } else {
            InputStream inputStream = process.getErrorStream();
            byte[] bytes = new byte[inputStream.available()];
            int len = inputStream.read(bytes);
            throw new RuntimeException(new String(bytes, 0, len, "GBK"));
        }
        System.out.println("--------------------------------------end------------------------------------------------");
        return result;
    }
}

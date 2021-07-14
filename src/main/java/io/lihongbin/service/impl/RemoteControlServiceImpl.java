package io.lihongbin.service.impl;

import io.lihongbin.core.AdbCore;
import io.lihongbin.model.constant.KeyEventCode;
import io.lihongbin.service.RemoteControlService;
import lombok.AllArgsConstructor;
import org.im4java.core.IM4JavaException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
public class RemoteControlServiceImpl implements RemoteControlService {

    private final AdbCore adbCore;

    private static String result;
    private final static Lock lock = new ReentrantLock();
    @Override
    public String screencap() throws IOException, InterruptedException, IM4JavaException {
        boolean lockResult = false;
        try {
            lockResult = lock.tryLock(1L, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) { }

        if (lockResult) {
            String screencap = adbCore.screencap();
            byte[] bytes = FileCopyUtils.copyToByteArray(new File(screencap));
            result = Base64Utils.encodeToString(bytes);
            synchronized (this) {
                lock.unlock();
                this.notifyAll();
            }
        } else {
            synchronized (this) {
                wait();
            }
        }


        return result;
    }

    @Override
    public void tap(int clientX, int clientY, int imageHeight) throws IOException, InterruptedException {
        int phoneX = adbCore.getX();
        int phoneY = adbCore.getY();

        int imageWidth =  (int) (((double) imageHeight / phoneY) * phoneX);

        int x = (int) (((double) clientX / imageWidth) * phoneX);
        int y = (int) (((double) clientY / imageHeight) * phoneY);

        adbCore.tap(x, y);
    }

    @Override
    public void swipe(int clientSx, int clientSy, int clientEx, int clientEy, int imageHeight) throws IOException, InterruptedException {
        int phoneX = adbCore.getX();
        int phoneY = adbCore.getY();

        int imageWidth =  (int) (((double) imageHeight / phoneY) * phoneX);

        int sx = (int) (((double) clientSx / imageWidth) * phoneX);
        int sy = (int) (((double) clientSy / imageHeight) * phoneY);

        int ex = (int) (((double) clientEx / imageWidth) * phoneX);
        int ey = (int) (((double) clientEy / imageHeight) * phoneY);

        adbCore.swipe(sx, sy, ex, ey);
    }

    @Override
    public void keyevent(KeyEventCode keyEventCode) throws IOException, InterruptedException {
        adbCore.keyevent(keyEventCode);
    }
}

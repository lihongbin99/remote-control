package io.lihongbin.service;

import io.lihongbin.model.constant.KeyEventCode;
import org.im4java.core.IM4JavaException;

import java.io.IOException;

public interface RemoteControlService {

    String screencap() throws IOException, InterruptedException, IM4JavaException;

    void tap(int clientX, int clientY, int imageHeight) throws IOException, InterruptedException;

    void swipe(int clientSx, int clientSy, int clientEx, int clientEy, int imageHeight) throws IOException, InterruptedException;

    void keyevent(KeyEventCode keyEventCode) throws IOException, InterruptedException;

}

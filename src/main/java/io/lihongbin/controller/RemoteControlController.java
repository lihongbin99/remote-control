package io.lihongbin.controller;

import io.lihongbin.model.to.KeyeventTo;
import io.lihongbin.model.to.SwipeTo;
import io.lihongbin.model.to.TapTo;
import io.lihongbin.model.entity.Result;
import io.lihongbin.service.RemoteControlService;
import lombok.AllArgsConstructor;
import org.im4java.core.IM4JavaException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("android")
public class RemoteControlController {

    private final RemoteControlService remoteControlService;

    @GetMapping("screencap")
    public Result<String> screencap() throws IOException, InterruptedException, IM4JavaException {
        String imageBase64 = remoteControlService.screencap();
        return Result.success(imageBase64);
    }

    @PostMapping("tap")
    public Result<Object> tap(@RequestBody TapTo to) throws IOException, InterruptedException {
        remoteControlService.tap(to.getX(), to.getY(), to.getImageHeight());
        return Result.success();
    }

    @PostMapping("swipe")
    public Result<Object> swipe(@RequestBody SwipeTo to) throws IOException, InterruptedException {
        remoteControlService.swipe(to.getSx(), to.getSy(), to.getEx(), to.getEy(), to.getImageHeight());
        return Result.success();
    }

    @PostMapping("keyevent")
    public Result<Object> keyevent(@RequestBody KeyeventTo to) throws IOException, InterruptedException {
        remoteControlService.keyevent(to.getKeyEventCode());
        return Result.success();
    }

}

package io.lihongbin.core;

import io.lihongbin.config.GlobalConfig;
import lombok.AllArgsConstructor;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ImageCore {

    private final GlobalConfig globalConfig;

    public void imageMagick(String src, String target) throws IOException, InterruptedException, IM4JavaException {
        IMOperation operation = new IMOperation();
        operation.addImage(src);
        operation.addImage(target);

        ConvertCmd cmd = new ConvertCmd();
        cmd.setSearchPath(globalConfig.getSearchPath());
        cmd.run(operation);
    }

}

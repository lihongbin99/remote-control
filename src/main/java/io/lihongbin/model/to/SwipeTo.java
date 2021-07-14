package io.lihongbin.model.to;

import lombok.Data;

@Data
public class SwipeTo {

    /**
     * 起始X轴坐标
     */
    private Integer sx;

    /**
     * 起始Y轴坐标
     */
    private Integer sy;

    /**
     * 结束X轴坐标
     */
    private Integer ex;

    /**
     * 结束Y轴坐标
     */
    private Integer ey;

    /**
     * 浏览器中的图片高度
     */
    private Integer imageHeight;

}

package com.qpz.tank.single;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author pengzhan.qian
 * @since 2025/6/3 13:22
 **/
public class ImageUtil {


    /**
     * 动态调整图片的朝向位置
     *
     * @param bufferedimage
     * @param degree
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}

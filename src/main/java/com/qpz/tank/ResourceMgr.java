package com.qpz.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * 资源管理类
 *
 * @author pengzhan.qian
 * @since 2025/5/30 22:01
 **/
public class ResourceMgr {
    public static BufferedImage tankL;
    public static BufferedImage tankU;
    public static BufferedImage tankR;
    public static BufferedImage tankD;

    public static BufferedImage bulletL;
    public static BufferedImage bulletU;
    public static BufferedImage bulletR;
    public static BufferedImage bulletD;

    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            tankL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif")));
            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif")));
            tankR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif")));
            tankD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif")));

            bulletL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif")));
            bulletU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif")));
            bulletR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif")));
            bulletD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif")));

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

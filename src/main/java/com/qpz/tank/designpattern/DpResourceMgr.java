package com.qpz.tank.designpattern;

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
public class DpResourceMgr {

    public static BufferedImage goodTankL1;
    public static BufferedImage goodTankU1;
    public static BufferedImage goodTankR1;
    public static BufferedImage goodTankD1;
    public static BufferedImage badTankL1;
    public static BufferedImage badTankU1;
    public static BufferedImage badTankR1;
    public static BufferedImage badTankD1;
    public static BufferedImage goodTankL2;
    public static BufferedImage goodTankU2;
    public static BufferedImage goodTankR2;
    public static BufferedImage goodTankD2;
    public static BufferedImage badTankL2;
    public static BufferedImage badTankU2;
    public static BufferedImage badTankR2;
    public static BufferedImage badTankD2;

    public static BufferedImage bulletL;
    public static BufferedImage bulletU;
    public static BufferedImage bulletR;
    public static BufferedImage bulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
//            tankL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif")));
//            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif")));
//            tankR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif")));
//            tankD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif")));
//            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png")));
//            tankL = ImageUtil.rotateImage(tankU, -90);
//            tankR = ImageUtil.rotateImage(tankU, 90);
//            tankD = ImageUtil.rotateImage(tankU, 180);

            goodTankU1 = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png")));
            goodTankL1 = DpImageUtil.rotateImage(goodTankU1, -90);
            goodTankR1 = DpImageUtil.rotateImage(goodTankU1, 90);
            goodTankD1 = DpImageUtil.rotateImage(goodTankU1, 180);

            badTankU1 = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png")));
            badTankL1 = DpImageUtil.rotateImage(badTankU1, -90);
            badTankR1 = DpImageUtil.rotateImage(badTankU1, 90);
            badTankD1 = DpImageUtil.rotateImage(badTankU1, 180);

            goodTankU2 = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank2.png")));
            goodTankL2 = DpImageUtil.rotateImage(goodTankU2, -90);
            goodTankR2 = DpImageUtil.rotateImage(goodTankU2, 90);
            goodTankD2 = DpImageUtil.rotateImage(goodTankU2, 180);

            badTankU2 = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank2.png")));
            badTankL2 = DpImageUtil.rotateImage(badTankU2, -90);
            badTankR2 = DpImageUtil.rotateImage(badTankU2, 90);
            badTankD2 = DpImageUtil.rotateImage(badTankU2, 180);

//            bulletL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif")));
//            bulletU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif")));
//            bulletR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif")));
//            bulletD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif")));

            bulletU = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png")));
            bulletL = DpImageUtil.rotateImage(bulletU, -90);
            bulletR = DpImageUtil.rotateImage(bulletU, 90);
            bulletD = DpImageUtil.rotateImage(bulletU, 180);

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DpResourceMgr() {
    }

    public static DpResourceMgr getInstance() {
        return DpResourceMgrHolder.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(DpResourceMgr.getInstance().hashCode())).start();
        }
    }

    private static class DpResourceMgrHolder {
        private static final DpResourceMgr INSTANCE = new DpResourceMgr();
    }
}

package com.qpz.tank.test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.Assert.assertNotNull;

/**
 * @author pengzhan.qian
 * @since 2025/5/30 21:28
 **/
public class ImageTest {

    @Test
    public void test() {
        // 绝对路径读取图片 不适用
        try {
            BufferedImage image = ImageIO.read(new File("E:\\pri-workspace\\java\\msb_tank\\src\\main\\resources\\images\\0.gif"));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 相对路径读取
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("images/bulletD.gif");
            assertNotNull(inputStream);
            BufferedImage image = ImageIO.read(inputStream);
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

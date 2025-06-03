package com.qpz.tank.dp;

import java.io.IOException;
import java.util.Properties;

/**
 * @author pengzhan.qian
 * @since 2025/6/3 16:26
 **/
public class DpPropertyMgr {

    public static Properties props = new Properties();

    static {
        try {
            props.load(DpPropertyMgr.class.getClassLoader().getResourceAsStream("config"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DpPropertyMgr() {
    }

    public static String getProperty(String key) {
        if (props == null) return null;
        return props.getProperty(key);
    }

    public static Integer getIntProperty(String key) {
        if (props == null) return 0;
        return Integer.parseInt(props.getProperty(key));
    }

    public static void main(String[] args) {
        String initTankCount = getProperty("initTankCount");
        assert initTankCount != null;
        System.out.println(Integer.parseInt(initTankCount));
    }

}

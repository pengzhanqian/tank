/**
 * 工厂模式 处理 坦克 子弹 爆炸
 *
 * @author pengzhan.qian
 * @since 2025/6/4 15:53
 **/
package com.qpz.tank.designpattern.factory;

/*
 * 1、先抽象爆炸，这个最简单 可以看下 DpExplode 类 爆炸抽象完 只需要1个方法 paint 和 die  即可
 *
 * 2、然后抽象子弹 可以发现, 子弹主要包含 paint 和 移动 碰撞检测 死亡 4个方法
 *
 * 3、
 */
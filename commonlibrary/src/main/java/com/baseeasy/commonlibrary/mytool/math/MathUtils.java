package com.baseeasy.commonlibrary.mytool.math;

import android.graphics.Point;

/**
 * 作者：WangZhiQiang
 * 时间：2016/7/20/
 * 邮箱：sos181@163.com
 * 描述：
 *
 */

public class MathUtils {

    /**
     * 创建人： Wang   时间： 2016/7/20  11:48
     * 说明：已知两点坐标，求与X轴夹角
     * MathUtils.azimuth_angle(0, 0, -2, 2);135
     * MathUtils.azimuth_angle(0, 0, 2, 2);45
     * MathUtils.azimuth_angle(0, 0, 2, -2);-45
     * MathUtils.azimuth_angle(0, 0, -2, -2);-135
     *
     * @return 夹角
     */
    public static double azimuth_angle(double x1, double y1, double x2, double y2) {

        return Math.atan2((y2 - y1), (x2 - x1)) * 180 / Math.PI;
    }


    /**
     * 作者：WangZhiQiang
     * 时间：2018/11/13
     * 邮箱：sos181@163.com
     * 描述：已知圆心、半径、以及夹角求对应点的坐标
     */
    public static Point round_point(Point p1, float r, double angle){
        double x1 = p1.x + r * Math.cos(angle * Math.PI / 180);
        double y1 = p1.y + r * Math.sin(angle * Math.PI / 180);
        Point point =new Point((int) x1,(int) y1);
        return point;
    }


    /**
     * 创建人： Wang   时间： 2016/7/20  11:48
     * 说明：
     *
     * @param x     圆心x
     * @param r     半径
     * @param angle 夹角
     * @return 圆上点的x坐标
     */

    public static double round_x(float x, float r, double angle) throws Exception {
//        x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
        double x1 = x + r * Math.cos(angle * Math.PI / 180);
        return x1;
    }
    //


    /**
     * 创建人： Wang   时间： 2016/7/20  11:50
     * 说明：
     *
     * @param y     圆心x
     * @param r     半径
     * @param angle 夹角
     * @return 圆上点的y坐标
     */
    public static double round_y(float y, float r, double angle) throws Exception {
        // y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
        double y1 = y + r * Math.sin(angle * Math.PI / 180);
        return y1;
    }


    /**
     * 作者：WangZhiQiang
     * 时间：2018/11/13
     * 邮箱：sos181@163.com
     * 描述：已知两点坐标求两点间距离
     */
    public  static double  distance(Point p1, Point p2){
        double distance = Math.sqrt(Math.abs((p1.x - p2.x)
                * (p1.x - p2.x)+(p1.y - p2.y)
                * (p1.y - p2.y)));
        return distance;
    }
}

package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：
 */

public class AutoTrigger {

    static Timer timerFocus = null;

    static Timer timerAutoTake = null;

    /**
     * 对焦频率
     */
    static final long cameraFocusInterval = 2000;

    /**
     * 自动拍照频率
     */
    static final long cameraAutoTakeInterval = 50;

    /**
     * 创建一个定时对焦的timer任务
     * @param runnable 对焦代码
     * @return Timer Timer对象，用来终止自动对焦
     */
    public static Timer createAutoFocusTimerTask(final Runnable runnable) {
        if (timerFocus != null) {
            return timerFocus;
        }
        timerFocus = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        timerFocus.scheduleAtFixedRate(task, 0, cameraFocusInterval);
        return timerFocus;
    }

    /**
     * 创建一个定时相机获取任务
     * @param runnable 对焦代码
     * @return Timer Timer对象，用来终止自动对焦
     */
    public static Timer createAutoTakeTimerTask(final Runnable runnable) {
        if (timerAutoTake != null) {
            return timerAutoTake;
        }
        timerAutoTake = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        timerAutoTake.scheduleAtFixedRate(task, 0, cameraAutoTakeInterval);
        return timerAutoTake;
    }

    /**
     * 终止自动对焦任务，实际调用了cancel方法并且清空对象
     * 但是无法终止执行中的任务，需额外处理
     *
     */
    public static void cancelAutoFocusTimer() {
        if (timerFocus != null) {
            timerFocus.cancel();
            timerFocus = null;
        }
    }

    /**
     * 终止自动拍照，实际调用了cancel方法并且清空对象
     * 但是无法终止执行中的任务，需额外处理
     *
     */
    public static void cancelAutoTakeTimer() {
        if (timerAutoTake != null) {
            timerAutoTake.cancel();
            timerAutoTake = null;
        }
    }
}

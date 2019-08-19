package com.baseeasy.commonlibrary.http.uploadfile;

/**
 * 作者：WangZhiQiang
 * 时间：2019/8/16
 * 邮箱：sos181@163.com
 * 描述：
 */
public class UpLoadFileProgress {
    int index=0;
    int maxIndex=0;
    int progress=0;
    boolean done=false;
    public UpLoadFileProgress(int index, int maxIndex, int progress,boolean done) {
        this.index = index;
        this.maxIndex = maxIndex;
        this.progress = progress;
        this.done=done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getIndex() {
        return index;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public int getProgress() {
        return progress;
    }
}

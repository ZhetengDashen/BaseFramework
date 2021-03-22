package com.baseeasy.commonlibrary.http.uploadfile;

import android.os.Handler;
import android.os.Looper;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by WangZhiQiang on 2016/8/25.
 * Detail 重写request 用于监听读取进度
 */
public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private UploadBodyListener mProgressListener;
    private UploadBodyListeners mProgressListeners;
    private BufferedSink bufferedSink;
    int  index=0;
    int  maxIndex=0;
    Handler mainHandler = new Handler(Looper.getMainLooper());
    public UploadFileRequestBody(File file, UploadBodyListener progressListener) {
        this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        this.mProgressListener = progressListener;
    }
    public UploadFileRequestBody(int index, int maxIndex, File file, UploadBodyListeners progressListeners) {
        this.index=index;
        this.maxIndex=maxIndex;
        this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        this.mProgressListeners = progressListeners;
    }
    public UploadFileRequestBody(RequestBody requestBody, UploadBodyListener progressListener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = progressListener;
    }
    public UploadFileRequestBody(int  index,int maxIndex, RequestBody requestBody, UploadBodyListeners progressListeners) {
        this.index=index;
        this.maxIndex=maxIndex;
        this.mRequestBody = requestBody;
        this.mProgressListeners = progressListeners;
    }
    //返回了requestBody的类型，想什么form-data/MP3/MP4/png等等等格式
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    //返回了本RequestBody的长度，也就是上传的totalLength
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (sink instanceof Buffer) {
            // Log Interceptor
            mRequestBody.writeTo(sink);
            return;
        }
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();


    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调上传接口
                if(mProgressListener!=null){
//                    LogUtils.e((int)(bytesWritten * 100 / contentLength())+"%");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mProgressListener.onProgress( (int)(bytesWritten * 100 / contentLength()),bytesWritten == contentLength);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }
                if(mProgressListeners!=null){
//                    LogUtils.e(index+"/"+maxIndex+1+":"+(int)(bytesWritten * 100 / contentLength())+"%");
                   mainHandler.post(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               mProgressListeners.onProgresss(index+1,maxIndex+1, (int)(bytesWritten * 100 / contentLength()),bytesWritten == contentLength&&index==maxIndex);
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   });

                  }

            }
        };
    }

    // body内部回调
    public interface UploadBodyListener{
        void onProgress(int percentage, boolean done);
    }
    // body内部回调
    public interface UploadBodyListeners{
        /*
        *   index 当前传到第几个文件
        *   maxIndex 总文件个数
        *   percentage 当前文件进度
        *  done  总进度是否完成
        *
        * */
        void onProgresss(int index, int maxIndex, int percentage, boolean done);

    }
}
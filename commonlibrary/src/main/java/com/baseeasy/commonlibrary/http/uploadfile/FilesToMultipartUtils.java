package com.baseeasy.commonlibrary.http.uploadfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by WangZhiQiang on 2016/8/25
 */
public class FilesToMultipartUtils {

    public static MultipartBody.Part fileToMultipartBodyPart(File file, String key, UploadFileRequestBody.UploadBodyListener uploadBodyListener) {


        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file,uploadBodyListener);

        MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), uploadFileRequestBody);
        return part;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files, String key, UploadFileRequestBody.UploadBodyListeners uploadBodyListeners) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());

        for (int i = 0; i < files.size(); i++) {
            UploadFileRequestBody requestBody = new UploadFileRequestBody(i, files.size()-1,files.get(i),uploadBodyListeners);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, files.get(i).getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static MultipartBody filesToMultipartBody(List<File> files, String key ) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(key, file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return  builder.build();
    }
}

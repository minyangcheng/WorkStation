package com.min.know.logger;

import okhttp3.*;

import java.io.File;

public class Scan {

    private static final String url = "http://10.10.13.23:9099/uploadLog";

    public static void main(String args[]) throws Exception {
        new UploadLogThread().start();
    }

    public static class UploadLogThread extends Thread {

        @Override
        public void run() {
            try {
                File[] files = FileLogger.scanCompleteFile();
                if (files != null && files.length > 0) {
                    File file = null;
                    for (int i = 0; i < files.length; i++) {
                        file = files[i];
                        System.out.println("start upload file " + file.getName());
                        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("file", file.getName(), fileBody)
                                .build();

                        Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody)
                                .build();
                        Response response = OkHttpUtil.getOkHttpClient().newCall(request).execute();
                        System.out.println("end upload file " + file.getName() + " responseCode=" + response.code());
                        if (response.code() == 200) {
                            String responseBody = response.body().string();
                            if ("success".equals(responseBody)) {
                                file.delete();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

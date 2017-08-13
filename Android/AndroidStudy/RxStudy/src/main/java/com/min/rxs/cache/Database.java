// (c)2016 Flipboard Inc, All Rights Reserved.

package com.min.rxs.cache;

public class Database {
    private static String DATA_FILE_NAME = "data.db";

    private static Database INSTANCE;


    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public String readItems() {
        // Hard code adding some delay, to distinguish reading from memory and reading disk clearly
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
//            Reader reader = new FileReader(dataFile);
//            return gson.fromJson(reader, new TypeToken<String>(){}.getType());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeItems(String items) {
    }

    public void delete() {
    }
}

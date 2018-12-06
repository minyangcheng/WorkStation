package com.min.know.clazz;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String rootDir;

    public MyClassLoader(String rootDir) {
        super();
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        // 读取类文件的字节
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }

    public static void main(String args[]) {
        try {
            MyClassLoader classLoader = new MyClassLoader("/home/minych/project/java/Sample/out/production/classes");
            Class clazz = classLoader.findClass("com.min.know.clazz.Counter");
            Object object=clazz.getDeclaredConstructor().newInstance();
            ICounter counter= (ICounter) object;
            System.out.println(counter.toString());
            counter.add();
            counter.add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

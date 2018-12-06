package com.min.ndk;


public class MainActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public static void main(String args[]) {
	 System.out.println(new MainActivity().stringFromJNI());
    }

    public native String stringFromJNI();
}

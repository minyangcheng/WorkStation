package com.min.know.offer;

public class StringTest {

    public static void main(String[] args) {
        System.out.println(findFirstLetterOnce("abaccdeff"));
        System.out.println(1&2);
    }

    public static char findFirstLetterOnce(String str) {
        char[] array = str.toCharArray();
        int size = 256;
        int[] hastTable = new int[size];
        for (int i = 0; i < size; i++) {
            hastTable[i] = 0;
        }
        for (int i = 0; i < array.length; i++) {
            hastTable[array[i]]++;
        }

        for (int i = 0; i < array.length; i++) {
            if (hastTable[array[i]] == 1) {
                return array[i];
            }
        }

        return '\0';
    }

}

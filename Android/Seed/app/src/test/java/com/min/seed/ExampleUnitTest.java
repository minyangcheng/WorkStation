package com.min.seed;

import com.blankj.utilcode.util.EncryptUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void additionIsCorrect() throws Exception {
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testMd5(){
        String input="adfsasdfasd12";
        String expect="70f142f91b36341069808dd8bd6fb85e";
        Assert.assertEquals(expect,EncryptUtils.encryptMD5ToString(input));
    }

}
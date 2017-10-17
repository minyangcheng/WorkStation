package com.cheguo.pos;

import com.min.common.util.EncryptUtils;
import com.cheguo.pos.util.FormatUtil;
import com.min.core.util.GsonUtil;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

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
    public void testMd5() {
        String input = "adfsasdfasd12";
        String expect = "70f142f91b36341069808dd8bd6fb85e";
        Assert.assertEquals(expect, EncryptUtils.encryptMD5ToString(input));
    }

    @Test
    public void testRmb() {
        Double d = 0.00;
        String str = FormatUtil.formatRmb("444443.42", 2);
        System.out.println(str);
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testSplit() {
        String s = "";
        String[] arr = s.split("\\.");
        System.out.println(GsonUtil.toJson(arr));
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testReplace() {
        String s = "0.";
        System.out.println(s.replaceAll("\\.", ""));
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testTime() {
        String time = "2016-09-12 12:12:12";
        String timeStr = FormatUtil.getBeautifulYearMonth(time);
        System.out.print(timeStr);
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testRMB() {
        double d=100.02;
        System.out.println(String.valueOf(d));
        BigDecimal bg=new BigDecimal(String.valueOf(d));
        System.out.println(bg.toPlainString());
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void testTransTime() {
        System.out.println(FormatUtil.getTransTime("0907","114804"));
        System.out.println(FormatUtil.getTransTime(null,null));
        Assert.assertEquals(4, 2 + 2);
    }

}
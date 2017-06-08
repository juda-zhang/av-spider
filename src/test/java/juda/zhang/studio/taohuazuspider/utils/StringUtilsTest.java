package juda.zhang.studio.taohuazuspider.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Juda.Zhang on 2017/6/7.
 */
public class StringUtilsTest {
    @Test
    public void testTrimAndUppder() {
        String s = StringUtils.trimAndUpper(" aAA ");
        Assert.assertEquals(s, "AAA");
    }
}

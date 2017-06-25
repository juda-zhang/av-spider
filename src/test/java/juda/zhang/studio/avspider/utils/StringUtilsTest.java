package juda.zhang.studio.avspider.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Juda.Zhang on 2017/6/7.
 */
public class StringUtilsTest {
    @Test
    public void testTrimAndUppder() {
        String s = StringUtils.trimAndUpper(" aAA ");
        Assert.assertEquals("AAA", s);
    }

    @Test
    public void test() {
        String s = "[KAWD-8/23]\\新人！ka?waii*専属デビュ→ 童顔アンバ|ランスGカップ！1:8?<歳現>役グラドル 一色さゆりAVデビュー";
        Assert.assertEquals("[KAWD-823]新人！kawaii専属デビュ→ 童顔アンバランスGカップ！18歳現役グラドル 一色さゆりAVデビュー"
                , StringUtils.filterSpecialDirString(s));
    }

}

package juda.zhang.studio.taohuazuspider.utils;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by Juda.Zhang on 2017/6/7.
 */
public class HttpUtilsTest {

    @Test
    public void testDownloadFile() throws IOException {
        String url = "https://www.baidu.com/img/baidu_jgylogo3.gif";
        String filePath = "C:/UnitTest";
        String fileName = "baidu.gif";
        HttpUtils.downloadFile(url, filePath, fileName);
    }
}

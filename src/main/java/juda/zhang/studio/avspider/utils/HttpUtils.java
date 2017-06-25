package juda.zhang.studio.avspider.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通用的http处理类
 * Created by Juda.Zhang on 2017/6/8.
 */
public class HttpUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 下载文件，重试指定次数
     *
     * @param url        完整url
     * @param filePath   下载到的目的路径
     * @param fileName   下载后的文件名
     * @param overwrite  是否覆盖
     * @param retryTimes 重试的次数
     */
    public static void downloadFile(String url, String filePath, String fileName, boolean overwrite, int retryTimes) {
        if (retryTimes <= 0) {
            return;
        }

        try {
            downloadFile(url, filePath, fileName, overwrite);
        } catch (IOException e) {
            //重试次数减1
            retryTimes -= 1;
            if (retryTimes <= 0) {
                //重试次数用完
                LOGGER.error("下载重试次数使用完毕。仍然下载失败！url=" + url + ",fileName=" + fileName, e);
            } else {
                //重试次数未用完
                LOGGER.debug("下载异常！url=" + url + ",fileName=" + fileName, e);
                LOGGER.info("开始重试下载文件。url={},fileName={},retryTimes={}", url, fileName, retryTimes);
                downloadFile(url, filePath, fileName, overwrite, retryTimes);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param url       完整url
     * @param filePath  下载到的目的路径
     * @param fileName  下载后的文件名
     * @param overwrite 是否覆盖
     * @throws IOException
     */
    public static void downloadFile(String url, String filePath, String fileName, boolean overwrite)
            throws IOException {

        if (StringUtils.isBlank(url) || StringUtils.isBlank(filePath)) {
            LOGGER.error("文件路径为空!url={},fileName={}", url, fileName);
            throw new RuntimeException("参数为空!");
        }

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileFullName = filePath + "/" + fileName;
        File file = new File(fileFullName);
        if (!overwrite && file.exists()) {
            LOGGER.info("文件已存在!fileFullName={}", fileFullName);
            return;
        }

        // 生成一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        // Create a custom response handler
        ResponseHandler<Boolean> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                try {
                    FileOutputStream flout = new FileOutputStream(file);
                    int l;
                    byte[] tmp = new byte[1024];
                    while ((l = in.read(tmp)) != -1) {
                        flout.write(tmp, 0, l);
                    }
                    flout.flush();
                    flout.close();
                } finally {
                    in.close();
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
            return true;
        };
        httpclient.execute(httpget, responseHandler);
        httpclient.close();
    }


    /**
     * 下载文件。如果文件已存在则覆盖
     *
     * @param url      完整url
     * @param filePath 下载到的目的路径
     * @param fileName 下载后的文件名
     * @throws IOException
     */
    public static void downloadFile(String url, String filePath, String fileName)
            throws IOException {
        downloadFile(url, filePath, fileName, true);
    }
}

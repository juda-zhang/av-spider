package juda.zhang.studio.taohuazuspider.utils;

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
     * 下载文件
     *
     * @param url      完整url
     * @param filePath 下载到的目的路径
     * @param fileName 下载后的文件名
     * @throws IOException
     */
    public static void downloadFile(String url, String filePath, String fileName)
            throws IOException {

        if (StringUtils.isBlank(url) || StringUtils.isBlank(filePath)) {
            LOGGER.error("文件路径为空!url={},filePath={},fileName={}", url, fileName, fileName);
            throw new RuntimeException("参数为空!");
        }

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
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

                String fileFullName = filePath + "/" + fileName;
                File file = new File(fileFullName);
                try {
                    FileOutputStream fout = new FileOutputStream(file);
                    int l;
                    byte[] tmp = new byte[1024];
                    while ((l = in.read(tmp)) != -1) {
                        fout.write(tmp, 0, l);
                    }
                    fout.flush();
                    fout.close();
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

}

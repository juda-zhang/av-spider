package juda.zhang.studio.taohuazuspider.spider.pipline;

import juda.zhang.studio.taohuazuspider.core.model.FilmDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 保存文件的管道
 * Created by 晨辉 on 2017/6/5.
 */
@Service("thzFileSavePipline")
public class ThzFileSavePipline implements Pipeline {

    public static final String DEST_DIR = "C:/TaoHuaZu";
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzFileSavePipline.class);

    //    Accept:image/webp,image/*,*/*;q=0.8
//    Accept-Encoding:gzip, deflate, sdch
//    Accept-Language:zh-CN,zh;q=0.8
//    Cache-Control:max-age=0
//    Connection:keep-alive
//    Host:pic.thzhd.wang
//    If-Modified-Since:Mon, 05 Jun 2017 07:22:54 GMT
//    If-None-Match:"135588cccddd21:0"
//    Referer:http://taohuabbs.cc/thread-1064225-1-2.html
//    User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36
    public static boolean downLoadFiles(List<String> fileUrls, String dir, String fileName, String format) throws Exception {
        boolean isSuccess = true;
        //创建根目录
        File fileDir = new File(dir);
        fileDir.mkdirs();
        // 创建子目录
        //fileDir = new File(dir);
        //fileDir.mkdirs();

        int i = 1;
        // 循环下载图片
        for (String fileUrl : fileUrls) {
            URL url = new URL(fileUrl);
            // 打开网络输入流
            DataInputStream dis = new DataInputStream(url.openStream());
            String seq = fileUrls.size() == 1 ? "" : "_" + i;
            String newImageName = dir + "/" + fileName + seq + "." + format;
            // 建立一个新的文件
            FileOutputStream fos = new FileOutputStream(new File(newImageName));
            byte[] buffer = new byte[1024];
            int length;
            LOGGER.info("正在下载第 " + i + "个文件。请稍候。。。");
            // 开始填充数据
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
            LOGGER.info("第 " + i + "个文件下载完毕.");
            i++;
        }
        return isSuccess;
    }

    public void process(ResultItems resultItems, Task task) throws RuntimeException {
        ProductDO productDO = resultItems.get("productDO");
        if (productDO != null) {
            String fullTitle = productDO.getTitle();
            String code = productDO.getCode();
            String coverImgUrl = productDO.getCoverImgUrl();

            String dir = DEST_DIR + "/" + fullTitle;
            try {
                downLoadFiles(productDO.getAllImgUrls(), dir, fullTitle, "jpg");
            } catch (Exception e) {
                LOGGER.info("保存预览文件出错!code=" + code + ",fullTitle=" + fullTitle, e);
                throw new RuntimeException(e);
            }

            FilmDO filmDO = resultItems.get("filmDO");
            String torrentUrl = filmDO.getFileUrl();
            if (filmDO != null) {
                try {
                    List<String> torrentUrls = new ArrayList<>();
                    torrentUrls.add(torrentUrl);
                    downLoadFiles(torrentUrls, dir, code, "torrent");
                } catch (Exception e) {
                    LOGGER.info("保存种子文件出错!code=" + code + ",fullTitle=" + fullTitle, e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

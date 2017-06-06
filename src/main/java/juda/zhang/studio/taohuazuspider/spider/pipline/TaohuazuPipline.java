package juda.zhang.studio.taohuazuspider.spider.pipline;

import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

/**
 * 保存文件的管道
 * Created by 晨辉 on 2017/6/5.
 */
public class TaohuazuPipline implements Pipeline {

    public static final String DEST_DIR = "C:/TaoHuaZu";

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
    public static boolean downLoadPics(List<String> imgUrls, String filePath, String title) throws Exception {
        boolean isSuccess = true;
        //创建根目录
        File fileDir = new File(filePath);
        fileDir.mkdirs();
        // 创建子目录
        String dir = filePath + "/" + title;
        fileDir = new File(dir);
        fileDir.mkdirs();

        int i = 1;
        // 循环下载图片
        for (String imgUrl : imgUrls) {
            URL url = new URL(imgUrl);
            // 打开网络输入流
            DataInputStream dis = new DataInputStream(url.openStream());
            int x = (int) (Math.random() * 1000000);
            String newImageName = dir + "/" + x + "pic" + i + ".jpg";
            // 建立一个新的文件
            FileOutputStream fos = new FileOutputStream(new File(newImageName));
            byte[] buffer = new byte[1024];
            int length;
            System.out.println("正在下载......第 " + i + "张图片......请稍后");
            // 开始填充数据
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
            System.out.println("第 " + i + "张图片下载完毕......");
            i++;
        }
        return isSuccess;
    }

    public void process(ResultItems resultItems, Task task) {
        ProductDO productDO = resultItems.get("productDO");
        if (productDO != null) {
            String fullTitle = productDO.getTitle();
            String code = productDO.getCode();
            String coverImgUrl = productDO.getCoverImgUrl();
            List<String> previewUrls = productDO.getPreviewUrls();
            //previewUrls.add(coverImgUrl);
            try {
                downLoadPics(previewUrls, DEST_DIR, fullTitle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

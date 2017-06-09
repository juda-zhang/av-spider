package juda.zhang.studio.taohuazuspider.spider.pipline;

import juda.zhang.studio.taohuazuspider.core.model.FilmDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductImgDO;
import juda.zhang.studio.taohuazuspider.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 保存文件的管道
 * Created by Juda.Zhang on 2017/6/5.
 */
@Service("thzFileSavePipline")
public class ThzFileSavePipeline implements Pipeline {
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzFileSavePipeline.class);
    @Value("${file.save.dir}")
    private String DEST_DIR;

    public void process(ResultItems resultItems, Task task) {
        ProductDO productDO = resultItems.get("productDO");
        List<ProductImgDO> productImgDOList = resultItems.get("productImgDOList");
        if (productDO != null) {
            String fullTitle = productDO.getFullTitle();
            String code = productDO.getCode();
            String dir = DEST_DIR + "/" + fullTitle;

            if (productImgDOList != null || productImgDOList.size() != 0) {
                int i = 1;
                for (ProductImgDO productImgDO : productImgDOList) {
                    String imgUrl = productImgDO.getUrl();
                    String fileName = null;
                    try {
                        if (productImgDO.getType() == 0) {
                            //下载封面
                            fileName = code + "_cover.jpg";
                            Long startTime = System.currentTimeMillis();
                            HttpUtils.downloadFile(imgUrl, dir, fileName);
                            Long endTime = System.currentTimeMillis();
                            LOGGER.info("下载封面图片完毕。fileName={},time={}s", fileName, (endTime - startTime) / 1000);
                        } else if (productImgDO.getType() == 2) {
                            fileName = code + "_" + i + ".jpg";
                            Long startTime = System.currentTimeMillis();
                            HttpUtils.downloadFile(imgUrl, dir, fileName);
                            Long endTime = System.currentTimeMillis();
                            LOGGER.info("下载预览图片完毕。fileName={},time={}s", fileName, (endTime - startTime) / 1000);
                            i++;
                        }
                    } catch (Exception e) {
                        LOGGER.info("保存图片出错!code=" + code + ",fileName=" + fileName, e);
                    }
                }
            }

            {
                //下载种子
                FilmDO filmDO = resultItems.get("filmDO");
                if (filmDO != null) {
                    try {
                        String torrentUrl = filmDO.getFileUrl();
                        String fileName = code + ".torrent";
                        Long startTime = System.currentTimeMillis();
                        HttpUtils.downloadFile(torrentUrl, dir, fileName);
                        Long endTime = System.currentTimeMillis();
                        LOGGER.info("下载种子文件完毕。fileName={},time={}s", fileName, (endTime - startTime) / 1000);
                    } catch (Exception e) {
                        LOGGER.info("保存种子文件出错!code=" + code + ",fullTitle=" + fullTitle, e);
                    }
                }
            }
        }
    }

}

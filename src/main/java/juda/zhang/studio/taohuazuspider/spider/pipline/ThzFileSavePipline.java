package juda.zhang.studio.taohuazuspider.spider.pipline;

import juda.zhang.studio.taohuazuspider.core.model.FilmDO;
import juda.zhang.studio.taohuazuspider.core.model.ProductDO;
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
public class ThzFileSavePipline implements Pipeline {
    private final static Logger LOGGER = LoggerFactory.getLogger(ThzFileSavePipline.class);
    @Value("${file.save.dir}")
    private String DEST_DIR;

    public void process(ResultItems resultItems, Task task) {
        ProductDO productDO = resultItems.get("productDO");
        if (productDO != null) {
            String fullTitle = productDO.getFullTitle();
            String code = productDO.getCode();
            String dir = DEST_DIR + "/" + fullTitle;

            {
                //下载封面
                try {
                    String coverImgUrl = productDO.getCoverImgUrl();
                    String fileName = code + "_cover.jpg";
                    HttpUtils.downloadFile(coverImgUrl, dir, fileName);
                } catch (Exception e) {
                    LOGGER.info("保存封面预览图片出错!code=" + code + ",fullTitle=" + fullTitle, e);
                }
            }

            {
                //下载预览
                List<String> previewImgUrls = productDO.getPreviewUrls();
                if (previewImgUrls != null) {
                    for (int i = 0; i < previewImgUrls.size(); i++) {
                        try {
                            String previewImgUrl = previewImgUrls.get(i);
                            String fileName = code + "_" + (i + 1) + ".jpg";
                            HttpUtils.downloadFile(previewImgUrl, dir, fileName);
                        } catch (Exception e) {
                            LOGGER.info("保存内容预览图片出错!code=" + code + ",fullTitle=" + fullTitle, e);
                        }
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
                        HttpUtils.downloadFile(torrentUrl, dir, fileName);
                    } catch (Exception e) {
                        LOGGER.info("保存种子文件出错!code=" + code + ",fullTitle=" + fullTitle, e);
                    }
                }
            }
        }
    }

}

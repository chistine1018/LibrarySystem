package com.anson.school_books_system.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.anson.school_books_system.common.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author <a href=""></a>
 * @package com.anson.school_books_system.utils
 * @createTime
 * @description
 */
@Slf4j
public class ImgFileUtil {

    private static Integer PORT;

    private static Set<String> ALLOW_IMG_TYPE = new HashSet<>();

    static {
        ALLOW_IMG_TYPE.add("jpg");
        ALLOW_IMG_TYPE.add("png");
        ALLOW_IMG_TYPE.add("jpeg");
    }

    public static void setPORT(Integer port) {
        // 服務器啟動的時候，會設置
        PORT = port;
    }

    public static String uploadImg(MultipartFile imgFile) {
        // 校驗文件格式
        String uploadImgType = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1);
        if (StringUtils.isBlank(uploadImgType)) {
            throw new SysException("上傳文件格式錯誤！");
        }
        if (!ALLOW_IMG_TYPE.contains(uploadImgType.toLowerCase(Locale.ROOT))) {
            throw new SysException("圖片只支持：jpg、png、jpeg 格式！");
        }

        // 項目路徑
        File imgFilePath = FileUtil.file(
                System.getProperty("user.dir") + File.separator +
                        "src" + File.separator +
                        "main" + File.separator +
                        "resources" + File.separator +
                        "static" + File.separator +
                        "img");

        if (Boolean.FALSE.equals(imgFilePath.exists())) {
            // 不存在，創建
            imgFilePath.mkdirs();
        }

        // 將圖片重新放到如下的路徑
        String newImgFileName = RandomUtil.randomString(32) + "." + uploadImgType;
        File newImgFile = new File(imgFilePath.getPath() + File.separator + newImgFileName);

        try {
            // 將圖片上傳到新創建的文件中，完成圖片上傳
            imgFile.transferTo(newImgFile);
        } catch (IOException e) {
            log.error("上傳文件失敗！", e);
            throw new SysException("上傳文件失敗！");
        }

        // 圖片資源路徑生成

        // 1、本地文件路徑訪問
        // return newImgFile.getPath();
        // 2、通過服務器訪問靜態資源
        return "http://localhost:" + PORT + "/static/img/" + newImgFileName;
    }
}

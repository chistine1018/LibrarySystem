package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.utils.ImgFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href=""></a>
 * @folder 文件上傳
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    /**
     * 圖片上傳
     *
     * @param file 圖片文件
     * @return 圖片地址
     */
    @PostMapping("/imgUpload")
    public String imgUpload(MultipartFile file) {
        return ImgFileUtil.uploadImg(file);
    }

}

package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.vo.HomeVO;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.service.HomeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @folder 首页
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    /**
     * 獲取首頁的數據
     *
     * @return
     */
    @GetMapping("")
    public HomeVO home() {
        return homeService.home();
    }
}

package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.query.PageQuery;
import com.anson.school_books_system.api.vo.BookCategoryVO;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.service.BookCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 
 * @folder 圖書分類管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/bookCategory")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    /**
     * 分頁查詢
     *
     * @param query 分頁對象
     * @param categoryName 分類名稱
     * @return
     */
    @GetMapping("/page")
    public IPage<BookCategoryVO> page(PageQuery query, String categoryName) {
        return bookCategoryService.page(query, categoryName);
    }

    /**
     * 保存或修改類別
     *
     * @param id           保存時候不傳，修改時必傳
     * @param categoryName 類別名稱
     */
    @GetMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestParam(name = "id", required = false) Long id,
                             @RequestParam(name = "categoryName", required = true) String categoryName) {
        bookCategoryService.saveOrUpdate(id, categoryName);
    }
}

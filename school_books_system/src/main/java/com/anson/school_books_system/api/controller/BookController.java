package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.form.BookSaveOrUpdateForm;
import com.anson.school_books_system.api.query.BookQuery;
import com.anson.school_books_system.api.vo.BookVO;
import com.anson.school_books_system.common.apirule.ApiRole;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.BookService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 
 * @folder 圖書管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    /**
     * 分頁查詢
     *
     * @param query
     * @return
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_USER})
    @GetMapping("/page")
    public IPage<BookVO> page(BookQuery query) {
        return bookService.page(query);
    }

    /**
     * 根據id獲取圖書信息
     *
     * @param id 圖書id
     * @return
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_USER})
    @GetMapping("/one")
    public BookVO one(@RequestParam(name = "id", required = true) Long id) {
        return bookService.one(id);
    }

    /**
     * 保存或修改圖書
     *
     * @param form
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@Validated @RequestBody BookSaveOrUpdateForm form) {
        bookService.saveOrUpdate(form);
    }
}

package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.query.BookBorrowInfoQuery;
import com.anson.school_books_system.api.vo.BookBorrowInfoVO;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.BookBorrowInfoService;
import com.anson.school_books_system.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @folder 圖書借閱管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/bookBorrowInfo")
public class BookBorrowInfoController {
    private final BookBorrowInfoService bookBorrowInfoService;

    /**
     * 分頁查詢
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public IPage<BookBorrowInfoVO> page(BookBorrowInfoQuery query) {
        if (SecurityUtil.getRole().equals(RoleEnum.ROLE_USER)) {
            // 當前訪問角色是 用戶，那麼只能查詢當前用戶的歸還訊息
            query.setUserId(SecurityUtil.getUserId());
        }
        return bookBorrowInfoService.page(query);
    }

    /**
     * 借閱圖書
     *
     * @param bookId 圖書id
     * @param borrowTotal  借閱數量
     */
    @GetMapping("/bookBorrow")
    public void bookBorrow(
            @RequestParam(name = "bookId", required = true) Long bookId,
            @RequestParam(name = "borrowTotal", required = true) Integer borrowTotal) {
        bookBorrowInfoService.bookBorrow(bookId, borrowTotal);
    }
}

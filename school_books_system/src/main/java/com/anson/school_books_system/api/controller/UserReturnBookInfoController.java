package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.query.UserReturnBookQuery;
import com.anson.school_books_system.api.vo.UserReturnBookInfoVO;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.UserReturnBookInfoService;
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
 * @folder 圖書歸還管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/userReturnBookInfo")
public class UserReturnBookInfoController {
    private final UserReturnBookInfoService userReturnBookInfoService;

    /**
     * 分頁查詢用戶歸還圖書信息
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public IPage<UserReturnBookInfoVO> page(UserReturnBookQuery query) {
        if (SecurityUtil.getRole().equals(RoleEnum.ROLE_USER)) {
            // 當前訪問角色是 用戶，那麼只能查詢當前用戶的歸還信息
            query.setUserId(SecurityUtil.getUserId());
        }
        return userReturnBookInfoService.page(query);
    }

    /**
     * 用戶歸還圖書
     *
     * @param bookBorrowInfoId 圖書借閱信息id
     * @param count            歸還數量
     */
    @GetMapping("/userReturnBook")
    public void userReturnBook(
            @RequestParam(name = "bookBorrowInfoId", required = true) Long bookBorrowInfoId,
            @RequestParam(name = "count", required = true) Integer count) {
        userReturnBookInfoService.userReturnBook(bookBorrowInfoId, count);
    }
}

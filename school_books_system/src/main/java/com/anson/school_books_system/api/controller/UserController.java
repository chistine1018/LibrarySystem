package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.form.UserRegisterForm;
import com.anson.school_books_system.api.query.UserQuery;
import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.common.apirule.ApiRole;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href=""></a>
 * @folder 用戶管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 用戶註冊
     *
     * @param form
     */
    @PostMapping("/register")
    public void register(@Validated @RequestBody UserRegisterForm form) {
        userService.register(form);
    }
    /**
     * 用戶修改
     *
     * @param form
     */
    @ApiRole(role = {RoleEnum.ROLE_USER})
    @PostMapping("/update")
    public void update(@Validated @RequestBody UserRegisterForm form) {
        userService.update(form);
    }

    /**
     * 分業查詢
     *
     * @param query
     * @return
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @GetMapping("/page")
    public IPage<UserVO> page(UserQuery query) {
        return userService.page(query);
    }

    /**
     * 刪除用戶
     * @param id
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @GetMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        userService.delete(id);
    }
}

package com.anson.school_books_system.api.controller;


import com.anson.school_books_system.api.form.AdminSaveOrUpdateForm;
import com.anson.school_books_system.api.query.AdminQuery;
import com.anson.school_books_system.api.vo.AdminVO;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.service.AdminService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href=""></a>
 * @folder 超級管理員接口
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {

    private final AdminService adminService;

    /**
     * 查詢所有普通管理員帳號列表
     * 超級管理員，不查
     *
     * @param query 查詢對象
     * @return
     */
    @GetMapping("/page")
    public IPage<AdminVO> page(AdminQuery query) {
        return adminService.page(query);
    }

    /**
     * 保存或更新管理員帳號
     *
     * @param form 表單
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public AdminVO saveOrUpdate(@Validated @RequestBody AdminSaveOrUpdateForm form) {
        return adminService.saveOrUpdate(form);
    }

    /**
     * 移除普通管理員帳號
     *
     * @param id 管理員id
     */
    @GetMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        adminService.removeById(id);
    }
}

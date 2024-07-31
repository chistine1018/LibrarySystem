package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.form.AnnouncementSaveForm;
import com.anson.school_books_system.api.query.AnnouncementQuery;
import com.anson.school_books_system.api.vo.AnnouncementVO;
import com.anson.school_books_system.common.apirule.ApiRole;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.AnnouncementService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @folder 公告管理
 * @package cn.j3code.booksys.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * 公告列表分頁查詢
     *
     * @param query 分頁對象
     * @return
     */
    @GetMapping("/page")
    @ApiRole(role = {RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_USER})
    public IPage<AnnouncementVO> page(AnnouncementQuery query) {
        return announcementService.page(query);
    }

    /**
     * 公告添加
     *
     * @param form
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @PostMapping("/save")
    public void save(@Validated @RequestBody AnnouncementSaveForm form) {
        announcementService.save(form);
    }

    /**
     * 公告刪除
     *
     * @param id 公告id
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @GetMapping("/delete")
    public void delete(@RequestParam(name = "id", required = true) Long id) {
        announcementService.removeById(id);
    }
}

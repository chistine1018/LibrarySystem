package com.anson.school_books_system.api.controller;



import com.anson.school_books_system.api.form.CarouselMapSaveForm;
import com.anson.school_books_system.api.query.PageQuery;
import com.anson.school_books_system.api.vo.CarouselMapVO;
import com.anson.school_books_system.common.apirule.ApiRole;
import com.anson.school_books_system.common.result.ResponseResult;
import com.anson.school_books_system.enums.RoleEnum;
import com.anson.school_books_system.service.CarouselMapService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @folder 輪播圖管理
 * @package com.anson.school_books_system.api.controller
 * @createTime
 * @description
 */
@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/carouselMap")
public class CarouselMapController {
    private final CarouselMapService carouselMapService;

    /**
     * 分頁查詢
     *
     * @param query 分頁對象
     * @return
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @GetMapping("/page")
    public IPage<CarouselMapVO> page(PageQuery query) {
        return carouselMapService.page(query);
    }

    /**
     * 輪播圖添加
     *
     * @param form
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @PostMapping("/save")
    public void save(@Validated @RequestBody CarouselMapSaveForm form) {
        carouselMapService.save(form);
    }

    /**
     * 輪播圖刪除
     *
     * @param id 輪播圖id
     */
    @ApiRole(role = {RoleEnum.ROLE_ADMIN})
    @GetMapping("/delete")
    public void delete(@RequestParam(name = "id", required = true) Long id) {
        carouselMapService.removeById(id);
    }
}

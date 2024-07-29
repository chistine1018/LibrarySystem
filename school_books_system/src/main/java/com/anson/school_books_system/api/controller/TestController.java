package com.anson.school_books_system.api.controller;

import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.common.result.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ResponseResult
@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 測試返回void
     *
     * @return
     */
    @GetMapping("/voidTest")
    public void voidTest() {
        System.out.println("voidTest");
    }

    /**
     * 測試返回string
     *
     * @return
     */
    @GetMapping("/stringTest")
    public String stringTest() {
        System.out.println("stringTest");
        return "stringTest";
    }

    /**
     * 測試返回string-null
     *
     * @return
     */
    @GetMapping("/stringNullTest")
    public String stringNullTest() {
        System.out.println("stringTest");
        return null;
    }

    /**
     * 測試返回UserVO
     *
     * @return
     */
    @GetMapping("/userVOTest")
    public UserVO userVOTest() {
        System.out.println("userVOTest");
        UserVO userVO = new UserVO();
        userVO.setName("userVOTest");
        return userVO;
    }

    /**
     * 測試返回UserVO-null
     *
     * @return
     */
    @GetMapping("/userVONullTest")
    public UserVO userVONullTest() {
        System.out.println("userVOTest");
        UserVO userVO = new UserVO();
        userVO.setName("userVOTest");
        return null;
    }

    /**
     * 測試返回異常
     *
     * @return
     */
    @GetMapping("/exceptionTest")
    public UserVO exceptionTest() {
        System.out.println("exceptionTest");
        UserVO userVO = new UserVO();
        userVO.setName("userVOTest");
        throw new SysException("出錯了！");
//        return userVO;
    }
}

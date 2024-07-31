package com.anson.school_books_system.service.impl;

import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.form.UserRegisterForm;
import com.anson.school_books_system.api.query.UserQuery;
import com.anson.school_books_system.api.vo.UserVO;
import com.anson.school_books_system.common.exception.AuthException;
import com.anson.school_books_system.common.exception.SysException;
import com.anson.school_books_system.converter.UserConverter;
import com.anson.school_books_system.utils.PasswordUtil;
import com.anson.school_books_system.utils.SysUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.User;
import com.anson.school_books_system.service.UserService;
import com.anson.school_books_system.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author user
 * @description 針對表【user】的數據庫操作Service實現
 * @createDate 2024-07-29 20:07:50
 */
@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    UserConverter userConverter;

    @Override
    public UserVO login(AuthForm form) {
        User user = lambdaQuery()
                .eq(User::getStudentNumber, form.getUsername())
//                .eq(User::getPassword, form.getPassword())
                .eq(User::getPassword, PasswordUtil.encryptPassword(form.getPassword()))
                .one();

        if (Objects.isNull(user)) {
            throw new AuthException("帳號不存在或者登陸訊息錯誤");
        }


        return userConverter.converter(user);
    }

    @Override
    public void register(UserRegisterForm form) {
        if (lambdaQuery().eq(User::getStudentNumber, form.getStudentNumber()).count() > 0) {
            throw new SysException("學號已存在！");
        }

        if (StringUtils.isBlank(form.getPassword())) {
            throw new SysException("密碼不能為空！");
        }

        if (Boolean.FALSE.equals(SysUtil.checkPhone(form.getPhone()))) {
            throw new SysException("請輸入正確電話號碼格式！");
        }

        User user = userConverter.converter(form);
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        save(user);
    }

    @Override
    public IPage<UserVO> page(UserQuery query) {
        Page<User> userPage = lambdaQuery()
                .like(StringUtils.isNotBlank(query.getStudentNumber()), User::getStudentNumber, query.getStudentNumber())
                .like(StringUtils.isNotBlank(query.getName()), User::getName, query.getName())
                .like(StringUtils.isNotBlank(query.getCollege()), User::getCollege, query.getCollege())
                .like(StringUtils.isNotBlank(query.getGrade()), User::getGrade, query.getGrade())
                .like(StringUtils.isNotBlank(query.getPhone()), User::getPhone, query.getPhone())
                .like(StringUtils.isNotBlank(query.getDormitory()), User::getDormitory, query.getDormitory())
                .eq(Objects.nonNull(query.getSex()), User::getSex, query.getSex())
                .page(query.getPage());
        return userPage.convert(userConverter::converter);
    }

    @Override
    public void update(UserRegisterForm form) {
        if (Objects.isNull(form.getId())) {
            throw new SysException("用戶ID不能為空！");
        }

        if (lambdaQuery()
                .eq(User::getStudentNumber, form.getStudentNumber())
                .ne(User::getId, form.getId())
                .count() > 0) {
            throw new SysException("學號已存在！");
        }

        if (Boolean.FALSE.equals(SysUtil.checkPhone(form.getPhone()))) {
            throw new SysException("請輸入正確電話號碼格式！");
        }

        User user = userConverter.converter(form);
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        updateById(user);
    }

    @Override
    public void delete(Long id) {
        // 刪除用戶借閱信息
//        bookBorrowInfoService.lambdaUpdate().eq(BookBorrowInfo::getUserId, id).remove();
        // 刪除用戶規還信息
//        userReturnBookInfoService.lambdaUpdate().eq(UserReturnBookInfo::getUserId, id).remove();
        // 刪除用戶信息
        removeById(id);
    }
}





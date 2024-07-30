package com.anson.school_books_system.service;

import com.anson.school_books_system.api.form.AuthForm;
import com.anson.school_books_system.api.vo.AuthVO;

public interface AuthService {
    AuthVO auth(AuthForm form);

    void logout();
}

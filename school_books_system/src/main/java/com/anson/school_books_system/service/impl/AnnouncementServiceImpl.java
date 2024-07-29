package com.anson.school_books_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anson.school_books_system.po.Announcement;
import com.anson.school_books_system.service.AnnouncementService;
import com.anson.school_books_system.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

/**
* @author user
* @description 针对表【announcement】的数据库操作Service实现
* @createDate 2024-07-29 20:07:18
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService{

}





package com.anson.school_books_system.converter;


import com.anson.school_books_system.api.form.AnnouncementSaveForm;
import com.anson.school_books_system.api.vo.AnnouncementVO;
import com.anson.school_books_system.po.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author
 * @package com.anson.school_books_system.converter
 * @createTime
 * @description
 */
@Mapper(componentModel = "spring", typeConversionPolicy = ReportingPolicy.ERROR)
public interface AnnouncementConverter {
    Announcement converter(AnnouncementSaveForm form);

    AnnouncementVO converter(Announcement form);

}

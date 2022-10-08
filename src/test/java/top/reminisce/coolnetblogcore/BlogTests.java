package top.reminisce.coolnetblogcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebAppConfiguration;
import top.reminisce.coolnetblogcore.controller.home.HomeController;
import top.reminisce.coolnetblogcore.pojo.ao.GlobalEachNeedData;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.text.ParseException;
import java.util.*;

@SpringBootTest
@WebAppConfiguration
@Component
class BlogTests {
    @Autowired
    private GlobalEachNeedData data;
    @Autowired

    private HomeController homeController;
    @Test
    void SCOPE_PROTOTYPETest() {
        System.out.println(data);
    }
    @Test
    void getMongoPageCountTest() {
        homeController.getArticles("keyword", "aa", 1, 1);
        System.out.println(data);
    }

    @Test
    void dateAdd() throws ParseException {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"), new Locale("zh", "CN"));
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
        System.out.println(calendar.getTime().toString());
        System.out.println("----");
        System.out.println(TimeUtils.currentDateTime());
        System.out.println(TimeUtils.currentDateTimeText());
        System.out.println(TimeUtils.dateTextConvertToDate("2022-10-08 17:02:10"));
        System.out.println(TimeUtils.dateConvertToDateText(TimeUtils.currentDateTime()));
        System.out.println(TimeUtils.dateExcludeTime(TimeUtils.currentDateTime()));
        System.out.println(TimeUtils.dateConvertToDateText(TimeUtils.dateExcludeTime(TimeUtils.currentDateTime())));
    }
    @Test
    void exceptionTest(){

    }
}

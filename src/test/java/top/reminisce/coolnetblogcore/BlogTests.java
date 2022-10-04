package top.reminisce.coolnetblogcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.controller.home.HomeController;
import top.reminisce.coolnetblogcore.pojo.ao.GlobalEachNeedData;

@SpringBootTest
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
}

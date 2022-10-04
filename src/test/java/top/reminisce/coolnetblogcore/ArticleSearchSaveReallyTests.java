package top.reminisce.coolnetblogcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.controller.home.HomeController;
import top.reminisce.coolnetblogcore.pojo.ao.GlobalEachNeedData;

@SpringBootTest
@Component
class ArticleSearchSaveReallyTests {
    @Autowired
    private GlobalEachNeedData data;
    @Autowired

    private HomeController homeController;

    @Test
    void saveArticleToElasticSearchTest() {
        System.out.println(data);
    }
}

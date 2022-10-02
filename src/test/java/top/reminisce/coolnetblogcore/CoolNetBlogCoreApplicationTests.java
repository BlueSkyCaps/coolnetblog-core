package top.reminisce.coolnetblogcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.reminisce.coolnetblogcore.pojo.ao.Tree;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.service.home.HomeService;

@SpringBootTest
class CoolNetBlogCoreApplicationTests {

    @Autowired
    HomeService homeService;
    CoreMenu menu;
    @Test
    void contextLoads() {
    }
    @Test
    void extendsTest() {

        // Tree tree = new CoreMenu();
        // tree.setPid(11);
        // System.out.println(tree.get());
    }
    @Test
    void menuTreeTest() {
        Object data = homeService.dealGlobalEachNeedData();
        System.out.println(data);
    }

    @Test
    void mongoGetTest() {
        Object data = homeService.dealGlobalEachNeedData();
        System.out.println(data);
    }

    @Test
    void mongoGetAdminExcludeTest() {
        Object data = homeService.dealGlobalEachNeedData();
        System.out.println(data);
    }

    @Test
    void customExceptionTest() {
        Object data = homeService.dealGlobalEachNeedData();
        System.out.println(data);
    }
}

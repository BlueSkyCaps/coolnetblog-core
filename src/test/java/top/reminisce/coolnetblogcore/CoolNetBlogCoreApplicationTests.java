package top.reminisce.coolnetblogcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import top.reminisce.coolnetblogcore.pojo.ao.GlobalEachNeedData;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;

@SpringBootTest
class CoolNetBlogCoreApplicationTests {

    @Autowired
    HomeGlobalNeedService homeService;
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

    @Autowired
    private GlobalEachNeedData data;
    @Autowired
    private ApplicationContext context;
    @Test
    void SCOPE_PROTOTYPETest() {
        System.out.println(data);
        System.out.println(context.getBean(GlobalEachNeedData.class));

    }
    @Autowired
    private ArticleSearchRepository repository;
    @Autowired
    private ElasticsearchRestTemplate template;
    @Test
    void elasticSearchRestTemplateTest() {
        ArticleSearch articleSearch = new ArticleSearch();
        articleSearch.setAbstractMsg("测2");
        articleSearch.setContent("草草草草2");
        repository.saveOne(template, articleSearch);

    }
}

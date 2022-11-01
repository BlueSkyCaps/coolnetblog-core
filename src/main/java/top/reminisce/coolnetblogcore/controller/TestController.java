package top.reminisce.coolnetblogcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.dto.ThumbUpDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.ThumbUpService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;
import top.reminisce.coolnetblogcore.util.PathUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.file.Paths;

/**
 * @author BlueSky
 * @date 2022/10/14
 */
@RestController
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    private HttpServletRequest servletRequest;
    @GetMapping("real/path")
    public String test(){

        return Paths.get(PathUtils.getCurrentProjectStaticResourcesPath(), "/static/img/abc.jpg").toString();
    }

}

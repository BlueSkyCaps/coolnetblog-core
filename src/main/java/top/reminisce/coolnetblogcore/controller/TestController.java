package top.reminisce.coolnetblogcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.util.PathUtils;

import javax.servlet.http.HttpServletRequest;
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

package top.reminisce.coolnetblogcore;

import joptsimple.internal.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import top.reminisce.coolnetblogcore.util.PathUtils;
import top.reminisce.coolnetblogcore.util.SecurityPasswordUtils;
import top.reminisce.coolnetblogcore.util.TextStringUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static top.reminisce.coolnetblogcore.util.TimeUtils.getDateTimeTextUsePatten;

/**
 * @author BlueSky
 * @date 2022/10/14
 */
public class CommonTest {
    @Test
    void  pathUriTest(){
        var link = File.separator+Paths.get("domain", "detail").toString();

        System.out.println(File.separator);
        System.out.println(link);
    }
    @Test
    void  timeCustomPattenTest(){
        System.out.println(getDateTimeTextUsePatten(new Date(), "yyyyMdd"));
    }
    @Test
    void getPath() throws IOException {
        String saveAbsPathName = PathUtils.getCurrentProjectSourcePath();
        System.out.println(saveAbsPathName);
        String dbRefPath;
        // 若类型是图片 保存到图片静态资源路径

        String nowDateDir = TimeUtils.getDateTimeTextUsePatten(TimeUtils.currentDateTime(), "yyyyMMdd");
        dbRefPath = File.separator + Paths.get("static", "img", nowDateDir, "abc");
        System.out.println(dbRefPath);

        saveAbsPathName = Paths.get(saveAbsPathName, dbRefPath).toString();
        System.out.println(saveAbsPathName);

        // 截取文件类型后缀
        String suffix = PathUtils.getFileNameSuffix("abc.jpg");
        String filePath = saveAbsPathName+"."+suffix;
        System.out.println(filePath);


        System.out.println(new File("static/img").getCanonicalPath());


    }
    @Test
    void stringText(){
        String a = "1    2  2       , ,  ,,  3";
        if (StringUtils.hasText(a)){
            List<String> strings = Arrays.stream(a.split(TextStringUtils.SPACE_VALUE))
                .filter(s->! s.isEmpty()).distinct()
                .collect(Collectors.toList());
            System.out.println(Strings.join(strings, TextStringUtils.SPACE_VALUE));
        }
    }

    @Test
    void genBcryptPasswordTest(){

        String bcryptPassword = SecurityPasswordUtils.genBcryptPassword("123456");
        System.out.printf(bcryptPassword);
    }

    @Test
    void passwordMatchTest(){
        boolean bcryptPassword = SecurityPasswordUtils.passwordMatch("123456",
            "$2a$10$.vLMro8QM3CxdOpvMN9DEeesZk10vc87BqUreNbH1njX9R8K.b6im");
        System.out.printf(String.valueOf(bcryptPassword));
    }

    @Test
    void durationTest(){
        Duration ofMillis = Duration.ofMillis(1000);
        System.out.printf(ofMillis.toString());
    }
}

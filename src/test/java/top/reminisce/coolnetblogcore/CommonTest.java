package top.reminisce.coolnetblogcore;

import lombok.var;
import org.junit.jupiter.api.Test;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.util.PathUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

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
}

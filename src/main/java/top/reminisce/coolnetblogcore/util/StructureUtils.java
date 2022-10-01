package top.reminisce.coolnetblogcore.util;

import top.reminisce.coolnetblogcore.pojo.ao.Tree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础工具类
 * @author BlueSky
 * @date 2022/10/1
 */
public class StructureUtils {

    public static <T extends Tree<T>> List<T> toTree(List<T> topObjs, List<T> children) {
        for (T top :topObjs ) {
            top.setChildren(children.stream().filter(t->t.getPid().equals(top.getId())).collect(Collectors.toList()));
            if (! top.getChildren().isEmpty()){
                toTree(top.getChildren(), children);
            }
        }
        return topObjs;
    }
}

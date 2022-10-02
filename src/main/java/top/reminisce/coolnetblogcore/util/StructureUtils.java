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

    /**
     * 形成树形（多级）结构。元素以id字段为唯一标识主键，以pid为对应父id字段，且继承自Tree类。（继承类可以不定义id和pid）
     * @param topObjs 顶级父元素列表
     * @param children 非顶级的所有子元素列表
     * @return 递归完成的树形结构，其中children为下级元素
     * @param <T> 继承子Tree的泛型约束
     */
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

package top.reminisce.coolnetblogcore.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.reminisce.coolnetblogcore.common.BlogException;

import java.util.Objects;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.SEARCH_ACTION_FROM_KEYWORD;
import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.searchArticleActionValues;

/**
 * 验证博客业务数据有效性工具类
 * @author BlueSky
 * @date 2022/10/2
 */
public class ValidationUtils {
    /**
     * 验证基本分页参数
     * @param index 页码
     * @param size 每页数
     */
    public static void pagePramsCheck(Integer index, Integer size){
        if (Objects.isNull(index) || index < 1){
            throw new BlogException("分页索引必须大于0");
        }
        if (Objects.isNull(size) || size < 1){
            throw new BlogException("分页的每页数量必须大于0");
        }
    }

    /**
     * 验证文章搜索参数逻辑
     * @param from 来源动作，菜单还是关键词搜索
     * @param keyword 若来源是关键词搜索，则是关键词
     */
    public static void searchArticlePramsCheck(String from, String keyword){
        if (! ObjectUtils.isEmpty(from) && ! searchArticleActionValues.contains(from)){
            throw new BlogException("当前文章搜索的动作来源无效，提供了具体来源值，但不是有效的来源值");
        }
        if (Objects.equals(from, SEARCH_ACTION_FROM_KEYWORD) && ObjectUtils.isEmpty(keyword)){
            throw new BlogException(String.format("当前文章搜索的动作来源为%s，但要搜索的关键词未提供", SEARCH_ACTION_FROM_KEYWORD));
        }
    }
}

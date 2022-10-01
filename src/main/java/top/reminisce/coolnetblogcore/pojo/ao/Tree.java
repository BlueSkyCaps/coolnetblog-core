package top.reminisce.coolnetblogcore.pojo.ao;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 统一的树形结构 用于实体继承。递归实体列表，形成基于多级的实体列表。
 * @author BlueSky
 * @date 2022/10/1
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Tree<T>{
    private Integer id;
    private Integer pid;

    @TableField(exist = false)
    private List<T>  children;
}

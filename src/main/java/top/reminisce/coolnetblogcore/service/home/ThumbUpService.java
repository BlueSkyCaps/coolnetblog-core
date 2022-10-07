package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.dto.ThumbUpDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * 内容点赞服务层接口
 * @author BlueSky
 * @date 2022/10/1
 */
public interface ThumbUpService extends BaseService {

    /**
     * 为内容点赞，如文章或评论、回复
     *
     * @param thumbUpDto 前端传递的点赞内容dto<br/>
     *                   thumbUpDto.sourceId 要点赞的原内容ido<br/>
     *                   thumbUpDto.sourceType 要点赞的原内容类型，诸如文章为1，评论2，回复3..<br/>
     *                   thumbUpDto.thumbType 点赞类型，若是文章点赞，此字段不为null。<br/>
     * @return 点赞后的点赞实体数据
     */
    CoreThumbUp thumbUpSource(ThumbUpDto thumbUpDto, HttpServletRequest request);

}

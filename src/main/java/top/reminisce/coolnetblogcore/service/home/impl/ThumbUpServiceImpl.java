package top.reminisce.coolnetblogcore.service.home.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.pojo.dto.ThumbUpDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;
import top.reminisce.coolnetblogcore.repository.mongo.ThumpUpRepository;
import top.reminisce.coolnetblogcore.service.home.ThumbUpService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeQueryService;
import top.reminisce.coolnetblogcore.util.PathUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.ThumbUpAddDtoToThumbUpMapperUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * 内容点赞服务层实现类
 * @author BlueSky
 * @date 2022/10/7
 */
@Service
public class ThumbUpServiceImpl extends AbstractHomeQueryService implements ThumbUpService {
    private final ThumpUpRepository thumpUpRepository;

    public ThumbUpServiceImpl(ThumpUpRepository thumpUpRepository) {
        this.thumpUpRepository = thumpUpRepository;
    }

    /**
     * 检查点赞实体逻辑
     * @param thumbUp 点赞实体
     * @param request 当前上下文请求，用于获取ip来标识点赞与否
     */
    private void initThumbUpLogic(CoreThumbUp thumbUp, HttpServletRequest request){
        if (ObjectUtils.isEmpty(request))
        {
            throw new BlogException("点赞: HttpServletRequest不得为空");
        }
        if (thumbUp.getSourceType() ==1 && ObjectUtils.isEmpty(thumbUp.getThumbType()))
        {
            throw new BlogException("文章点赞：点赞的风格类别不得为空");
        }
        thumbUpEntityPack(thumbUp, request);
    }

    private void thumbUpEntityPack(CoreThumbUp thumbUp, HttpServletRequest request) {
        String currentIp = PathUtils.getClientSourceIp(request);
        // 验证当前ip是否点赞过此内容
        CriteriaDefinition criteriaDefinition = new Criteria()
            .and("sourceId").is(thumbUp.getSourceId())
            .and("sourceType").is(thumbUp.getSourceType())
            .and("ip").is(thumbUp.getClientIp());
        boolean exists = this.thumpUpRepository
            .conditionWhereAlreadyExists(super.beanUtils.getMongoTemplate(), criteriaDefinition, CoreThumbUp.class);
        if (exists){
            throw new RuntimeException("你已经点过赞啦，不要重复点赞~");
        }
        thumbUp.setClientIp(currentIp);
        thumbUp.setUpTime(TimeUtils.currentDateTime());
    }

    @Override
    public CoreThumbUp thumbUpSource(ThumbUpDto thumbUpDto, HttpServletRequest request) {
        CoreThumbUp thumbUp = ThumbUpAddDtoToThumbUpMapperUtils.INSTANCE.thumbUpAddDtoToThumbUp(thumbUpDto);
        initThumbUpLogic(thumbUp, request);
        return this.thumpUpRepository.save(thumbUp);
    }
}

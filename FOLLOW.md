### 😂重写选型文档（mongo与mysql，以及缓存和搜索）

### mongo

#### core_sys_admin集合中的文档
包含的原mysql表
+ admin_user
+ site_setting
迁移成：
```json
{
  "id": 1,
  "token": "",
  "admin_info": {
  },
  "site_setting": {
  }
}
````
#### core_thumb_up集合中的文档
包含的原mysql表
+ article_thumb_up：文章点赞
#### core_comment集合中的文档
包含的原mysql表
+ comment：评论
#### core_reply集合中的文档
包含的原mysql表
+ reply：评论回复

### mysql
#### 新增的表
+ core_field_map：字段固定值字典
#### 不变的表
+ article：文章
+ menu：菜单
+ gossip：“闲言碎语”表
+ love_look：“看看这些”表
+ file_path：文件地址表

### 缓存redis
前台界面都可以走缓存，如每次刷新都需要展示menu和gossip。

### 搜索和分页
文章分页列表展示主要信息，如摘要、标题、时间。无论无关键词分页、关键词搜索分页、菜单分页，都用es查询。

### 补缺
评论只会在具体文章页显示，评论、回复、点赞用mongo处理。点击具体文章，查询mysql和mongo。
由于博客是单体服务器实例，mongodb不能多文档事务。因此关键的文章、
菜单使用mysql存储，利用事务进行删除时，提交后再使用mongo删除关联评论等。评论等数据无需考虑原子性，删除失败则忽略。垃圾数据采用定时任务进行统一清理。
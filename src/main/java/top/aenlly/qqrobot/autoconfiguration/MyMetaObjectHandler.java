package top.aenlly.qqrobot.autoconfiguration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充插入字段与更新字段
 *
 * @author Aenlly
 * @create by date 2021/12/16 21:58
 * @projectName RefuseClassificationCultivate
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  /** 修改更新 */
  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictUpdateFill(
        metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
  }

  @Override
  public void insertFill(MetaObject metaObject) {
    this.strictInsertFill(
            metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
    this.strictInsertFill(
            metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
  }
}
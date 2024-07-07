package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.ArrayTypeHandler;
import top.aenlly.qqrobot.enmus.AuditEnum;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "group_config",autoResultMap = true)
public class GroupConfigEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("group_id")
    private Long groupId;

    /**
     * 通知者qq
     */
    @TableField(value = "notice_qq",typeHandler = ArrayTypeHandler.class)
    private List<Long> noticeQQ;

    /**
     * 审核
     */
    @TableField("auto_audit")
    private AuditEnum autoAudit;


    /**
     * 黑名单阈值
     */
    @TableField("black_number")
    private int blackNumber;

}

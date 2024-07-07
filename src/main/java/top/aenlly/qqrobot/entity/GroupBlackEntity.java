package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "group_black",autoResultMap = true)
public class GroupBlackEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("group_id")
    private Long groupId;

    /**
     * 通知者qq
     */
    @TableField("black_qq")
    private Long blackQQ;

    /**
     * 匹配值
     */
    @TableField("auto_audit")
    private int number;

}

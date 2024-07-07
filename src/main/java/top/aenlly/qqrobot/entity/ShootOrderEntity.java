package top.aenlly.qqrobot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.aenlly.qqrobot.enmus.ShootOrderStatusEnum;
import top.aenlly.qqrobot.enmus.ShootTypeEnum;

import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "shoot_order",autoResultMap = true)
public class ShootOrderEntity extends BaseEntity{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("shoot_time")
    private LocalDate shootTime;

    @TableField("time_interval")
    private String timeInterval;

    @TableField("shoot_type")
    private ShootTypeEnum shootType;

    @TableField("qq")
    private int qq;
    @TableField("nick_name")
    private String nickName;


    @TableField("deposit")
    private int deposit;


    @TableField("final_money")
    private int finalMoney;

    @TableField("all_money")
    private int allMoney;

    @TableField("status")
    private ShootOrderStatusEnum status;

    @TableField("remark")
    private String remark;

    @TableField("admin_qq")
    private String adminQQ;
}

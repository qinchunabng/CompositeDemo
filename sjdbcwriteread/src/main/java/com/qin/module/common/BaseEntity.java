package com.qin.module.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * BaseEntity
 *
 * @blame Android Team
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6141866755261426157L;

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;

}

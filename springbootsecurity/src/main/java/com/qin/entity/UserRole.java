package com.qin.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author qcb
 * @since 2020-01-19
 */
@Data
@Accessors(chain = true)
public class UserRole {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;

}

package com.qin.mapper;

import com.qin.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qcb
 * @since 2020-01-19
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    List<RolePermission> getRolePermissions();
}

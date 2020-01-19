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
public class Permission {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String url;

    private String name;

    private String description;

    private Long pid;
}

package com.qin.module.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class UserDto implements Serializable {

    private static final long serialVersionUID = 8190729358736832603L;

    private Integer id;

    private String city;

    private String name;
}

package com.ssafy.s103.domain.user.entity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    private com.ssafy.s103.domain.user.entity.User user;

    public CustomUser(com.ssafy.s103.domain.user.entity.User user) {
        super(user.getUsername(), user.getPassword(),
            AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }
}

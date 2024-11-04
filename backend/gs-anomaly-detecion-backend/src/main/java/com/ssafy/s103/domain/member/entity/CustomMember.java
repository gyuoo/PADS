package com.ssafy.s103.domain.member.entity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomMember extends User {

    public CustomMember(Member member) {
        super(member.getUsername(), member.getPassword(),
            AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }
}

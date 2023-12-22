package com.crud.simple.dto;

import lombok.Data;

import java.util.List;

import com.crud.simple.model.User;

@Data
public class UserPageDto {
    private List< User >  user;
    private boolean first_page;
    private boolean last_page;
    private int page_number;
    public UserPageDto(List<User> user, boolean first_page, boolean last_page, int page_number) {
        this.user = user;
        this.first_page = first_page;
        this.last_page = last_page;
        this.page_number = page_number;
    }
}

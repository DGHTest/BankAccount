package com.banktest.account.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordModel {

    private String email;
    private String oldPassword;
    private String newPassword;
}

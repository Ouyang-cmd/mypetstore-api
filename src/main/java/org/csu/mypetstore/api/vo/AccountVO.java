package org.csu.mypetstore.api.vo;

import lombok.Data;

@Data
public class AccountVO
{
    //from table signon
    private String username;
    private String password;

    //from table account
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;

    //from table profile
    private String favouriteCategoryId;
    private String languagePreference;
    private boolean listOption;
    private boolean bannerOption;

    //from table bannerdata
    private String bannerName;
}

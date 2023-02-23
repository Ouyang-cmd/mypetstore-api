package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.entity.Account;
import org.csu.mypetstore.api.vo.AccountVO;

public interface AccountService
{
    CommonResponse<AccountVO> getAccount(String username);

    CommonResponse<AccountVO> register(AccountVO accountVO);

    CommonResponse<AccountVO> editAccount(String username, AccountVO accountVO);

    CommonResponse<String> signOn(String username, String password);
}

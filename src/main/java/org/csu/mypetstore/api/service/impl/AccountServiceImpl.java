package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.entity.Account;
import org.csu.mypetstore.api.entity.AccountProfile;
import org.csu.mypetstore.api.entity.AccountSignOn;
import org.csu.mypetstore.api.entity.BannerData;
import org.csu.mypetstore.api.persistence.AccountMapper;
import org.csu.mypetstore.api.persistence.AccountProfileMapper;
import org.csu.mypetstore.api.persistence.AccountSignOnMapper;
import org.csu.mypetstore.api.persistence.BannerDataMapper;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JWTUtil;

import java.util.HashMap;
import java.util.Map;

@Service("accountService")
public class AccountServiceImpl implements AccountService
{

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountProfileMapper accountProfileMapper;

    @Autowired
    private AccountSignOnMapper accountSignOnMapper;

    @Autowired
    private BannerDataMapper bannerDataMapper;

    @Override
    public CommonResponse<AccountVO> getAccount(String username)
    {
        Account account = accountMapper.selectById(username);

        if(account == null)
        {
            return CommonResponse.creatForSuccessMessage("用户名不存在");
        }

        AccountProfile accountProfile = accountProfileMapper.selectById(username);
        AccountSignOn accountSignOn = accountSignOnMapper.selectById(username);
        BannerData bannerData = bannerDataMapper.selectById(accountProfile.getFavouriteCategoryId());
        AccountVO accountVO = accountToAccountVO(account, accountProfile, accountSignOn, bannerData);

        return CommonResponse.creatForSuccess(accountVO);
    }

    @Override
    public CommonResponse<AccountVO> register(AccountVO accountVO)
    {
        Account account = accountMapper.selectById(accountVO.getUsername());
        if(account != null)
        {
            return CommonResponse.creatForError("用户名已存在");
        }
        accountMapper.insert(accountVOTOAccount(accountVO));
        accountProfileMapper.insert(accountVOTOAccountProfile(accountVO));
        accountSignOnMapper.insert(accountVOTOAccountSignOn(accountVO));
        bannerDataMapper.insert(accountVOTOBannerData(accountVO));
        return CommonResponse.creatForSuccess(accountVO);
    }

    @Override
    public CommonResponse<AccountVO> editAccount(String username, AccountVO accountVO)
    {
        accountMapper.updateById(accountVOTOAccount(accountVO));
        accountProfileMapper.updateById(accountVOTOAccountProfile(accountVO));
        accountSignOnMapper.updateById(accountVOTOAccountSignOn(accountVO));
        bannerDataMapper.updateById(accountVOTOBannerData(accountVO));
        return CommonResponse.creatForSuccess("修改成功", accountVO);
    }

    @Override
    public CommonResponse<String> signOn(String username, String password)
    {
        QueryWrapper<AccountSignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        AccountSignOn accountSignOn = accountSignOnMapper.selectOne(queryWrapper);
        if(accountSignOn == null)
        {
            return CommonResponse.creatForError("用户名或密码错误");
        }
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        String token = JWTUtil.getToken(payload);
        return CommonResponse.creatForSuccessMessage(token);

//        Account account = accountMapper.selectById(username);
//        AccountProfile accountProfile = accountProfileMapper.selectById(username);
//        BannerData bannerData = bannerDataMapper.selectById(accountProfile.getFavouriteCategoryId());
//        AccountVO accountVO = accountToAccountVO(account, accountProfile, accountSignOn, bannerData);
    }


    private AccountVO accountToAccountVO(Account account, AccountProfile accountProfile, AccountSignOn accountSignOn, BannerData bannerData)
    {
        AccountVO accountVO = new AccountVO();

        accountVO.setUsername(accountSignOn.getUsername());
        accountVO.setPassword(accountSignOn.getPassword());

        accountVO.setEmail(account.getEmail());
        accountVO.setFirstName(account.getFirstName());
        accountVO.setLastName(account.getLastName());
        accountVO.setStatus(account.getStatus());
        accountVO.setAddress1(account.getAddress1());
        accountVO.setAddress2(account.getAddress2());
        accountVO.setCity(account.getCity());
        accountVO.setState(account.getState());
        accountVO.setZip(account.getZip());
        accountVO.setCountry(account.getCountry());
        accountVO.setPhone(account.getPhone());

        accountVO.setFavouriteCategoryId(accountProfile.getFavouriteCategoryId());
        accountVO.setLanguagePreference(accountProfile.getLanguagePreference());
        accountVO.setListOption(accountProfile.isListOption());
        accountVO.setBannerOption(accountProfile.isBannerOption());

        accountVO.setBannerName(bannerData.getBannerName());

        return accountVO;
    }

    private Account accountVOTOAccount(AccountVO accountVo)
    {
        Account account = new Account();
        account.setUsername(accountVo.getUsername());
        account.setEmail(accountVo.getEmail());
        account.setFirstName(accountVo.getFirstName());
        account.setLastName(accountVo.getLastName());
        account.setStatus(accountVo.getStatus());
        account.setAddress1(accountVo.getAddress1());
        account.setAddress2(accountVo.getAddress2());
        account.setCity(accountVo.getCity());
        account.setState(accountVo.getState());
        account.setZip(accountVo.getZip());
        account.setCountry(accountVo.getCountry());
        account.setPhone(accountVo.getPhone());
        return account;
    }

    private AccountProfile accountVOTOAccountProfile(AccountVO accountVO)
    {
        AccountProfile accountProfile = new AccountProfile();
        accountProfile.setUsername(accountVO.getUsername());
        accountProfile.setFavouriteCategoryId(accountVO.getFavouriteCategoryId());
        accountProfile.setLanguagePreference(accountVO.getLanguagePreference());
        accountProfile.setBannerOption(accountVO.isBannerOption());
        accountProfile.setListOption(accountVO.isListOption());
        return accountProfile;
    }

    private AccountSignOn accountVOTOAccountSignOn(AccountVO accountVO)
    {
        AccountSignOn accountSignOn = new AccountSignOn();
        accountSignOn.setUsername(accountVO.getUsername());
        accountSignOn.setPassword(accountVO.getPassword());
        return accountSignOn;
    }

    private BannerData accountVOTOBannerData(AccountVO accountVO)
    {
        BannerData bannerData = new BannerData();
        bannerData.setFavouriteCategoryId(accountVO.getFavouriteCategoryId());
        bannerData.setBannerName(accountVO.getBannerName());
        return bannerData;
    }
}

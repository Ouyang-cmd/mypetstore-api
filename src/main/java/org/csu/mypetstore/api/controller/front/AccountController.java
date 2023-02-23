package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account/")
public class AccountController
{

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts/{id}")
    @ResponseBody
    public CommonResponse<AccountVO> getAccount(@PathVariable("id") String username)
    {
        return accountService.getAccount(username);
    }

    @PostMapping("/accounts")
    @ResponseBody
    public CommonResponse<AccountVO> register(@RequestParam("accountVO") AccountVO accountVO)
    {
        return accountService.register(accountVO);
    }

    @PutMapping("/accounts/{id}")
    @ResponseBody
    public CommonResponse<AccountVO> editAccount(@PathVariable("id") String userame, @RequestParam("accountVO") AccountVO accountVO)
    {
        return accountService.editAccount(userame, accountVO);
    }

    @PostMapping("/tokens")
    @ResponseBody
    public CommonResponse<String> signOn(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        return accountService.signOn(username, password);
    }

    @DeleteMapping("/tokens")
    @ResponseBody
    public CommonResponse<AccountVO> signOff()
    {
        return null;
    }

    @PostMapping("/test")
    @ResponseBody
    public CommonResponse<String> test()
    {
        return CommonResponse.creatForSuccess("token通过");
    }

}

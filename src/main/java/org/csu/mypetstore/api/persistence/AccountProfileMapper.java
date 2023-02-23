package org.csu.mypetstore.api.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.mypetstore.api.entity.AccountProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProfileMapper extends BaseMapper<AccountProfile>
{
}

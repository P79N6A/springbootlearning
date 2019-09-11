package aopcachetest.service;


import aopcachetest.cache.annotattion.Cache;
import aopcachetest.dto.CompanyDTO;
import aopcachetest.entity.CompanyPO;
import aopcachetest.entity.UserPO;
import aopcachetest.mapper.CompanyPOMapper;
import aopcachetest.mapper.UserPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EleService {
    @Autowired
    private UserPOMapper userPOMapper;

    @Autowired
    private CompanyPOMapper companyPOMapper;

    @Cache(key = "#id")
    public UserPO getUser(Long id) {
        return userPOMapper.selectByPrimaryKey(id);
    }

    public List<UserPO> getUser1(UserPO userPO) {
        return userPOMapper.selectByPO(userPO);
    }


    public CompanyDTO getComDTO(CompanyPO companyPO) {
        return companyPOMapper.selectByCompanyPO(companyPO);
    }

    public int insertUserPO(UserPO userPO) {
        return userPOMapper.insertByPO(userPO);
    }



}

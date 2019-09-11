package aopcachetest.controller;


import aopcachetest.service.EleService;
import aopcachetest.dto.CompanyDTO;
import aopcachetest.entity.CompanyPO;
import aopcachetest.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/a")
public class EleController {
    @Autowired
    private EleService eleService;

    @GetMapping("/b")
    public UserPO getUserPO(Long id) {
        return eleService.getUser(id);
    }

    @GetMapping("/b1")
    public List<UserPO> getUserPO2(UserPO userPO) {
        return eleService.getUser1(userPO);
    }

    @GetMapping("/b2")
    public CompanyDTO getD(CompanyPO companyPO) {
        return eleService.getComDTO(companyPO);
    }

    @PostMapping("/b3")
    public int insertUserPO(UserPO userPO) {
        return eleService.insertUserPO(userPO);
    }

}

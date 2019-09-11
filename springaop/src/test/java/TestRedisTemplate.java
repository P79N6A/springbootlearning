import aopcachetest.Application;

import aopcachetest.entity.UserPO;
import aopcachetest.mapper.UserPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TestRedisTemplate {
    @Autowired
    private UserPOMapper userPOMapper;
    @Value("${ele.mapper.globalid}")
    private boolean globalid;

    @Test
    public void test1() {
        UserPO userPO = new UserPO();
        userPO.setAge(21);
        System.out.println(userPOMapper.selectByPO(userPO));
    }

}

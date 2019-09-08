import mybatistest.Application;
import mybatistest.dao.StudentDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class MybatisTest {
    @Autowired
    private StudentDAO studentDAO;
    @Test
    public void test1() {
        System.out.println(studentDAO.selectById(1));
    }
}

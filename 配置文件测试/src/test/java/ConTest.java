import configuration.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class ConTest {
    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void test1() {
        System.out.println(applicationContext.getBean("people"));
    }

    @Test
    public void test2() {
        System.out.println(applicationContext.getBean("student"));
    }

    @Test
    public void test3() {
        ExpressionParser expressionParser = new SpelExpressionParser();
        System.out.println(expressionParser.parseExpression("1994-11-08").getValue());
    }
}

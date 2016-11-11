package info.zhwan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBatchMultilineTestApplicationTests {

	@Autowired
	GenericApplicationContext context;
	
	@Test
	public void contextLoads() {
		for (String bean : context.getBeanDefinitionNames()) {
			System.err.println(bean);
		}
	}
}

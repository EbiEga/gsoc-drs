package com.ega.datarepositorysevice;

import com.ega.datarepositorysevice.utils.AssumingConnection;
import com.ega.datarepositorysevice.utils.ConnectionChecker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DataRepositorySeviceApplicationTests {

    @ClassRule
    public static AssumingConnection assumingConnection =
            new AssumingConnection(new ConnectionChecker());

    @Test
    public void contextLoads() {
    }

}

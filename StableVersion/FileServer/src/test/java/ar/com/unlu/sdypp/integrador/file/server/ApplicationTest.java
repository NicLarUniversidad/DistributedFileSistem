package ar.com.unlu.sdypp.integrador.file.server;

import ar.com.unlu.sdypp.integrador.file.server.mocks.ControllerMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ApplicationTest {

    @Autowired
    private ControllerMock controllerMock;

    @Test
    public void contextLoads() {
        assertNotNull(controllerMock);
    }
}

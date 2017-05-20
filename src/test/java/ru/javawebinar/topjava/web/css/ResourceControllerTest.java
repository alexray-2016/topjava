package ru.javawebinar.topjava.web.css;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Administrator on 19.05.2017.
 */

public class ResourceControllerTest extends AbstractControllerTest{

    @Test
    public void testStyle() throws Exception
    {
        mockMvc.perform(get("/resources/css/style.css")
        .contentType("text/css"))
                .andExpect(status().isOk());
    }
}

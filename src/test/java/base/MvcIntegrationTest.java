package base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.configuration.AppConfig;

@SpringJUnitConfig(classes = {AppConfig.class})
@WebAppConfiguration("classpath:/com")
public class MvcIntegrationTest {

	@Autowired
    private WebApplicationContext webApplicationContext;

    public MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public MvcRequestBuilder get(String uri) {
        return new MvcRequestBuilder(MockMvcRequestBuilders.get(uri), mockMvc);
    }

    public MvcRequestBuilder post(String uri) {
        return new MvcRequestBuilder(MockMvcRequestBuilders.post(uri), mockMvc);
    }

    public MvcRequestBuilder delete(String uri) {
        return new MvcRequestBuilder(MockMvcRequestBuilders.delete(uri), mockMvc);
    }

    public MvcRequestBuilder put(String uri) {
        return new MvcRequestBuilder(MockMvcRequestBuilders.put(uri), mockMvc);
    }

    public MvcRequestBuilder multipartPost(String uri) {
        return new MvcRequestBuilder(MockMvcRequestBuilders.multipart(uri), mockMvc);
    }
}

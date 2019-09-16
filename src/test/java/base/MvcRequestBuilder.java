package base;

import static java.util.Collections.emptyMap;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class MvcRequestBuilder {
    private final MockHttpServletRequestBuilder requestBuilder;
    private final MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Object requestContent = null;
    private Map<String, List<String>> params = emptyMap();


	public MvcRequestBuilder(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, MockMvc mockMvc) {
		this.requestBuilder = mockHttpServletRequestBuilder;
		this.mockMvc = mockMvc;
	}

    public MvcRequestBuilder withContent(Object requestContent) {
        this.requestContent = requestContent;
        return this;
    }

    public <T> MvcJsonResult<T> forJsonOf(Class responseClass, Class... parameterClasses) {
        MvcResult mvcResult;
        T response;
        RequestError requestError;
        try {
            mvcResult = buildAndPerformRequest();
            response = getResponse(mvcResult, responseClass, parameterClasses);
            requestError = getRequestError(mvcResult);
        } catch (Throwable t) {
            throw new RuntimeException(t.getMessage());
        }
        return new MvcJsonResult<>(mvcResult, response, requestError);
    }

    public MvcResult buildAndPerformRequest() throws Exception {
        requestBuilder
                .content(objectMapper.writeValueAsString(requestContent))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        return mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();
    }

    public MvcResult performMultipartRequest(MockMultipartFile jsonFile) throws Exception {
    	return mockMvc.perform(((MockMultipartHttpServletRequestBuilder) requestBuilder)
                .file(jsonFile)
                .characterEncoding("UTF-8")).andReturn();
    }

    public MvcResult performMultipart() throws Exception {
    	return mockMvc.perform(requestBuilder)
    			.andDo(print())
    			.andReturn();
    }

    private <T> T getResponse(MvcResult mvcResult, Class responseClass, Class... parameterClasses) throws Exception {
        T response = null;
        if (responseClass != null &&
                !mvcResult.getResponse().getContentAsString().isEmpty() &&
                mvcResult.getResponse().getStatus() >= HttpStatus.OK.value() &&
                mvcResult.getResponse().getStatus() < HttpStatus.MULTIPLE_CHOICES.value()) {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(responseClass, parameterClasses);
            response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), javaType);
        }
        return response;
    }

    private RequestError getRequestError(MvcResult mvcResult) throws Exception {
        RequestError requestError = null;
        if (!mvcResult.getResponse().getContentAsString().isEmpty() &&
                mvcResult.getResponse().getStatus() >= HttpStatus.BAD_REQUEST.value()) {
            requestError = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RequestError.class);
        }
        return requestError;
    }
}

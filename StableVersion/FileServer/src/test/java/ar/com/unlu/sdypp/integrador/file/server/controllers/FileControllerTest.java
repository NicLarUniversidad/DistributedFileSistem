package ar.com.unlu.sdypp.integrador.file.server.controllers;

import ar.com.unlu.sdypp.integrador.file.server.ApplicationTest;
import ar.com.unlu.sdypp.integrador.file.server.models.RealFileLocation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest extends ApplicationTest {

    private final ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public FileControllerTest() {
        objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    @Test
    public void contextLoads() {
        super.contextLoads();
        assertNotNull(webApplicationContext);
    }

    @Test
    void saveFile() throws Exception {
        MockMultipartFile file = createMockFile();
        MockMultipartHttpServletRequestBuilder builder = createServletRequestBuilder("/file", "PUT");
        performSaveFile(builder, file)
                .andDo(print())
                .andExpect(status().isOk());
        MockMultipartHttpServletRequestBuilder builderGet = createServletRequestBuilder("/file", "GET");
        perform(builderGet, "test.txt")
                .andDo(print())
                .andExpect(status().isOk());
    }

    private ResultActions perform(MockMultipartHttpServletRequestBuilder builder, String body) throws Exception {
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        return mockMvc.perform(builder
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(body)
        );
    }

    private ResultActions performSaveFile(MockMultipartHttpServletRequestBuilder builder, MockMultipartFile file) throws Exception {
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RealFileLocation fileLocation = new RealFileLocation();
        fileLocation.setName("test.txt");
        fileLocation.setPath("");
        return mockMvc.perform(builder.file(file)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(fileLocation))
        );
    }

    private MockMultipartHttpServletRequestBuilder createServletRequestBuilder(String url, String method) {
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.fileUpload(url);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod(method);
                return request;
            }
        });
        return builder;
    }

    private MockMultipartFile createMockFile() {
        return new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "test file content".getBytes()
        );
    }

}

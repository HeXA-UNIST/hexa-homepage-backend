package pro.hexa.backend.main.api.domain.main.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.hexa.backend.main.api.domain.main.dto.MainPageResponse;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(controllers = {MainPageController.class})
@RequestMapping("/mainpage")
class MainPageControllerTest {
    @Mock
    private MainPageResponse mainPageResponse;

    @InjectMocks
    private MainPageController mainPageController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMainPageResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/data"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
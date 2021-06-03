package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.service.VerifyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VerifyController.class)
public class VerifyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Required to fix ApplicationContext error
    @MockBean
    private VerifyService verifyService;


    @Test
    void statusBadRequestWhenEmailNotGiven() throws Exception {
        String body = "{\"email\": \"\"}";

        mockMvc.perform(post("/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void statusBadRequestWhenEmailInvalid() throws Exception {
        String body = "{\"email\": \"email\"}";

        mockMvc.perform(post("/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void statusOkWhenResourceValid() throws Exception {
        String body = "{\"email\": \"test@test.com\"}";

        when(verifyService.verify(any())).thenReturn(new UserResponseBuilder().withOk().build());

        mockMvc.perform(post("/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.service.ResetPasswordService;
import be.tomtruyen.cryptotracker.service.VerifyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ResetPasswordController.class)
public class ResetPasswordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Required to fix ApplicationContext error
    @MockBean
    private ResetPasswordService resetPasswordService;

    @MockBean
    private UserDao userDao;


    @Test
    public void resetStatusBadRequestWhenEmailNotGiven() throws Exception {
        String body = "{\"email\": \"\"}";

        mockMvc.perform(post("/resetpassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void resetStatusBadRequestWhenEmailInvalid() throws Exception {
        String body = "{\"email\": \"email\"}";

        mockMvc.perform(post("/resetpassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void resetStatusOkWhenResourceValid() throws Exception {
        String body = "{\"email\": \"test@test.com\"}";

        when(userDao.findUserByEmailAndPassword(anyString(), anyString())).thenReturn(null);
        when(resetPasswordService.reset(any())).thenReturn(new UserResponseBuilder().withOk().build());

        mockMvc.perform(post("/resetpassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void confirmStatusBadRequestWhenEmailNotGiven() throws Exception {
        String body = "{\"email\": \"\", \"password\": \"password\"}";

        mockMvc.perform(post("/resetpassword/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void confirmStatusBadRequestWhenEmailInvalid() throws Exception {
        String body = "{\"email\": \"email\", \"password\": \"password\"}";

        mockMvc.perform(post("/resetpassword/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void confirmStatusOkWhenResourceValid() throws Exception {
        String body = "{\"email\": \"test@test.com\", \"password\": \"Password1\"}";

        when(userDao.findUserByEmailAndPassword(anyString(), anyString())).thenReturn(null);
        when(resetPasswordService.confirm(any())).thenReturn(new UserResponseBuilder().withOk().build());

        mockMvc.perform(post("/resetpassword/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

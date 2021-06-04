package be.tomtruyen.cryptotracker.rest;

import be.tomtruyen.cryptotracker.domain.builder.CryptoResponseBuilder;
import be.tomtruyen.cryptotracker.service.CryptoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CryptoController.class)
public class CryptoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Required to fix ApplicationContext error
    @MockBean
    private CryptoService cryptoService;

    @Test
    void getPortfolioBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(get("/cryptocurrencies/portfolio")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getPortfolioStatusOkWhenHeaderValid() throws Exception {
        when(cryptoService.getPortfolio(anyString())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(get("/cryptocurrencies/portfolio")
            .contentType(MediaType.APPLICATION_JSON)
            .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getPortfolioHistoryBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(get("/cryptocurrencies/portfolio/history")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPortfolioHistoryStatusOkWhenHeaderValid() throws Exception {
        when(cryptoService.getPortfolioHistory(anyString())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(get("/cryptocurrencies/portfolio/history")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCryptoListBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(get("/cryptocurrencies/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCryptoListStatusOkWhenHeaderValid() throws Exception {
        when(cryptoService.getCryptoList(anyString())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(get("/cryptocurrencies/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void buyCryptoBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(post("/cryptocurrencies/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buyCryptoBadRequestWhenCryptoBuyResourceInvalid() throws Exception {
        String body = "{\"ticker\": \"\", \"amount\": \"1\", \"price\": \"1\"}";

        mockMvc.perform(post("/cryptocurrencies/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buyCryptoBadRequestWhenCryptoBuyResourceContainsAmountOrPriceSmallerThanOrEqualToZeroInvalid() throws Exception {
        String body = "{\"ticker\": \"BTC\", \"amount\": \"1\", \"price\": \"0\"}";

        mockMvc.perform(post("/cryptocurrencies/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buyCryptoStatusOkWhenHeaderAndResourceValid() throws Exception {
        String body = "{\"ticker\": \"BTC\", \"amount\": \"1\", \"price\": \"1\"}";

        when(cryptoService.buy(anyString(), any())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(post("/cryptocurrencies/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void sellCryptoBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(post("/cryptocurrencies/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sellCryptoBadRequestWhenCryptoSellResourceInvalid() throws Exception {
        String body = "{\"id\": \"\", \"ticker\": \"\", \"amount\": \"1\", \"price\": \"1\", \"isGas\": \"true\"}";

        mockMvc.perform(post("/cryptocurrencies/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sellCryptoBadRequestWhenCryptoSellResourceIdNotValid() throws Exception {
        String body = "{\"id\": \"0\", \"ticker\": \"BTC\", \"amount\": \"1\", \"price\": \"1\", \"isGas\": \"true\"}";

        mockMvc.perform(post("/cryptocurrencies/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sellCryptoBadRequestWhenCryptoSellResourceContainsAmountOrPriceSmallerThanOrEqualToZeroInvalid() throws Exception {
        String body = "{\"id\": \"1\", \"ticker\": \"BTC\", \"amount\": \"0\", \"price\": \"1\", \"isGas\": \"true\"}";

        mockMvc.perform(post("/cryptocurrencies/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sellCryptoStatusOkWhenHeaderAndSellResourceValid() throws Exception {
        String body = "{\"id\": \"1\", \"ticker\": \"BTC\", \"amount\": \"1\", \"price\": \"1\", \"isGas\": \"true\"}";

        when(cryptoService.sell(anyString(), any())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(post("/cryptocurrencies/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void setPriceAlertBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(post("/cryptocurrencies/alert/set")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setPriceAlertBadRequestWhenSetAlertResourceInvalid() throws Exception {
        String body = "{\"id\": \"0\", \"alert\": \"0\"}";

        mockMvc.perform(post("/cryptocurrencies/alert/set")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setPriceAlertBadRequestWhenSetAlertResourceIdNotValid() throws Exception {
        String body = "{\"id\": \"0\", \"alert\": \"1\"}";

        mockMvc.perform(post("/cryptocurrencies/alert/set")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setPriceAlertBadRequestWhenSetAlertResourceAlertSmallerThanOrEqualToZero() throws Exception {
        String body = "{\"id\": \"1\", \"alert\": \"0\"}";

        mockMvc.perform(post("/cryptocurrencies/alert/set")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setPriceAlertStatusOkWhenHeaderAndResourceValid() throws Exception {
        String body = "{\"id\": \"1\", \"alert\": \"1\"}";

        when(cryptoService.setAlert(anyString(), any())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(post("/cryptocurrencies/alert/set")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deletePriceAlertBadRequestWhenAuthorizationHeaderMissing() throws Exception {
        mockMvc.perform(post("/cryptocurrencies/alert/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePriceAlertBadRequestWhenSetAlertResourceInvalid() throws Exception {
        String body = "{\"id\": \"0\"}";

        mockMvc.perform(post("/cryptocurrencies/alert/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePriceAlertBadRequestWhenSetAlertResourceIdNotValid() throws Exception {
        String body = "{\"id\": \"0\"}";

        mockMvc.perform(post("/cryptocurrencies/alert/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePriceAlertStatusOkWhenHeaderAndResourceValid() throws Exception {
        String body = "{\"id\": \"1\"}";

        when(cryptoService.deleteAlert(anyString(), any())).thenReturn(new CryptoResponseBuilder().withOk().build());

        mockMvc.perform(post("/cryptocurrencies/alert/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNjIyODA4NTU0NDc3LCJpZCI6MSwiZW1haWwiOiJ0b20udHJ1eWVuQGdtYWlsLmNvbSIsImF1dGhEYXRlIjoxNjIyNzIyMTU0NDc3fQ.W5UyVOLprIQHnReFAI19WhnfxNI9cjd27RMpMqS_wMc")
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

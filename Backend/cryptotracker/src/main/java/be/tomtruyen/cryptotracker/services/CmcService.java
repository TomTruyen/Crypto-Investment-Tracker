package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CmcCrypto;
import be.tomtruyen.cryptotracker.domain.CmcCryptoPrice;
import be.tomtruyen.cryptotracker.repositories.CryptoPriceRepository;
import be.tomtruyen.cryptotracker.repositories.CryptoRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CmcService {
    private static final String API_KEY = "585fc9c4-b56a-401d-8d64-31c70ccf507f";

    public static void fetchCryptos() {
        System.out.println("Fetching Crypto's");

        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/map";
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("sort", "cmc_rank"));

        try {
            String result = call(uri, parameters);

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();

            Map<String, Object> json = gson.fromJson(result, type);

            List<Map<String, Object>> cryptos = (List<Map<String, Object>>)json.getOrDefault("data", new ArrayList<>());

            if(cryptos.size() > 0) {
                CryptoRepository.getInstance().clear();
                for(int i = 0; i < cryptos.size(); i++) {
                    CmcCrypto crypto = CmcCrypto.fromJSON(cryptos.get(i), i + 1);

                    if(crypto != null) {
                        CryptoRepository.getInstance().add(crypto);
                    }
                }

                if(CryptoRepository.getInstance().getAll().size() > 0) {
                    new Thread(() -> {
                        FileService.writeCryptoToFile(CryptoRepository.getInstance().getAll());
                    }).start();
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (CryptoRepository.getInstance().getAll().size() <= 0) {
            List<CmcCrypto> cryptosFromFile = FileService.readCryptoFromFile();
            CryptoRepository.getInstance().set(cryptosFromFile);
        }
    }

    public static void fetchPrices() {
        System.out.println("Updating Prices");

        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("start", "1"));
        parameters.add(new BasicNameValuePair("limit", "200"));
        parameters.add(new BasicNameValuePair("convert", "USD"));

        try {
            String result = call(uri, parameters);

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();

            Map<String, Object> json = gson.fromJson(result, type);

            List<Map<String, Object>> prices = (List<Map<String, Object>>)json.getOrDefault("data", new ArrayList<>());

            if(prices.size() > 0) {
                CryptoPriceRepository.getInstance().clear();
                prices.forEach(c -> {
                    CmcCryptoPrice cryptoPrice = CmcCryptoPrice.fromJSON(c);

                    if(cryptoPrice != null) {
                        CryptoPriceRepository.getInstance().add(cryptoPrice);
                    }
                });

                if(CryptoPriceRepository.getInstance().getAll().size() > 0) {
                    new Thread(() -> {
                        FileService.writeCryptoPricesToFile(CryptoPriceRepository.getInstance().getAll());
                    }).start();
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (CryptoPriceRepository.getInstance().getAll().size() <= 0) {
            List<CmcCryptoPrice> pricesFromFile = FileService.readCryptoPricesFromFile();
            CryptoPriceRepository.getInstance().set(pricesFromFile);
        }
    }

    public static String call(String uri, List<NameValuePair> parameters)  throws URISyntaxException, IOException {
        String responseContent = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", API_KEY);

        CloseableHttpResponse response = client.execute(request);

        try {
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return responseContent;
    }
}

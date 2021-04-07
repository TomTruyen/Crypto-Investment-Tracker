package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoingeckoApiService {
    public static void fetchCryptos() {
        System.out.println("Fetching Crypto's");

        String uri = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=false&price_change_percentage=24h%2C7d%2C30d%2C1y";
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("sort", "cmc_rank"));

        try {
            String result = call(uri, parameters);

            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();

            List<Map<String, Object>> cryptos = gson.fromJson(result, type);

            if(cryptos.size() > 0) {
                CryptoRepository.getInstance().clear();
                for (Map<String, Object> stringObjectMap : cryptos) {
                    CoingeckoCrypto crypto = CoingeckoCrypto.fromJSON(stringObjectMap);

                    CryptoRepository.getInstance().add(crypto);
                }

                CryptoRepository.getInstance().sortByRank();

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
            List<CoingeckoCrypto> cryptosFromFile = FileService.readCryptoFromFile();
            CryptoRepository.getInstance().set(cryptosFromFile);
        }
    }

    public static String call(String uri, List<NameValuePair> parameters)  throws URISyntaxException, IOException {
        String responseContent = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");

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

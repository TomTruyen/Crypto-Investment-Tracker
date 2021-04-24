package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.repositories.CryptoRepository;
import be.tomtruyen.cryptotracker.utils.Utils;
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

import javax.imageio.ImageIO;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class CoingeckoApiService {
    private static Color findCryptoColor(CoingeckoCrypto crypto) {
        String imageUrl = crypto.getImage();
        imageUrl = imageUrl.replaceFirst("large", "small");

        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl));

            int height = image.getHeight();
            int width = image.getWidth();

            Map<Color, Integer> color2counter = new HashMap<>();

            for(int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    Color pixel = new Color(image.getRGB(x, y));

                    int whiteIgnoreRange = 25; //Range of RGB that will be ignored (from white side)

                    if(!((pixel.getBlue() == 255 && pixel.getGreen() == 255 && pixel.getRed() == 255) || (pixel.getBlue() == 0 && pixel.getGreen() == 0 && pixel.getRed() == 0))) {
                        if(!(pixel.getBlue() >= (255 - whiteIgnoreRange) && pixel.getGreen() >= (255 - whiteIgnoreRange) && pixel.getRed() >= (255 - whiteIgnoreRange))) {
                            int count = color2counter.getOrDefault(pixel, 0);

                            color2counter.put(pixel, count + 1);
                        }
                    }
                }
            }

            Map.Entry<Color, Integer> cryptoColor = color2counter.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);

            Color color = new Color(0, 0, 0);
            if(cryptoColor != null && cryptoColor.getKey() != null) {
                color = cryptoColor.getKey();
            }

            return color;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void fetchCryptos() {
        CryptoRepository cryptoRepository = CryptoRepository.getInstance();

        int fetchCount = cryptoRepository.getCount();

        System.out.printf("Getting cryptos... [#%d - %s]%n", fetchCount, Utils.getDateTime());

        boolean getColors = false;
        if(fetchCount == 1) {
            System.out.println("Getting colors...");
            getColors = true;
        }

        cryptoRepository.incrementCount();

        String uri = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=false&price_change_percentage=24h%2C7d%2C30d%2C1y";
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("sort", "cmc_rank"));

        try {
            String result = call(uri, parameters);

            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();

            List<Map<String, Object>> cryptos = gson.fromJson(result, type);

            if(cryptos.size() > 0) {
                for (Map<String, Object> stringObjectMap : cryptos) {
                    CoingeckoCrypto crypto = CoingeckoCrypto.fromJSON(stringObjectMap);

                    if(cryptoRepository.find(crypto.getSymbol()) == null) {
                        // Find the image color and set it
                        Color color = findCryptoColor(crypto);
                        crypto.setColor(color);
                    }

                    cryptoRepository.add(crypto);
                }

                cryptoRepository.sortByRank();

                if(cryptoRepository.getAll().size() > 0) {
                    // Check for price Alerts
                    new Thread(() -> {
                        try {
                            DatabaseService databaseService = new DatabaseService();

                            databaseService.checkPriceAlerts(cryptoRepository);
                        } catch (SQLException ignored) {}
                    }).start();

                    // Write to file
                    new Thread(() -> {
                        FileService.writeCryptoToFile(cryptoRepository.getAll());
                    }).start();
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (cryptoRepository.getAll().size() <= 0) {
            List<CoingeckoCrypto> cryptosFromFile = FileService.readCryptoFromFile();
            cryptoRepository.set(cryptosFromFile);
        }

        if(getColors) System.out.println("Colors saved");
        System.out.println("Cryptos saved");
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

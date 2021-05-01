package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    public static void writeCryptoToFile(List<CoingeckoCrypto> list) {
        if(!list.isEmpty()) {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;

            try {
                File file = new File("src/main/resources/cryptos.data");
                if(System.getProperty("os.name").equalsIgnoreCase("linux")) {
                    file = new File("/home/pi/Desktop/cryptos.data");
                }


                file.createNewFile();

                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);

                for (Object item : list) {
                    objectOutputStream.writeObject(item);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                closeFileStreams(fileOutputStream, objectOutputStream);
            }
        }
    }

    private static void closeFileStreams(FileOutputStream fileOutputStream, ObjectOutputStream objectOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<CoingeckoCrypto> readCryptoFromFile() {
        List<CoingeckoCrypto> cryptos = new ArrayList<CoingeckoCrypto>();

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            File file = new File("src/main/resources/cryptos.data");
            if(System.getProperty("os.name").equalsIgnoreCase("linux")) {
                file = new File("/home/pi/Desktop/cryptos.data");
            }

            if(file.exists()) {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);

                boolean hasData = true;
                while (hasData) {
                    CoingeckoCrypto crypto = null;
                    try {
                        crypto = (CoingeckoCrypto) objectInputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (crypto != null) {
                        cryptos.add(crypto);
                    } else {
                        hasData = false;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return cryptos;
    }
}

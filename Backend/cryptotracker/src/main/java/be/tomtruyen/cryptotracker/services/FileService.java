package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CmcCrypto;
import be.tomtruyen.cryptotracker.domain.CmcCryptoPrice;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public static void writeCryptoToFile(List<CmcCrypto> list) {
        if(!list.isEmpty()) {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;

            try {
                File file = new File("src/main/resources/cryptos.data");

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

    public static void writeCryptoPricesToFile(List<CmcCryptoPrice> list) {
        if(!list.isEmpty()) {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;

            try {
                File file = new File("src/main/resources/cryptoPrices.data");

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

    public static List<CmcCrypto> readCryptoFromFile() {
        List<CmcCrypto> cryptos = new ArrayList<CmcCrypto>();

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            File file = new File("src/main/resources/cryptos.data");

            if(file.exists()) {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);

                boolean hasData = true;
                while (hasData) {
                    CmcCrypto crypto = null;
                    try {
                        crypto = (CmcCrypto) objectInputStream.readObject();
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

    public static List<CmcCryptoPrice> readCryptoPricesFromFile() {
        List<CmcCryptoPrice> cryptos = new ArrayList<CmcCryptoPrice>();

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            File file = new File("src/main/resources/cryptoPrices.data");

            if(file.exists()) {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);

                boolean hasData = true;
                while (hasData) {
                    CmcCryptoPrice crypto = null;
                    try {
                        crypto = (CmcCryptoPrice) objectInputStream.readObject();
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

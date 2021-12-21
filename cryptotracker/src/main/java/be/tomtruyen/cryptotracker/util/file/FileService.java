package be.tomtruyen.cryptotracker.util.file;

import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final String debugFile = "src/main/resources/cryptos.data";
    private final String prodFile = System.getProperty("user.dir") + "/cryptos.data";

    public void writeCryptoToFile(List<CoingeckoCrypto> list) {
        if(!list.isEmpty()) {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;

            try {
                File file = new File(debugFile);
                if(System.getProperty("os.name").equalsIgnoreCase("linux")) {
                    file = new File(prodFile);
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

    private void closeFileStreams(FileOutputStream fileOutputStream, ObjectOutputStream objectOutputStream) {
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

    public List<CoingeckoCrypto> readCryptoFromFile() {
        List<CoingeckoCrypto> cryptos = new ArrayList<>();

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try {
            File file = new File(debugFile);
            if(System.getProperty("os.name").equalsIgnoreCase("linux")) {
                file = new File(prodFile);
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

package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();

            InputStream inputStream =
                    ConfigReader.class.getClassLoader()
                            .getResourceAsStream("config.properties");
            if (inputStream == null) {
                throw new RuntimeException("config.properties bulunamadı!");
            }

            properties.load(inputStream);

            inputStream.close();
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("config.properties okunurken hata oluştu!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

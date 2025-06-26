package utils;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class BrokenLinkVerifier {
	private static final Logger logger = LogManager.getLogger(BrokenLinkVerifier.class);

    // Method to verify broken links
    public static void verifyBrokenLinks(List<WebElement> elements, String attribute) {
        int brokenLinkCount = 0;
        logger.info("Total links found: {}", elements.size());

        for (WebElement element : elements) {
            String urlString = element.getDomAttribute(attribute);
            if (urlString == null || urlString.isEmpty()) {
                logger.warn("Empty or null URL found.");
                continue;
            }

            try {
                URL url = URI.create(urlString).toURL();
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                httpConn.setConnectTimeout(5000);
                httpConn.connect();

                int responseCode = httpConn.getResponseCode();
                String responseMessage = httpConn.getResponseMessage();

                if (responseCode != 200 && responseCode != 429) {
                    logger.error("Broken link: {} - {} {}", urlString, responseCode, responseMessage);
                    brokenLinkCount++;
                }
            } catch (Exception e) {
                logger.error("Exception while checking URL: {} - {}", urlString, e.getMessage());
            }
        }

        logger.error("Total broken links found: {}", brokenLinkCount);
    }

    // Method to verify broken images
    public static void verifyBrokenImages(List<WebElement> elements, String attribute) {
        int brokenImageCount = 0;
        logger.info("Total images found: {}", elements.size());

        for (WebElement image : elements) {
            String src = image.getDomAttribute(attribute);
            if (src == null || src.isEmpty()) {
                logger.warn("Empty or null image source found.");
                continue;
            }

            try {
                URL url = URI.create(src).toURL();
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                httpConn.setConnectTimeout(5000);
                httpConn.connect();

                int responseCode = httpConn.getResponseCode();
                String responseMessage = httpConn.getResponseMessage();

                if (responseCode != 200 && responseCode != 429) {
                    logger.error("Broken image: {} - {} {}", src, responseCode, responseMessage);
                    brokenImageCount++;
                }
            } catch (Exception e) {
                logger.error("Exception while checking image: {} - {}", src, e.getMessage());
            }
        }

        logger.error("Total broken images found: {}", brokenImageCount);
    }
}

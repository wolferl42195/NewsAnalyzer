package newsdownloader;

import newsapi.NewsApiException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public abstract class Downloader {

    public static final String HTML_EXTENTION = ".html";
    public static final String DIRECTORY_DOWNLOAD = "./download/";

    public abstract int process(List<String> urls) throws NewsDownloaderException;

    public String saveUrl2File(String urlString) throws NewsDownloaderException {
        InputStream is = null;
        OutputStream os = null;
        String fileName = "";

        try {
            URL url4download = new URL(urlString);
            is = url4download.openStream();

            fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
            if (fileName.isEmpty()) {
                fileName = url4download.getHost();
            }
            fileName = fileName.replaceAll("[^A-Za-z\\d()\\[\\]\\\\\\.]|(s|.html)", "");        //filter illegal characters for the filename and for the .html ending
            os = new FileOutputStream(DIRECTORY_DOWNLOAD + fileName + HTML_EXTENTION);                    //add .html ending so every name is the same (avoiding .html.html)

            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw new NewsDownloaderException("A Problem with the Downloader has occurred - "+ e.getMessage());
        } finally {
            try {
                Objects.requireNonNull(is).close();
                Objects.requireNonNull(os).close();
            } catch (IOException e) {
                throw new NewsDownloaderException("A Problem with the Downloader has occurred - "+ e.getMessage());
            }
        }
        return fileName;
    }
}

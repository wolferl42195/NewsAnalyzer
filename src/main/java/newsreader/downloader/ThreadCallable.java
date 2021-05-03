package newsreader.downloader;


import java.util.concurrent.Callable;

public class ThreadCallable implements Callable {

    @Override
    public String call() throws Exception {
        return dwlnd.saveUrl2File(url);
    }
    private Downloader dwlnd;
    private String url;

    public ThreadCallable(Downloader dwnld, String url) {
        this.dwlnd = dwnld;
        this.url = url;
    }
}

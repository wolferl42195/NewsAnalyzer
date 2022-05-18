package newsdownloader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelDownloader extends Downloader{
    int count = 0;
    @Override
    public int process(List<String> urls) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                for (String url : urls) {
                    String fileName = saveUrl2File(url);
                    if(fileName != null) {
                        count++;
                    }
                }
            }
        });
        executorService.shutdown();
        return count;
    }
}

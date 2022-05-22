package newsreader.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {
    @Override
    public int process(List<String> urls) {
        long timer = System.currentTimeMillis();
        int count = 0;
        List <Future<String>> futures = new ArrayList<>();

        for (String url : urls) {
            ThreadCallable urlP = new ThreadCallable(this, url);
            ExecutorService executor = Executors.newCachedThreadPool();
            futures.add(executor.submit(urlP));
        }

        for(Future<String> future : futures) {
            try {
                String fileName = future.get();
                if (fileName != null)
                    count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Timer - Parallel: " + (System.currentTimeMillis() - timer));
        return count;
    }
}

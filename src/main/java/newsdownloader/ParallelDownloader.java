package newsdownloader;

import newsapi.NewsApiException;

import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class ParallelDownloader extends Downloader{

    int count;
    @Override
    public int process(List<String> urls) throws  NewsDownloaderException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        class Task implements Callable<Integer>{
            @Override
            public Integer call() throws Exception {
                int count = 0;
                for (String url : urls) {
                    String fileName = saveUrl2File(url);
                    if(fileName != null) {
                        count++;
                    }
                }
                return count;
            }
        }

        Future<Integer> future = executorService.submit(new Task());

        try{
            System.out.print("loading");
            while(!(future.isDone())){
                System.out.print(".");
                sleep(690);
            }
            count = future.get();
        }catch(InterruptedException | ExecutionException e){
            throw new NewsDownloaderException("A Problem with the Parallel Downloader has occurred - "+ e.getMessage());
        }

        executorService.shutdown();
        return count;
    }
}

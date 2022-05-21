package newsdownloader;

import newsapi.NewsApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class ParallelDownloader extends Downloader{

    int count;
    @Override
    public int process(List<String> urls) throws  NewsDownloaderException {

        class Task implements Callable<Integer>{
            String url;
            public Task(String url) {
                this.url = url;
            }

            @Override
            public Integer call() throws NewsDownloaderException {
                String fileName = saveUrl2File(url);
                if (fileName != null) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }



        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future> allFutures = new ArrayList<>();
        for(String url : urls){
            Future<Integer> future  = executorService.submit(new Task(url));
            allFutures.add(future);
        }



        //Future<Integer> future = executorService.submit(new Task());



        try{
            boolean finished = false;

            while(!finished){
                finished = true;
                for(Future<Integer> future : allFutures){
                    if(!future.isDone()){
                        finished = false;
                    }
                }
            }

            for(Future<Integer> future : allFutures){
                count += future.get();
            }

            count = allFutures.size();

        }catch(InterruptedException | ExecutionException | CancellationException e){
            throw new NewsDownloaderException("A Problem with the Parallel Downloader has occurred - "+ e.getMessage());
        }


        /*try{
            System.out.print("loading");
            while(!(future.isDone())){
                System.out.print(".");
                sleep(690);
            }
            count = future.get();
        }catch(InterruptedException | ExecutionException | CancellationException e){
            throw new NewsDownloaderException("A Problem with the Parallel Downloader has occurred - "+ e.getMessage());
        }*/

        executorService.shutdown();
        return count;
    }
}

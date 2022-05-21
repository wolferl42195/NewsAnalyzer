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



        try{
            boolean finished = false;

            System.out.print("Loading");
            while(!finished){

                finished = true;
                for(Future<Integer> future : allFutures){       //check if all threads are done
                    if(!future.isDone()){
                        finished = false;           //if one or more threads are unfinished, set to false;
                    }
                }

                System.out.print(".");
                sleep(690);
            }

            for(Future<Integer> future : allFutures){       //get the results of saveUrl2File (successfully downloaded)
                count += future.get();
            }


        }catch(InterruptedException | ExecutionException | CancellationException e){
            throw new NewsDownloaderException("A Problem with the Parallel Downloader has occurred - "+ e.getMessage());
        }



        executorService.shutdown();
        return count;
    }
}

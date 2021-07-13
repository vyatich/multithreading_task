import model.CalculateResult;
import model.DownloadResult;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Calendar start = Calendar.getInstance();
        int downloadResults = 3000;
        AtomicInteger finishCounter = new AtomicInteger(0);

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<DownloadResult>> completableDownloadResults = new ArrayList<>();

        ExecutorService calculateExService = Executors.newFixedThreadPool(8);
        List<Future<CalculateResult>> calc = new ArrayList<>();

        for (int i = 0; i < downloadResults; i++) {
            CompletableFuture<DownloadResult> completableFuture = CompletableFuture.supplyAsync(new Download(i), executorService);
            completableDownloadResults.add(completableFuture);
        }

        for (CompletableFuture<DownloadResult> resultFuture : completableDownloadResults) {
            resultFuture.thenApply(result -> calc.add(calculateExService.submit(new Calculate(result))));
        }
        executorService.shutdown();
        calculateExService.shutdown();

        for (Future<CalculateResult> calculateResultFuture : calc) {
            if (calculateResultFuture.get().found) {
                finishCounter.incrementAndGet();
            }
        }

        System.out.println("Total success checks: " + finishCounter);

        Calendar stop = Calendar.getInstance();

        System.out.println("Total time: " + (stop.getTimeInMillis() - start.getTimeInMillis()) + " ms");

    }
}

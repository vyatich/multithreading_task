import model.CalculateResult;
import model.DownloadResult;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Download d = new Download();
        Calculate c = new Calculate();
        int finishCounter = 0;
        for (int i = 0; i < 30000; i++) {
            DownloadResult downloadResult = d.downloadNext(i);
            CalculateResult calculateResult = c.calculate(downloadResult);
            if (calculateResult.found) {
                finishCounter++;
            }
        }

        System.out.println("Total success checks: " + finishCounter);
    }
}

import model.CalculateResult;
import model.DownloadResult;

import java.util.Random;

public class Calculate {
    public CalculateResult calculate(DownloadResult download) {
        Random r = new Random();
        CalculateResult result = new CalculateResult();
        result.id = download.id;
        result.data = download.data;
        for (int i = 0; i < 200000; i++) {
            int check = r.nextInt(100000);
            if (check == download.data) {
                result.found = true;
                return result;
            }
        }
        return result;
    }
}

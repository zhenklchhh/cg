public class OtsuThreshold {
    public static int otsuThreshold(int[] histogram) {
        int totalPixels = 0;
        for (int i = 0; i < histogram.length; i++) {
            totalPixels += histogram[i];
        }

        float[] weightedSum = new float[histogram.length];
        weightedSum[0] = histogram[0];
        for (int i = 1; i < histogram.length; i++) {
            weightedSum[i] = weightedSum[i - 1] + histogram[i];
        }

        float sumB = 0;
        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < histogram.length; i++) {
            float weightB = weightedSum[i] / totalPixels;
            float weightF = (totalPixels - weightedSum[i]) / totalPixels;

            if (weightB == 0 || weightF == 0) continue; //Avoid division by zero

            float meanB = 0;
            for (int j = 0; j <= i; j++) {
                meanB += j * histogram[j];
            }
            meanB /= weightedSum[i];

            float meanF = 0;
            for (int j = i + 1; j < histogram.length; j++) {
                meanF += j * histogram[j];
            }
            meanF /= (totalPixels - weightedSum[i]);


            float varBetween = weightB * weightF * (meanB - meanF) * (meanB - meanF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }
}

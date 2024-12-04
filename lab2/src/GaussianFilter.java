import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class GaussianFilter {
    public static BufferedImage applyGaussianFilter(BufferedImage image, int kernelSize, double sigma) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, image.getType());
        WritableRaster raster = filteredImage.getRaster();

        double[][] kernel = createGaussianKernel(kernelSize, sigma);
        int kernelRadius = kernelSize / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double sumR = 0, sumG = 0, sumB = 0;
                double kernelSum = 0;

                for (int ky = -kernelRadius; ky <= kernelRadius; ky++) {
                    for (int kx = -kernelRadius; kx <= kernelRadius; kx++) {
                        int currentX = x + kx;
                        int currentY = y + ky;

                        currentX = Math.max(0, Math.min(currentX, width - 1));
                        currentY = Math.max(0, Math.min(currentY, height - 1));


                        int pixel = image.getRGB(currentX, currentY);
                        int r = (pixel >> 16) & 0xFF;
                        int g = (pixel >> 8) & 0xFF;
                        int b = pixel & 0xFF;

                        sumR += r * kernel[ky + kernelRadius][kx + kernelRadius];
                        sumG += g * kernel[ky + kernelRadius][kx + kernelRadius];
                        sumB += b * kernel[ky + kernelRadius][kx + kernelRadius];
                        kernelSum += kernel[ky + kernelRadius][kx + kernelRadius];
                    }
                }

                int filteredR = (int) Math.round(sumR / kernelSum);
                int filteredG = (int) Math.round(sumG / kernelSum);
                int filteredB = (int) Math.round(sumB / kernelSum);

                filteredR = Math.max(0, Math.min(filteredR, 255));
                filteredG = Math.max(0, Math.min(filteredG, 255));
                filteredB = Math.max(0, Math.min(filteredB, 255));


                int filteredPixel = (filteredR << 16) | (filteredG << 8) | filteredB;
                raster.setSample(x, y, 0, filteredR);
                raster.setSample(x, y, 1, filteredG);
                raster.setSample(x, y, 2, filteredB);

            }
        }
        return filteredImage;
    }


    public static double[][] createGaussianKernel(int kernelSize, double sigma) {
        double[][] kernel = new double[kernelSize][kernelSize];
        double sum = 0;
        int center = kernelSize / 2;

        for (int y = 0; y < kernelSize; y++) {
            for (int x = 0; x < kernelSize; x++) {
                double distance = Math.pow(x - center, 2) + Math.pow(y - center, 2);
                kernel[y][x] = Math.exp(-distance / (2 * sigma * sigma));
                sum += kernel[y][x];
            }
        }

        for (int y = 0; y < kernelSize; y++) {
            for (int x = 0; x < kernelSize; x++) {
                kernel[y][x] /= sum;
            }
        }
        return kernel;
    }
}

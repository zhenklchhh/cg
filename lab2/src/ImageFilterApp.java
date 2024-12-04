import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFilterApp extends JFrame {

    private JLabel imageLabel;
    private BufferedImage currentImage;
    private JButton loadButton;
    private JButton gaussianButton;
    private JButton otsuButton;
    private JButton simpleThresholdButton;
    private JTextField thresholdValueField;

    public ImageFilterApp() {
        setTitle("Image Filter Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        loadButton = new JButton("Load Image");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });
        buttonPanel.add(loadButton);

        gaussianButton = new JButton("Apply Gaussian Filter");
        gaussianButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyGaussianFilter();
            }
        });
        gaussianButton.setEnabled(false);
        buttonPanel.add(gaussianButton);

        otsuButton = new JButton("Apply Otsu Threshold");
        otsuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyOtsuThreshold();
            }
        });
        otsuButton.setEnabled(false);
        buttonPanel.add(otsuButton);

        simpleThresholdButton = new JButton("Apply Simple Threshold");
        simpleThresholdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySimpleThreshold();
            }
        });
        simpleThresholdButton.setEnabled(false);
        buttonPanel.add(simpleThresholdButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void applySimpleThreshold() {
        if (currentImage != null) {
            try {
                int threshold = Integer.parseInt(thresholdValueField.getText());
                if (threshold < 0 || threshold > 255) {
                    throw new NumberFormatException("Threshold value must be between 0 and 255.");
                }
                BufferedImage grayImage = convertImageToGray(currentImage);
                BufferedImage thresholdedImage = applyThreshold(grayImage, threshold);
                imageLabel.setIcon(new ImageIcon(thresholdedImage));
                currentImage = thresholdedImage; //Update currentImage

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid threshold value: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                currentImage = ImageIO.read(selectedFile);
                imageLabel.setIcon(new ImageIcon(currentImage));
                gaussianButton.setEnabled(true);
                otsuButton.setEnabled(true);
                simpleThresholdButton.setEnabled(true);
                pack();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void applyGaussianFilter() {
        if (currentImage != null) {
            BufferedImage filteredImage = GaussianFilter.applyGaussianFilter(currentImage, 5, 1.0); // Adjust kernel size and sigma as needed
            imageLabel.setIcon(new ImageIcon(filteredImage));
            currentImage = filteredImage;
        }
    }


    private void applyOtsuThreshold() {
        if (currentImage != null) {
            BufferedImage grayImage = convertImageToGray(currentImage);
            int[] histogram = calculateHistogram(grayImage);
            int threshold = OtsuThreshold.otsuThreshold(histogram);
            BufferedImage thresholdedImage = applyThreshold(grayImage,threshold);
            imageLabel.setIcon(new ImageIcon(thresholdedImage));
            currentImage = thresholdedImage;

        }
    }


    private BufferedImage convertImageToGray(BufferedImage img){
        BufferedImage grayImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return grayImage;
    }

    private int[] calculateHistogram(BufferedImage img){
        int[] histogram = new int[256];
        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                int pixel = img.getRGB(i,j);
                int r = (pixel >> 16) & 0xff;
                histogram[r]++;
            }
        }
        return histogram;
    }


    private BufferedImage applyThreshold(BufferedImage img, int threshold){
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = img.getRGB(x, y);
                int red = (pixel >> 16) & 0xFF;
                thresholdedImage.setRGB(x, y, (red > threshold) ? 0xFFFFFF : 0x000000);
            }
        }
        return thresholdedImage;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageFilterApp app = new ImageFilterApp();
            app.setVisible(true);
        });
    }
}
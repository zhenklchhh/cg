package logic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class Main implements ChangeListener, ActionListener {
    private JFrame jf = null;

    private JPanel image, compImage;
    private JLabel imageLabel, compImageLabel;

    private JSlider RSlider = new JSlider(JSlider.VERTICAL, 0, 25500, 0);
    private JSlider GSlider = new JSlider(JSlider.VERTICAL, 0, 25500, 25500);
    private JSlider BSlider = new JSlider(JSlider.VERTICAL, 0, 25500, 0);
    private JSlider CSlider = new JSlider(JSlider.VERTICAL, 0, 10000, 10000);
    private JSlider MSlider = new JSlider(JSlider.VERTICAL, 0, 10000, 0);
    private JSlider YSlider = new JSlider(JSlider.VERTICAL, 0, 10000, 10000);
    private JSlider KSlider = new JSlider(JSlider.VERTICAL, 0, 10000, 0);
    private JSlider HSlider = new JSlider(JSlider.VERTICAL, 0, 35999, 12000);
    private JSlider LSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
    private JSlider SSlider = new JSlider(JSlider.VERTICAL, 0, 100, 100);/
    private JButton inputrgb = new JButton("RGB");
    private JTextField rfield = new JTextField();
    private JTextField gfield = new JTextField();
    private JTextField bfield = new JTextField();
    private JButton inputcmyk = new JButton("CMYK");
    private JTextField cfield = new JTextField();
    private JTextField mfield = new JTextField();
    private JTextField yfield = new JTextField();
    private JTextField kfield = new JTextField();
    private JButton inputHLS = new JButton("HLS");
    private JTextField hfield = new JTextField();
    private JTextField lfield = new JTextField();
    private JTextField sfield = new JTextField();
    private JButton chooseColorButton = new JButton("Выбрать цвет");

    public static void main(String[] args) {
        Main a4 = new Main();
    }

    public void init() {
        Main a4 = new Main();
    }

    public Main() {
        jf = new JFrame("Color");
        jf.setSize(800, 800);
        jf.setResizable(false);

        Color rgb = new Color(0, 255, 0);
        CMYK cmyk = ConvertMethods.convertRGBtoCMYK(rgb);
        HLS hls = ConvertMethods.convertRGBtoHLS(rgb);

        image = new JPanel();
        image.setSize(230, 200);
        image.setPreferredSize(new Dimension(230, 200));
        imageLabel = new JLabel("");
        imageLabel.setPreferredSize(new Dimension(230, 160));
        imageLabel.setVerticalAlignment(JLabel.TOP);
        image.add(imageLabel);

        compImage = new JPanel();
        compImage.setSize(230, 200);
        compImage.setPreferredSize(new Dimension(230, 200));
        compImageLabel = new JLabel("");
        compImageLabel.setPreferredSize(new Dimension(230, 160));
        compImageLabel.setVerticalAlignment(JLabel.TOP);
        compImage.add(compImageLabel);

        Box sliders = new Box(BoxLayout.X_AXIS);

        fixSlider(sliders, RSlider, 'R');
        fixSlider(sliders, GSlider, 'G');
        fixSlider(sliders, BSlider, 'B');

        sliders.add(Box.createRigidArea(new Dimension(20, 0)));

        fixSlider(sliders, CSlider, 'C');
        fixSlider(sliders, MSlider, 'M');
        fixSlider(sliders, YSlider, 'Y');
        fixSlider(sliders, KSlider, 'K');

        sliders.add(Box.createRigidArea(new Dimension(20, 0)));

        fixSlider(sliders, HSlider, 'H');
        fixSlider(sliders, LSlider, 'L');
        fixSlider(sliders, SSlider, 'S');

        Box GUI = new Box(BoxLayout.X_AXIS);
        GUI.add(image);
        GUI.add(compImage);
        GUI.add(sliders);

        setSliders(rgb, hls, cmyk);

        setColorPanels(rgb, hls, cmyk);

        Box fields = createFields();
        Box finalBox = new Box(BoxLayout.Y_AXIS);
        finalBox.add(GUI);
        finalBox.add(fields);

        chooseColorButton.addActionListener(this);
        finalBox.add(chooseColorButton);

        jf.getContentPane().add(finalBox);
        jf.pack();
        jf.setVisible(true);
    }

    public Box createFields() {
        Box fields = new Box(BoxLayout.X_AXIS);

        Box rgbBox = new Box(BoxLayout.Y_AXIS);
        rgbBox.setBorder(BorderFactory.createTitledBorder("RGB Inputs"));
        addLabeledField(rgbBox, "R", rfield);
        addLabeledField(rgbBox, "G", gfield);
        addLabeledField(rgbBox, "B", bfield);
        inputrgb.addActionListener(this);
        rgbBox.add(inputrgb);

        Box cmykBox = new Box(BoxLayout.Y_AXIS);
        cmykBox.setBorder(BorderFactory.createTitledBorder("CMYK Inputs"));
        addLabeledField(cmykBox, "C", cfield);
        addLabeledField(cmykBox, "M", mfield);
        addLabeledField(cmykBox, "Y", yfield);
        addLabeledField(cmykBox, "K", kfield);
        inputcmyk.addActionListener(this);
        cmykBox.add(inputcmyk);

        Box hlsBox = new Box(BoxLayout.Y_AXIS);
        hlsBox.setBorder(BorderFactory.createTitledBorder("HLS Inputs"));
        addLabeledField(hlsBox, "Hue", hfield);
        addLabeledField(hlsBox, "Light", lfield);
        addLabeledField(hlsBox, "Sat", sfield);
        inputHLS.addActionListener(this);
        hlsBox.add(inputHLS);

        fields.add(rgbBox);
        fields.add(cmykBox);
        fields.add(hlsBox);

        return fields;
    }

    private void addLabeledField(Box box, String labelText, JTextField textField) {
        textField.setPreferredSize(new Dimension(80, 25));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        box.add(panel);
    }

    public void fixSlider(Box sliders, JSlider s, char c) {
        Box b = new Box(BoxLayout.Y_AXIS);
        b.add(new JLabel("" + c));
        b.add(s);
        sliders.add(b);
        s.addChangeListener(this);
    }

    public void fixListeners(boolean b) {
        if (b) {
            RSlider.addChangeListener(this);
            GSlider.addChangeListener(this);
            BSlider.addChangeListener(this);
            HSlider.addChangeListener(this);
            LSlider.addChangeListener(this);
            SSlider.addChangeListener(this);
            CSlider.addChangeListener(this);
            MSlider.addChangeListener(this);
            YSlider.addChangeListener(this);
            KSlider.addChangeListener(this);

        } else {
            RSlider.removeChangeListener(this);
            GSlider.removeChangeListener(this);
            BSlider.removeChangeListener(this);
            CSlider.removeChangeListener(this);
            MSlider.removeChangeListener(this);
            YSlider.removeChangeListener(this);
            KSlider.removeChangeListener(this);
            HSlider.removeChangeListener(this);
            LSlider.removeChangeListener(this);
            SSlider.removeChangeListener(this);
        }
    }

    public void stateChanged(ChangeEvent e) {
        Color rgb;
        CMYK cmyk;
        HLS hls;

        fixListeners(false);

        JSlider source = (JSlider) e.getSource();

        if (source.equals(HSlider) || source.equals(LSlider) ||
                source.equals(SSlider)) {
            double H = (double) HSlider.getValue() / 100.0;
            double L = (double) LSlider.getValue() / 100.0;
            double S = (double) SSlider.getValue() / 100.0;
            hls = new HLS(H, L, S); // Changed to HLS
            rgb = ConvertMethods.convertHLStoRGB(hls);
            cmyk = ConvertMethods.convertRGBtoCMYK(rgb);
        } else if (source.equals(RSlider) || source.equals(GSlider) ||
                source.equals(BSlider)) {
            int R = (int) Math.round(RSlider.getValue() / 100.0);
            int G = (int) Math.round(GSlider.getValue() / 100.0);
            int B = (int) Math.round(BSlider.getValue() / 100.0);
            rgb = new Color(R, G, B);
            hls = ConvertMethods.convertRGBtoHLS(rgb);
            cmyk = ConvertMethods.convertRGBtoCMYK(rgb);
        } else if (source.equals(CSlider) || source.equals(MSlider) ||
                source.equals(YSlider) || source.equals(KSlider)) {
            double C = CSlider.getValue() / 100.0;
            double M = MSlider.getValue() / 100.0;
            double Y = YSlider.getValue() / 100.0;
            double K = KSlider.getValue() / 100.0;
            cmyk = new CMYK(C, M, Y, K);
            rgb = ConvertMethods.convertCMYKtoRGB(cmyk);
            hls = ConvertMethods.convertRGBtoHLS(rgb);
        } else
            return;

        setSliders(rgb, hls, cmyk);
        setColorPanels(rgb, hls, cmyk);

        fixListeners(true);
    }

    public void setSliders(Color rgb, HLS hls, CMYK cmyk) {
        RSlider.setValue(rgb.getRed() * 100);
        GSlider.setValue(rgb.getGreen() * 100);
        BSlider.setValue(rgb.getBlue() * 100);

        HSlider.setValue((int) (hls.getH() * 100.0));
        LSlider.setValue((int) (hls.getL() * 100.0));
        SSlider.setValue((int) (hls.getS() * 100.0));

        CSlider.setValue((int) (cmyk.cyan() * 100.0));
        MSlider.setValue((int) (cmyk.magenta() * 100.0));
        YSlider.setValue((int) (cmyk.yellow() * 100.0));
        KSlider.setValue((int) (cmyk.black() * 100.0));
    }

    public void setColorPanels(Color rgb, HLS hls, CMYK cmyk) {
        image.setBackground(rgb);
        Color compRgb = ConvertMethods.complementRGB(rgb);
        compImage.setBackground(compRgb);

        imageLabel.setForeground(compRgb);
        imageLabel.setText("<html> Color<br> RGB:    " + ConvertMethods.toString(rgb) +
                "<br> CMYK: " + ConvertMethods.toString(cmyk) +
                "<br> HSV:    " + ConvertMethods.toString(hls) + "<br><br>" +
                " R,G,B sliders in: 0..255<br>" +
                " C,M,Y,K sliders: 0 to 100%<br>" +
                " H slider: 0 <= H < 360 degrees<br>" +
                " L,S sliders: 0 <= S,V <= 1" + "</html>");
        compImageLabel.setForeground(rgb);
        HLS compHsv = ConvertMethods.convertRGBtoHLS(compRgb);
        CMYK compCmyk = ConvertMethods.convertRGBtoCMYK(compRgb);
        compImageLabel.setText("<html> Complementary Color<br> RGB:    " +
                ConvertMethods.toString(compRgb) +
                "<br> CMYK: " + ConvertMethods.toString(compCmyk) +
                "<br> HSV:    " + ConvertMethods.toString(compHsv) + "<br><br>" +
                " R,G,B sliders in: 0..255<br>" +
                " C,M,Y,K sliders: 0 to 100%<br>" +
                " H slider: 0 <= H < 360 degrees<br>" +
                " L,S sliders: 0 <= S,V <= 1" +
                "</html>");
        jf.setTitle("Color RGB: " + ConvertMethods.toString(rgb));
    }

    public void actionPerformed(ActionEvent e) {
        fixListeners(false);

        Color rgb;
        HLS hls;
        CMYK cmyk;
        if (e.getSource() == inputrgb) {
            int r = getInt(rfield.getText());
            int g = getInt(gfield.getText());
            int b = getInt(bfield.getText());

            rfield.setText("" + r);
            gfield.setText("" + g);
            bfield.setText("" + b);

            rgb = new Color(r, g, b);
            hls = ConvertMethods.convertRGBtoHLS(rgb);
            cmyk = ConvertMethods.convertRGBtoCMYK(rgb);

        } else if (e.getSource() == inputcmyk) {
            double c = getDouble100(cfield.getText());
            double m = getDouble100(mfield.getText());
            double y = getDouble100(yfield.getText());
            double k = getDouble100(kfield.getText());

            cfield.setText(ConvertMethods.roundTo5(c));
            mfield.setText(ConvertMethods.roundTo5(m));
            yfield.setText(ConvertMethods.roundTo5(y));
            kfield.setText(ConvertMethods.roundTo5(k));

            cmyk = new CMYK(c, m, y, k);
            rgb = ConvertMethods.convertCMYKtoRGB(cmyk);
            hls = ConvertMethods.convertRGBtoHLS(rgb);

        } else if (e.getSource() == inputHLS) {
            double h = getDouble360(hfield.getText());
            double l = getDouble(lfield.getText());
            double s = getDouble(sfield.getText());
            hls = new HLS(h, l, s);
            rgb = ConvertMethods.convertHLStoRGB(hls);
            cmyk = ConvertMethods.convertRGBtoCMYK(rgb);

            hfield.setText(ConvertMethods.roundTo5(h));
            lfield.setText(ConvertMethods.roundTo5(l));
            sfield.setText(ConvertMethods.roundTo5(s));
        } else if (e.getSource() == chooseColorButton) {
            rgb = JColorChooser.showDialog(jf, "Выберите цвет", Color.white);
            if (rgb != null) {
                hls = ConvertMethods.convertRGBtoHLS(rgb);
                cmyk = ConvertMethods.convertRGBtoCMYK(rgb);

                rfield.setText(String.valueOf(rgb.getRed()));
                gfield.setText(String.valueOf(rgb.getGreen()));
                bfield.setText(String.valueOf(rgb.getBlue()));

                cfield.setText(ConvertMethods.roundTo5(cmyk.cyan()));
                mfield.setText(ConvertMethods.roundTo5(cmyk.magenta()));
                yfield.setText(ConvertMethods.roundTo5(cmyk.yellow()));
                kfield.setText(ConvertMethods.roundTo5(cmyk.black()));

                hfield.setText(ConvertMethods.roundTo5(hls.getH()));
                lfield.setText(ConvertMethods.roundTo5(hls.getL()));
                sfield.setText(ConvertMethods.roundTo5(hls.getS()));
            } else {
                return;
            }
        } else {
            return;
        }

        setColorPanels(rgb, hls, cmyk);
        setSliders(rgb, hls, cmyk);

        fixListeners(true);
    }

    public static int getInt(String s) {
        try {
            int i = Integer.parseInt(s.trim());
            return Math.max(0, Math.min(255, i));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double getDouble(String s) {
        try {
            double d = Double.parseDouble(s.trim());
            return Math.max(0.0, Math.min(1.0, d));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static double getDouble100(String s) {
        try {
            double d = Double.parseDouble(s.trim());
            return Math.max(0.0, Math.min(100.0, d));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static double getDouble360(String s) {
        try {
            double d = Double.parseDouble(s.trim());
            return Math.max(0.0, Math.min(359.9, d));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
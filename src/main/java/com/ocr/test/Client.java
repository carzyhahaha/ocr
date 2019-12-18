package com.ocr.test;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Client {
    private static String picPath;
    private static ImageIcon icon = new ImageIcon("C:\\Users\\Xxx\\Desktop\\86b9f14cb88f49138a32f8ee6cfbd570.jpeg");
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {



        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        JFrame jf = new JFrame("文字识别");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel fileTypeSelect = new JLabel("选择图片类型:");

        String[] listData = new String[]{"手写文字", "印刷文字", "身份证", "增值税发票", "营业执照", "银行卡"};
        JComboBox<String> comboBox = new JComboBox<>(listData);
        panel.add(fileTypeSelect);
        panel.add(comboBox);

        JPanel panel1 = new JPanel();
        JLabel fileSelect = new JLabel("选择图片");
        panel1.add(fileSelect);
        JTextField filePath = new JTextField(15);
        panel1.add(filePath);
        JButton selectButton = new JButton("选择");
        JButton startButton = new JButton("识别");
        panel1.add(selectButton);
        panel1.add(startButton);



        JPanel panel2 = new JPanel();
        JTextArea textArea = new JTextArea(20, 100);
        JPanel picPanel = new JPanel();
        panel2.add(new JScrollPane(textArea));

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.showDialog(new JLabel(), "选择");
                File file = chooser.getSelectedFile();
                if (file == null) {
                    return;
                }
                filePath.setText(file.getAbsoluteFile().toString());
                picPath = file.getAbsoluteFile().toString();
                picPanel.removeAll();
                jf.repaint();
                ImageIcon image = new ImageIcon(picPath);
                picPanel.add(new JLabel(image));
                jf.revalidate();
            }
        });


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                Integer type = comboBox.getSelectedIndex();
                String filePathStr = filePath.getText();
                if (StringUtils.isBlank(filePathStr)) {
                    JOptionPane.showMessageDialog(
                            jf,
                            "请选择文件!",
                            "警告",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                switch (type) {
                    case 0 :
                        textArea.setText(HandWrite.start(filePathStr));
                        break;
                    case 1 :
                        textArea.setText(Printing.start(filePathStr));
                        break;
                    case 2 :
                        textArea.setText(IDCard.start(filePathStr));
                        break;
                    case 3 :
                        textArea.setText(Invoice.start(filePathStr));
                        break;
                    case 4 :
                        textArea.setText(BusinessLicense.start(filePathStr));
                        break;
                    case 5 :
                        textArea.setText(BankCard.start(filePathStr));
                        break;
                    default:
                        break;
                }
            }
        });

        // 创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
        Box vBox = Box.createVerticalBox();
        vBox.add(panel);
        vBox.add(panel1);
        vBox.add(panel2);
        vBox.add(picPanel);


//        JPanel mainPanel = new JPanel();
//        mainPanel.add(panel);
//        mainPanel.add(panel1);
//        mainPanel.add(panel2);
//        mainPanel.add(picPanel);

        jf.setContentPane(vBox);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);



    }

    static class PicPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            icon = new ImageIcon(picPath);
            if (icon != null) {

                g.drawImage(icon.getImage(),
                        100,
                        100,
                        icon.getImage().getWidth(null),
                        icon.getImage().getHeight(null),
                        null);
            }

        }
    }

}

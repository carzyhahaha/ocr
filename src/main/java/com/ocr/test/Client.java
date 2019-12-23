package com.ocr.test;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
        JTextField filePath = new JTextField(30);
        panel1.add(filePath);
        JButton selectButton = new JButton("选择");
        JButton startButton = new JButton("识别");
        panel1.add(selectButton);
        panel1.add(startButton);



        JPanel panel2 = new JPanel();
        JTextArea textArea = new JTextArea(20, 60);
        JPanel picPanel = new PicPanel();
        panel2.add(new JScrollPane(textArea));

        JLabel picture = new JLabel("请选择图片"){
            protected void paintComponent(Graphics g) {
                if (StringUtils.isNotBlank(picPath)) {
                    ImageIcon icon = new ImageIcon(picPath);
                    Integer width = icon.getImage().getWidth(null);
                    Integer height = icon.getImage().getHeight(null);
                    g.drawImage(icon.getImage(), 0, 0, width, height,
                            null);
                }
            }
        };
//        picPanel.add(picture);

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
                jf.repaint();
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

//         创建一个垂直盒子容器, 把上面 3 个 JPanel 串起来作为内容面板添加到窗口
//        Box vBox = Box.createVerticalBox();
//        vBox.add(panel);
//        vBox.add(panel1);
//        vBox.add(panel2);
//        vBox.add(picPanel);


        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel(layout);
        mainPanel.add(panel);
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        picPanel.setPreferredSize(new Dimension(1080, 780));
        mainPanel.add(picPanel);

        SpringLayout.Constraints labelCons = layout.getConstraints(panel);  // 从布局中获取指定组件的约束对象（如果没有，会自动创建）
        labelCons.setX(Spring.constant(5));
        labelCons.setY(Spring.constant(5));

        SpringLayout.Constraints labelCons2 = layout.getConstraints(panel1);
        labelCons2.setX(Spring.constant(5));
        labelCons2.setY(Spring.constant(30));

        SpringLayout.Constraints labelCons3 = layout.getConstraints(panel2);
        labelCons3.setX(Spring.constant(5));
        labelCons3.setY(Spring.constant(60));

        SpringLayout.Constraints labelCons4 = layout.getConstraints(picPanel);
        labelCons4.setX(Spring.constant(505));
        labelCons4.setY(Spring.constant(60));


        jf.setContentPane(mainPanel);
        jf.setSize(1600, 900);
//        jf.setLocationRelativeTo(null);
        jf.setVisible(true);



    }

    static class PicPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            icon = new ImageIcon(picPath);
            double width = 1080;
            double height = 780;
            if (icon != null) {
                int iWidth = icon.getImage().getWidth(null);
                int iHeight = icon.getImage().getHeight(null);
                double bl = 1;
                if (iWidth > iHeight) {
                    bl = width/iWidth;
                } else {
                    bl = height/iHeight;
                }

                g.drawImage(icon.getImage(),
                        0,
                        0,
                        (int) (iWidth * bl),
                        (int) (iHeight * bl),
                        null);
            }


        }
    }


}

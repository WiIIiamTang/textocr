import javax.swing.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.*;

public class Interface {
    private TextReader textReader;
    private String content;

    public Interface()
    {
        this.content = "";
        this.textReader = new TextReader();
        JFrame frame = new JFrame("TextOCR");
        JButton selector = new JButton("Select region");
        JButton saver = new JButton("Save to out.txt");
        final JTextField field = new JTextField();

        field.setBounds(59, 10, 170, 20);
        field.setEditable(false);

        selector.setBounds(65, 40, 150, 30);
        saver.setBounds(65, 75, 150, 30);

        frame.add(selector);
        frame.add(saver);
        frame.add(field);
        frame.setLayout(null);
        frame.setSize(300,150);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        selector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        field.setText("...");
                        textReader.setRegion();
                        getText();
                        toClipboard();
                        field.setText("Saved to clipboard");
                    }
                }).start();
            }
        });

        saver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        if (content.equals(""))
                        {
                            field.setText("Content is empty.");
                        }
                        else if(toFile())
                        {
                            field.setText("Saved to out.txt");
                        }
                        else
                        {
                            field.setText("Error in saving to file.");
                        }
                    }
                }).start();
            }
        });


    }

    public String getText()
    {
        this.content = textReader.ocr();

        return this.content;
    }

    public void toClipboard()
    {
        StringSelection strtext = new StringSelection(this.content);

        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(strtext, null);
    }

    public boolean toFile()
    {
        try
        {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.txt"), "utf-8"));

            writer.write(this.content);

            writer.close();
        } catch (Exception IOException)
        {
            return false;
        }
        return true;

    }

    public static void main(String[] args)
    {
        new Interface();
    }

}

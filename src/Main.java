import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends JFrame{
    private JTextField textField1;
    private JButton deleteButton;
    private JTextArea textArea1;
    private JPanel panel;
    private List<Cyklovýlet> list = new ArrayList<>();
    private int i = 1;
    private static final String SPLITTER = ",";
    private final JFileChooser jFileChooser = new JFileChooser(".");


    public static void main(String[] args) { new Main();}

    public Main(){
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("Soubor");
        JMenuItem load = new JMenuItem("Načti");
        load.addActionListener(e -> loadData());
        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.addActionListener(e -> refresh());
        deleteButton.addActionListener(e -> delete());
        jMenu.add(refresh);
        jMenu.add(load);
        jMenuBar.add(jMenu);

        setVisible(true);
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
    }

    public List<Cyklovýlet> loadData() {
        refresh();
        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Nesprávná možnost..");
            return null;
        }
        return scan(jFileChooser.getSelectedFile());
    }

    private List<Cyklovýlet> scan(File file){
        List<Cyklovýlet> list = new ArrayList<>();
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))){
            while(scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(SPLITTER);
                int cisla = Integer.parseInt(data[1]);
                LocalDate ld = LocalDate.now();
                list.add(new Cyklovýlet(data[0], cisla, ld));
                textArea1.append((i++ +") " + data[0] + " (" + cisla + " km) \n"));
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Soubor nelze načíst");
        }
        return list;
    }

    private void delete(){
        try{
            int lineNumbers = textArea1.getLineCount();
            int lineNumber = Integer.parseInt(textField1.getText());

            if (lineNumbers <= lineNumber){
                JOptionPane.showMessageDialog(null, "Řádek s číslem " + textField1.getText() + " neexistuje!");
            }
            list.remove(textField1.getText());
            deleteLine(textArea1, lineNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Musíte napsat číslo řádku, který chcete odstranit");
        }
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(jFileChooser.getSelectedFile())))){
            list.forEach(cyklovýlet -> {
                writer.println(cyklovýlet.toString());
            });
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Soubor neobsahuje žádný řádek " + textField1.getText());
        }
    }
    private void deleteLine(JTextArea textArea1, int lineNumber) {
        String texts = textArea1.getText();
        String[] lines = texts.split("\n");

        if (lineNumber >= 1 && lineNumber <= lines.length) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < lines.length; i++) {
                if (i != lineNumber - 1) {
                    builder.append(lines[i]).append("\n");
                }
            }
            textArea1.setText(builder.toString());
        }
    }
    private void refresh(){
        textArea1.setText("");
        textField1.setText("");
    }
}


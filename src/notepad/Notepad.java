package notepad;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;

public class Notepad extends JFrame implements ActionListener {

    private JTextArea area ;
    private JScrollPane scpane ;
    String text = "" ;
    public Notepad() {
        super("Notepad");//In jFrame there is method setTitle which inheriating here
        setSize(1950, 1050);//method avaailable

        setLayout(new BorderLayout());//layout available in jFrame

        JMenuBar menuBar = new JMenuBar(); //menubar
//------------------FIle->>new ,open,save,print,exist-----------------------------------------------------------//
        JMenu file = new JMenu("File"); //file menu

        JMenuItem newdoc = new JMenuItem("New");
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);

        JMenu edit = new JMenu("Edit");
        JMenuItem copy = new JMenuItem("Copy");

        copy.addActionListener(this);
        JMenuItem paste = new JMenuItem("Paste");

        paste.addActionListener(this);
        JMenuItem cut = new JMenuItem("Cut");

        cut.addActionListener(this);

        JMenuItem selectall = new JMenuItem("Select All");
        selectall.addActionListener(this);


        JMenu about = new JMenu("Help");
        JMenuItem notepad = new JMenuItem("About Notepad");
        notepad.addActionListener(this);

        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);//if text is too long wrap in allocate width
        area.setWrapStyleWord(true);

        scpane = new JScrollPane(area);//scroll view of a component
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar);//method inherit from Jframe use to set menu bar in Jframe
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(about);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);

        add(scpane, BorderLayout.CENTER);
        setVisible(true);//by default it is fasle,if false windwow will hidden
    }

    public void actionPerformed(ActionEvent ae){
        if (ae.getActionCommand().equals("New")){
            area.setText("");//setting a new text area field
        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("C:/Java");//location for file to open
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);//restricting filter to file extension

            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                try{

                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read( br, null );
                    br.close();
                    area.requestFocus();//requestFocus() makes a request that the given Component gets set to a focused state.
                    // This method requires that the component is displayable, focusable, visible and have all it's ancestors be visible too.
                }catch(Exception e){
                    System.out.print(e);
                }
            }
        } else if(ae.getActionCommand().equals("Save")){
            final JFileChooser SaveAs = new JFileChooser();//final as can't reassign these object name to anyother
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(ae.getActionCommand().equals("Print")){
            try{
                area.print();
            }catch(Exception e){}
        }else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        }else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText();
        }else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition());
        }else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        }else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll() ;
        }
    }
         public static void main(String[] args) {
        new Notepad();
    }
}
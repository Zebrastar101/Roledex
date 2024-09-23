import java.io.IOException;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Collections;
public class Roledex extends JFrame {

    public Roledex() throws IOException{
    //Set frame properties
        super("Roledex");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //Create names list
        ArrayList<People> people = new ArrayList<People>();
        readFile(people);
        JList names = new JList(people.toArray());
        //create scroll pane of needed
        JScrollPane scroll = new JScrollPane(names, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(10, 10, 200, 300);
        add(scroll);
        //Create Labels
        JLabel firstLabel = new JLabel("First Name:");
        firstLabel.setBounds(220, 10, 100, 20);
        add(firstLabel);
        JTextField firstField = new JTextField();
        firstField.setBounds(320, 10, 100, 20);
        add(firstField);
        JLabel lastLabel = new JLabel("Last Name:");
        lastLabel.setBounds(220, 40, 100, 20);
        add(lastLabel);
        JTextField lastField = new JTextField();
        lastField.setBounds(320, 40, 100, 20);
        add(lastField);
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(220, 70, 100, 20);
        add(phoneLabel);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(320, 70, 100, 20);
        add(phoneField);
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(220, 100, 100, 20);
        add(addressLabel);
        JTextField addressField = new JTextField();
        addressField.setBounds(320, 100, 100, 20);
        add(addressField);

        //Create buttons
        JButton save = new JButton("Save");
        save.setBounds(220, 130, 100, 20);
        add(save);
        JButton newStuff = new JButton("New");
        newStuff.setBounds(330, 130, 100, 20);
        add(newStuff);
        JButton delete = new JButton("Delete contact");
        delete.setBounds(250, 150, 200, 20);
        add(delete);
        delete.setVisible(false);
        JButton saveChanges = new JButton("Save Changes");
        saveChanges.setBounds(250, 130, 200, 20);
        add(saveChanges);
        saveChanges.setVisible(false);


        setVisible(true);
        save.addActionListener(e -> {
            if(firstField.getText().equals("") || lastField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill in First Name and Last name fields");
            } else {
                people.add(new People(firstField.getText(), lastField.getText(), phoneField.getText(), addressField.getText()));
                Collections.sort(people,Comparator.comparing(People::getLast).thenComparing(People::getFirst));

                names.setListData(people.toArray());

                firstField.setText("");
                lastField.setText("");
                phoneField.setText("");
                addressField.setText("");
            }

        });
        newStuff.addActionListener(e -> {
            firstField.setText("");
            lastField.setText("");
            phoneField.setText("");
            addressField.setText("");
        });
        delete.addActionListener(e -> {
            int index = names.getSelectedIndex();
            if(index != -1) {
                people.remove(index);
                Collections.sort(people,Comparator.comparing(People::getLast).thenComparing(People::getFirst));
                names.setListData(people.toArray());
            }
            firstField.setText("");
            lastField.setText("");
            phoneField.setText("");
            addressField.setText("");
            save.setVisible(true);
            newStuff.setVisible(true);
            delete.setVisible(false);
            saveChanges.setVisible(false);
            repaint();
            names.clearSelection();
        });
        saveChanges.addActionListener(e -> {
            people.get(names.getSelectedIndex()).setFirst(firstField.getText());
            people.get(names.getSelectedIndex()).setLast(lastField.getText());
            people.get(names.getSelectedIndex()).setPhone(phoneField.getText());
            people.get(names.getSelectedIndex()).setAddress(addressField.getText());
            Collections.sort(people,Comparator.comparing(People::getLast).thenComparing(People::getFirst));
            names.setListData(people.toArray());
            firstField.setText("");
            lastField.setText("");
            phoneField.setText("");
            addressField.setText("");
            save.setVisible(true);
            newStuff.setVisible(true);
            delete.setVisible(false);
            saveChanges.setVisible(false);
            repaint();
        });
        names.addListSelectionListener(e -> {
            if(names.getSelectedIndex() != -1) {
                firstField.setText(people.get(names.getSelectedIndex()).getFirst());
                lastField.setText(people.get(names.getSelectedIndex()).getLast());
                phoneField.setText(people.get(names.getSelectedIndex()).getPhone());
                addressField.setText(people.get(names.getSelectedIndex()).getAddress());
                save.setVisible(false);
                newStuff.setVisible(false);
                delete.setVisible(true);
                saveChanges.setVisible(true);
            }

        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    saveFile(people);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                e.getWindow().dispose();

            }
        });



    }

    public static void saveFile(ArrayList<People> people) throws IOException {
        File fileRef = new File("Rolodex.csv");
        if(!fileRef.exists())
        {
            fileRef.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(fileRef,false);
        PrintWriter writer = new PrintWriter(fileWriter);
        for(People p : people) {
            writer.println(p.getFirst() + "," + p.getLast() + "," + p.getPhone() + "," + p.getAddress());

        }
        writer.close();
    }
    public static void readFile(ArrayList<People> people) {
        File fileRef = new File("Rolodex.csv");
        if (fileRef.exists()) {
            try {
                Scanner fileReader = new Scanner(fileRef);
                while (fileReader.hasNextLine()) {
                    String[] data = fileReader.nextLine().split(",");
                    if(data.length == 4) {
                        people.add(new People(data[0], data[1], data[2], data[3]));
                    } else if (data.length == 3) {
                        people.add(new People(data[0], data[1], data[2], ""));
                    } else if (data.length == 2) {
                        people.add(new People(data[0], data[1], "", ""));
                    }

                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }






}


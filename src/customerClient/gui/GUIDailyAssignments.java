package customerClient.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import enumeration.CalendarState;
import server.Customer;

import javax.print.attribute.DocAttribute;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.lang.*;


public class GUIDailyAssignments extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel p = new JPanel();
    private JButton button[];
    private JLabel[] labelDescription;
    private JPanel[] infoPanel;
    private JLabel lb = new JLabel();
    private JLabel createLabel = new JLabel();
    private JScrollPane scroll = new JScrollPane(p);
    private HashMap<Integer, Assignment> listAssigment;
    private CustomerProxy proxy;
    private String email;
    private Date todayDate = new Date();


    public GUIDailyAssignments(CalendarState cs, String email, Date todayDate) {
        //TODO cambiare il costruttore: l'oggetto customer non sarà più accessibile
        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        this.email = email;
        proxy = new CustomerProxy(email);
        this.listAssigment = proxy.getAssignmentList();
        this.todayDate = todayDate;
        initComponents(cs);
    }


    private void initComponents(CalendarState cs) {
        //inserire query per interrogare db


        if (cs.equals(CalendarState.REMOVING)) {
            setTitle("Daily assignment");
            HashMap<Integer, Assignment> todayAssigment = new HashMap<>();

            int n=0;
            for (Integer i : listAssigment.keySet()) {
                Assignment a = null;
                a = listAssigment.get(i);
                Date dateStart = a.getDateStart();
                SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = date1.format(dateStart);
                System.out.println(dateString1);
                SimpleDateFormat date2 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString2 = date1.format(todayDate);
                System.out.println(dateString1);
                dateString1.equals(dateString2);
                if (dateString1.equals(dateString2)) { //|| a.getDateEnd().equals(todayDate))) {
                    System.out.println("OK");
                    todayAssigment.put(n, a);
                    System.out.println("Non funziona");
                }

                n++;
            }

            System.out.println(todayAssigment.size());
            labelDescription= new JLabel[todayAssigment.size()];
            button = new JButton[todayAssigment.size()];
            infoPanel = new JPanel[todayAssigment.size()];

            int j = 0;
            for (Integer i : todayAssigment.keySet()) {
                Assignment a = null;
                String labelString = "";
                a = todayAssigment.get(i);
                String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
                labelString = "<html>" + a.getDateStart() + "<br/>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";
                labelDescription[j] = new JLabel(labelString);
                button[j] = new JButton("Delete");
                button[j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showConfirmDialog(null,"Are you sure to cancel ?","Conferm Actions",JOptionPane.YES_NO_OPTION);}
                });
                infoPanel[j] = new JPanel();
                infoPanel[j].add(labelDescription[j]);
                infoPanel[j].add(button[j]);
                add(infoPanel[j]);
                j++;
            }
        }

          /*  p.setLayout(new GridLayout(button.length, 2));
            for (int i = 0; i < nAssignments; i++) {
                int n = i + 1;
                lb = new JLabel("Daily assignment n° " + n);
                button[i] = new JButton("Delete");
                p.add(lb);
                p.add(button[i]);
                button[i].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showConfirmDialog(null,"Are you sure to cancel ?","Conferm Actions",JOptionPane.YES_NO_OPTION);}
                });
            }*/


        else if (cs.equals(CalendarState.NORMAL)) {
            setTitle("Daily assignment");
           /* p.setLayout(new GridLayout(nAssignments, 2));
            for (int i = 0; i < nAssignments; i++) {
                int n = i + 1;
                lb = new JLabel("Daily assignment n° " + n);
                button[i] = new JButton("Info");
                p.add(lb);
                p.add(button[i]);
            }


        }*/
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            getContentPane().add(scroll);


        }
    }

}
   // Far combaciare la data del calendario con quella di un determinato appuntamento in modo da aver la possibilità di cancellarlo
  // Da sistemare


   /*             int nAssignments = todayAssigment.size();
                // panelout.add(panelButton);
                button = new JButton[nAssignments];
                if (cs.equals(CalendarState.REMOVING)) {
                infoPanel = new JPanel[nAssignments];
                labelDescription = new JLabel[nAssignments];
                setTitle("Daily assignment");
                p.setLayout(new GridLayout(button.length, 2));
                ActionListener write = new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
        }
        };

            }
        }*/
           /* for (int i = 0; i < nAssignments; i++) {
                int n = i + 1;
                lb = new JLabel("Daily assignment n° " + n);
                button[i] = new JButton("Delete");
                p.add(lb);
                p.add(button[i]);
               // lb.setText("Delete");
               // panelButton.add(yes);
               // panelButton.add(no);
                panelButton.add(button[6]);
                add(panelout);
                button[nAssignments].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("Delete")){
                        }
                        JOptionPane.showMessageDialog(new JFrame(),"Are you sure ?", "Conferm Action", JOptionPane.YES_NO_OPTION);
                    }
                });
            }*//*
          else if(cs.equals(CalendarState.NORMAL)) {
        setTitle("Daily assignment");
        p.setLayout(new GridLayout(nAssignments, 2));
        for (int i = 0; i < nAssignments; i++) {
            int n = i + 1;
            lb = new JLabel("Daily assignment n° " + n);
            button[i] = new JButton("Info");
            p.add(lb);
            p.add(button[i]);
        }
    }
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scroll);

                }

                }  */






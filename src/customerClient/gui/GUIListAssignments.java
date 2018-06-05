package customerClient.gui;

import customerClient.CustomerProxy;
import database.DBConnector;
import server.Assignment;
import server.Customer;
import server.Review;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class GUIListAssignments extends JFrame{



    private int assignmentNumber, reviewNumber;

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );



    private JPanel contentPanel = new JPanel(); //pannello esterno
    private JScrollPane scrollPanel = new JScrollPane(contentPanel);

    private JLabel labelState[]; //TODO
    private JLabel[] labelDescription; //non va a capo, trovare un alternativa
    private JButton[] buttonAction;
    private JPanel[] infoPanel;  //infopanel[i] contiene una label e un bottone
    //private Customer customer;
    private HashMap<Integer, Assignment> listAssignment;
    private CustomerProxy proxy;
    private String email;

    public GUIListAssignments(CalendarState cs, HashMap<Integer, Assignment> listAssignment, String email, GUICustomer guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.listAssignment = listAssignment;
        this.email = email;
        this.proxy = new CustomerProxy(email);


        initComponents(cs, guiCustomer);
    }

    private void initComponents(CalendarState cs, GUICustomer guiCustomer){
        //TODO stato dell'assignment, e vedere se funziona la visualizzazione delle recensioni

        assignmentNumber = listAssignment.size();
        //assignmentNumber = customer.getAssignmentList().size();
        //reviewNumber = customer.getReviewList().size();
        reviewNumber = 0;   //TODO


        if(cs.equals(CalendarState.DELETING_REVIEW)|| cs.equals(CalendarState.SHOW_REVIEWS)){ //da controllare
            infoPanel = new JPanel[reviewNumber];
            labelDescription = new JLabel[reviewNumber];
            buttonAction = new JButton[reviewNumber];

        } else {
            infoPanel = new JPanel[assignmentNumber];
            labelDescription = new JLabel[assignmentNumber];
            buttonAction = new JButton[assignmentNumber];
            labelState = new JLabel[assignmentNumber];
        }

        contentPanel.setLayout(new GridLayout(infoPanel.length,1, 5,5));



        //DBConnector dbConnector = new DBConnector();


        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");

            ActionListener write = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIWriteReview writeReview = new GUIWriteReview();
                    writeReview.setVisible(true);
                }
            };


            int j = 0;
            for(Integer i : listAssignment.keySet()){
                Assignment a = null;
                String labelString = "";

                a = listAssignment.get(i);
                String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());

                labelString = "<html>" + a.getDateStart() + "<br/>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";

                labelDescription[j]= new JLabel(labelString);
                buttonAction[j]= new JButton("Write a review");
                buttonAction[j].addActionListener(write);
                createPanelReview(j);
                j++;
            }






        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){ //DA controllare!!!!!
            setTitle("Your reviews");
            int j = 0;
            //TODO
            /*for(Integer i: customer.getReviewList().keySet()){
                Review r = null;
                String s;
                r = customer.getReviewList().get(i);
                s = "INFO";
                labelDescription[j]= new JLabel(s);
                buttonAction[j]= new JButton("Delete review");

                createPanel(j);
                j++;

            }*/





        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){ //DA controllare!!!!!
            setTitle("Your reviews");
            int j = 0;
            //TODO
            /*for(Integer i: customer.getReviewList().keySet()){
                Review r = null;
                String s;
                r = customer.getReviewList().get(i);
                s = "INFO";
                labelDescription[j]= new JLabel(s);
                buttonAction[j]= new JButton("Show more");

                createPanel(j);
                j++;

            }*/




        } else {
            int j = 0;
            for(Integer i : listAssignment.keySet()){
                Assignment a = null;

                a = listAssignment.get(i);

                String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());


                ActionListener showInfo = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer();
                        assignmentInfo.setVisible(true);

                    }
                };

                labelDescription[j]= new JLabel(nameDogSitter + " " + surnameDogSitter);
                buttonAction[j]= new JButton("Info");
                buttonAction[j].addActionListener(showInfo);


                createPanelAssignment(a,j);


                j++;

            }



        }

        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);



        //problema: se voglio vedere di nuovo la finestra di show all assignment dopo aver visto quella delle review, rimane quell delle review
        //RISOLTO con questo pezzo di codice
        this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                guiCustomer.setCalendarState(CalendarState.NORMAL);
            }
         });
    }

    //metodo che crea infoPanel[i] e gli assegna bottone e label
    private void createPanelAssignment(Assignment a, int i){
        infoPanel[i] = new JPanel();

        infoPanel[i].setLayout(new BorderLayout());

        infoPanel[i].setPreferredSize(new Dimension(480,40));
        infoPanel[i].add(createLabelState(a,i), BorderLayout.WEST);
        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);

        /*infoPanel[i].setLayout(new FlowLayout());

        infoPanel[i].setPreferredSize(new Dimension(800,40));
        infoPanel[i].add(createLabelState(a,i), FlowLayout.LEFT);
        infoPanel[i].add(labelDescription[i], FlowLayout.CENTER);
        infoPanel[i].add(buttonAction[i], FlowLayout.RIGHT);*/

        contentPanel.add(infoPanel[i]);

    }

    private void createPanelReview (int i){
        infoPanel[i] = new JPanel();

        infoPanel[i].setLayout(new BorderLayout());

        infoPanel[i].setPreferredSize(new Dimension(480,40));
        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);

        contentPanel.add(infoPanel[i]);
    }


    //metodo per settare il colore della labelState
    private JLabel createLabelState(Assignment a, int i){
        //da controllare il funzionamento


        Date todayDate= new Date(System.currentTimeMillis());

        ImageIcon green = new ImageIcon("images/Green_square.svg.png");
        Image imageTransform = green.getImage(); // transform it
        Image newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        green = new ImageIcon(newImage);  // transform it back


        ImageIcon red = new ImageIcon("images/red-180x180.png");
        imageTransform = red.getImage(); // transform it
        newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        red = new ImageIcon(newImage);  // transform it back

        ImageIcon yellow = new ImageIcon("images/yellow.jpg");
        imageTransform = yellow.getImage(); // transform it
        newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        yellow = new ImageIcon(newImage);  // transform it back


        if(a.getDateEnd().before(todayDate)){
            labelState[i]= new JLabel(red);

        }
        else if (a.getState()){
            labelState[i]= new JLabel(green);
        }
        else {
            labelState[i]= new JLabel(yellow);
        }

        return labelState[i];

    }







}

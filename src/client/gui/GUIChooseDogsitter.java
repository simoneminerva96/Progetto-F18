package client.gui;


import client.proxy.DogSitterProxy;
import server.Dog;
import server.places.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.HashSet;

public class GUIChooseDogsitter extends JFrame {
    final int WIDTH = 800;
    final int HEIGHT = 600;

    private JPanel panelOut = new JPanel();
    private JPanel panelClose = new JPanel(new BorderLayout());
    private GridLayout gridLayout = new GridLayout(1,1,5,0);
    private JScrollPane panelScroll = new JScrollPane(panelOut);
    private JButton buttonClose = new JButton("Close");

    private HashSet<String> dogsitterList;
    private Date dateStartAssignment;
    private Date dateEndAssignment;
    private HashSet<Dog> selectedDogs;
    private Address meetingPoint;
    private boolean paymentInCash;
    private String emailCustomer;

    /**
     * Constructor of the class GUIChooseDogsitter
     * @param dogsitterList HashSet that contains the list of the dogsitters
     * @param dateStartAssignment Assignment tarting date
     * @param dateEndAssignment Assignment ending Date
     * @param selectedDogs HashSet of dogs selected for the Assignment
     * @param meetingPoint Address where Customer and Dogsitter will meet
     * @param paymentInCash Boolean for PaymentMethod
     * @param emailCustomer String email of the Customer
     */

    GUIChooseDogsitter(HashSet<String> dogsitterList, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint, boolean paymentInCash, String emailCustomer, GUINewAssignment guiNewAssignment) {
        setTitle("Choose the dogsitter");
        setSize(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        this.dogsitterList = dogsitterList;
        this.dateStartAssignment = dateStartAssignment;
        this.dateEndAssignment = dateEndAssignment;
        this.selectedDogs = selectedDogs;
        this.meetingPoint = meetingPoint;
        this.paymentInCash = paymentInCash;
        this.emailCustomer = emailCustomer;

        guiNewAssignment.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiNewAssignment.setEnabled(true);
            }
        });


        initComponents();
    }


    /**
     * This method implements graphic objects
     */

    public void initComponents() {
        panelOut.setLayout(new BorderLayout());
        JPanel panelContainer = new JPanel(gridLayout);
        panelClose.setBorder(BorderFactory.createEmptyBorder(20,320,20,320));

        GUIChooseDogsitter guiChooseDogsitter = this;

        panelOut.add(panelContainer, BorderLayout.NORTH);
        panelOut.add(panelClose, BorderLayout.SOUTH);
        panelClose.add(buttonClose, BorderLayout.CENTER);

        for (String mailDogsitter: dogsitterList){

            DogSitterProxy dogSitterProxy = new DogSitterProxy(mailDogsitter);


            JLabel labelDogsitter = new JLabel("<html><br>" + dogSitterProxy.getName() + " " + dogSitterProxy.getSurname() + "<br/>" + mailDogsitter, SwingConstants.LEFT);

            JPanel panelLabel = new JPanel();
            panelLabel.setBorder(BorderFactory.createEmptyBorder(0,40,20, 0));
            panelLabel.add(labelDogsitter);

            JPanel panelButtons = new JPanel();
            panelButtons.setLayout(new GridLayout(1,2,10,0));
            panelButtons.setBorder(BorderFactory.createEmptyBorder(30,0,30, 40));

            JButton buttonInfo = new JButton("Info");
            JButton buttonSelect = new JButton("Select");
            panelButtons.add(buttonInfo);
            panelButtons.add(buttonSelect);

            JPanel panelDogsitter = new JPanel();
            panelDogsitter.setLayout(new BorderLayout());
            panelDogsitter.setBorder(BorderFactory.createTitledBorder(""));
            panelDogsitter.add(panelLabel, BorderLayout.WEST);
            panelDogsitter.add(panelButtons, BorderLayout.EAST);
            panelContainer.add(panelDogsitter);

            gridLayout.setRows(gridLayout.getRows() + 1);


            buttonSelect.addActionListener(e -> new GUIConfirmAssignment(mailDogsitter, dateStartAssignment, dateEndAssignment, selectedDogs, meetingPoint, emailCustomer, paymentInCash, guiChooseDogsitter).setVisible(true));
            buttonInfo.addActionListener(e -> new GUIDogsitterInfo(mailDogsitter, guiChooseDogsitter).setVisible(true));

        }

        buttonClose.addActionListener(e -> guiChooseDogsitter.dispatchEvent(new WindowEvent(guiChooseDogsitter, WindowEvent.WINDOW_CLOSING)));



        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(panelScroll);

    }
}

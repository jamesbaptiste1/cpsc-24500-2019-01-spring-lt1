// Imports: In order of appearance. 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container; 
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

class ContactTile extends JPanel {
    private int red, green, blue;
    private ThunderbirdContact contactInSeat = null;

    private Boolean isAnIsle = false;
    public void setIsle() { isAnIsle = true; }

    ContactTile() {
        super();

        // Todo: Remove everything to do with random colors.
        // Todo: Implement visually appealing colors for isles and seats.
        SetRandomValues();
    }

    ContactTile(ThunderbirdContact contactInSeatIn) {
        super();
        SetRandomValues();
        contactInSeat = contactInSeatIn;
    }

    final public void SetRandomValues() {
        red = GetNumberBetween(0,255);
        green = GetNumberBetween(0,255);
        blue = GetNumberBetween(0,255);
    }

    private static int GetNumberBetween(int min, int max) {
        Random myRandom = new Random();
        return min + myRandom.nextInt(max-min+1);
    }   

     public void paintComponent(Graphics g) {
        super.paintComponent(g); 

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (isAnIsle) {
            g.setColor(new Color(0,0,0));
        } else {
            g.setColor(new Color(red,green,blue));
        }
        
        g.fillRect (10, 10, panelWidth-20, panelHeight-20);

        g.setColor(new Color(GetContrastingColor(red),GetContrastingColor(green),GetContrastingColor(blue)));

        final int fontSize=18;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int stringX = (panelWidth/2)-60;
        int stringY = (panelHeight/2)+30;
        if (contactInSeat != null) {
            // ToDo: Dispay preferred name instead of first and last name. 
            
            String firstAndLastName = contactInSeat.getFirstName()+" "+contactInSeat.getLastName();
            g.drawString(firstAndLastName,stringX,stringY);
        }
    }

    private static int GetContrastingColor(int colorIn) {
        return ((colorIn+128)%256);
    }
}

class ThunderbirdPrimeFrame extends JFrame implements ActionListener {
    private ArrayList<ContactTile> tileList;

    public ThunderbirdPrimeFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton reverseView = new JButton("Reverse View");
        buttonPanel.add(reverseView);
        reverseView.addActionListener(this);

        JPanel contactGridPanel = new JPanel();
        contentPane.add(contactGridPanel, BorderLayout.CENTER);
        contactGridPanel.setLayout(new GridLayout(4,8));

        // The following code was taken directly from SwissArmyKnifeLite.
        ThunderbirdModel tbM = new ThunderbirdModel();
        tbM.LoadIndex();
        // tbM.LoadContacts();
        tbM.LoadContactsThreaded();
        System.out.println("Printing Model:");
        System.out.println(tbM);
        tbM.ValidateContacts();   
        // End of copied code.    

        tileList = new ArrayList<ContactTile>();
        for(int i=1; i<33; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i);
            if (contactInSeat != null) {
                System.out.println(contactInSeat);
            }

            ContactTile tile = new ContactTile(contactInSeat);

            // Todo: Place all the isle seats in a array or an ArrayList instead of hard coding them. 
            if ((i==4)||(i==12)||(i==31)) {
                tile.setIsle();
            }

            tileList.add(tile);
            contactGridPanel.add(tile);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for(ContactTile tile : tileList) {
            // Todo: Remove randomization functionality.
            tile.SetRandomValues();

            // Todo: Implement reverse view where it looks like you are looking at the room from the back instead of the front 
            //     of the room. 
        }

        repaint();
    }
}

public class ThunderbirdPrime {
    public static void main(String[] args) {
        System.out.println("ThunderbirdPrime Starting...");

        ThunderbirdPrimeFrame myThunderbirdPrimeFrame = new ThunderbirdPrimeFrame();
        myThunderbirdPrimeFrame.setVisible(true);
    }
}
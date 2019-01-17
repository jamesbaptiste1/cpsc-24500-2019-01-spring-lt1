import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Container; 
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

// Model Classes
class PersonInfo {
    private String PreferredName;
    private String SeatLocation;

    public PersonInfo() {
        PreferredName = "";
        SeatLocation = "";
    }

    public void LoadFromURL(String URLIn) throws Exception {
        URL myURL = new URL(URLIn);
        URLConnection myConnection = myURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            ParseLine(inputLine);
            System.out.println(inputLine);
        }
        in.close();
    }

    private void ParseLine(String line) {
        if (line.indexOf("PreferredName") > -1) {
            String[] elements = line.split("\"");
            PreferredName = elements[3];
        }

        if (line.indexOf("SeatLocation") > -1) {
            String[] elements = line.split("\"");
            SeatLocation = elements[3];           
        }

        //Todo: Parse additional information.
    }    

    public int GetRow() {
        String[] elements = SeatLocation.split("-");
        return Integer.parseInt(elements[1]);
    }

    public int GetColumn() {
        String[] elements = SeatLocation.split("-");
        return Integer.parseInt(elements[0]);
    }

    public String toString() {
        return "PreferredName:"+PreferredName+", SeatLocation:"+SeatLocation+
            ", Column:"+GetColumn()+", Row:"+GetRow();
    }   
}

class PersonView extends JPanel {
    private PersonInfo info;

    public PersonView() {
        info = null;
    }

    public PersonView(PersonInfo infoIn) {
        info = infoIn;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        g.setColor(new Color(255,0,0));
        if (info == null) {
            g.fillRect(10, 10, panelWidth-20, panelHeight-20);
        }
        else {
            g.drawRect(10, 10, panelWidth-20, panelHeight-20);
            System.out.println(info);
        }

        //Todo: Draw walkways.
        //Todo: Draw PreferredName.
        //Todo: Make it much more visually appealing 
    }
}

// View Classes
class TBLView extends JFrame {
    public TBLView(ArrayList<PersonInfo>personInfoList) {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel roomView = new JPanel();
        roomView.setLayout(new GridLayout(4,8));

        for(int i=1; i<33; i++) {
            PersonView view = new PersonView();

            for(PersonInfo pI:personInfoList) {
                int seatNumber = ConvertRowAndColToSeatNumber(pI.GetColumn(), pI.GetRow());
                if (i == seatNumber) {
                    view = new PersonView(pI);
                }    
            }

            roomView.add(view);
        }

        Container contentPane = getContentPane();
        contentPane.add(roomView);
    }

    private int ConvertRowAndColToSeatNumber(int col, int row) {
        return (32-(row*8+(7-col)));
    }
}

public class ThunderbirdLite {
    public static void main(String[] args) throws Exception {
        System.out.println("ThunderbirdLite starting...");

        if (args.length != 1) {
            System.out.println("Error: This application requires the name of a file to be passed into the application.");
            System.exit(0);
        }

        ArrayList<PersonInfo>personInfoList = new ArrayList<PersonInfo>();

        BufferedReader myBR = new BufferedReader(new FileReader(args[0]));
        String line;
        while((line = myBR.readLine()) != null) {
            String[] elements = line.split("\"");
            if (elements.length > 1) {
                if (elements[1].indexOf("http") > -1) {
                    // Initiate http request to retrieve remote JSON file.
                    PersonInfo info = new PersonInfo();                  
                    info.LoadFromURL(elements[1]);
                    personInfoList.add(info);
                }
            }
        }

        TBLView myView = new TBLView(personInfoList);
    }
}
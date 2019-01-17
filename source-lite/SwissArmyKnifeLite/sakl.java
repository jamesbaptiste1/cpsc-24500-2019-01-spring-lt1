// SwissArmyKnifeLite (sakl)

public class sakl {
    public static void main(String[] args) {
        System.out.println("");
        if (args.length < 1) {
            // Process no arguments.
            System.out.println("This application requires at least one argument. Utilize the \"-Help\" parameter for more "
                +"information.");
            Help.printHelp();         
        } 
        else if (args[0].equalsIgnoreCase("-Help")) {
            System.out.println("Executing Help...");
            Help.printHelp();          
        } 
        else if (args[0].equalsIgnoreCase("-HttpRequest")) {
            System.out.println("Executing HttpRequest...");
            if (args.length <2) {
                System.out.println("The -HttpRequest function requires a valid URL as the second parameter.");
            } else {
                String URL = args[1];
                HttpRequest request = new HttpRequest();
                if (request.readURL(URL)) {
                    System.out.println(request);
                }           
            }
        }
        else if (args[0].equalsIgnoreCase("-HttpRequestFile")) {
             System.out.println("Executing HttpRequestFile...");    
            if (args.length <2) {
                System.out.println("The -HttpRequestFile function requires a valid JSON file name as the second parameter.");
            } else {
                String fileName = args[1];
                HttpRequestFile requestFile = new HttpRequestFile();
                if (requestFile.readFileURLs(fileName)) {
                    System.out.println(requestFile);
                    requestFile.processURLs();
                }        
            }
        }       
        else if (args[0].equalsIgnoreCase("-HttpRequestMultiThreaded ")) {
             System.out.println("Executing -HttpRequestMultiThreaded ...");    
             // Todo: Implement HttpRequestFileMultiThreaded.
        }       
        else if (args[0].equalsIgnoreCase("-TestModel")) {
            System.out.println("Executing -TestModel ...");   
         
            ThunderbirdModel tbM = new ThunderbirdModel();
            tbM.LoadIndex();
            tbM.LoadContacts();
            // tbM.LoadContactsThreaded();
            System.out.println("Printing Model:");
            System.out.println(tbM);
            tbM.ValidateContacts();
        }       

        System.out.println("");
    }
} 
import java.util.ArrayList;

class ThunderbirdModel extends HttpRequest {
    private String indexURL = "https://thunderbird-data-ejp.azurewebsites.net/url-list.json";
    private ArrayList<ThunderbirdContact> contactList;

    ThunderbirdModel() {
        contactList = new ArrayList<ThunderbirdContact>();
    }

    public Boolean LoadIndex() {
        Boolean returnValue = false;
        if (readURL(indexURL)) {
            // Look through the index JSON file for each URL and then creat a new Contact object.
            for (String line : urlContent) {
                String[] subString = line.split("\"");
                // The substring after the 11 double-quote should be the URL.
                if (subString.length >= 11) {
                    if (subString[11].indexOf("https://") > -1) {
                        contactList.add(new ThunderbirdContact(subString[11]));

                        // We must have added at least one ThunderbirdContact to be successful.
                        returnValue = true;
                    }
                }
            }            
        }
        
        return returnValue; 
    }

    public void LoadContacts() {
        long start = System.currentTimeMillis();
        int contactsLoaded = 0;
        for (ThunderbirdContact hrC : contactList) {
            hrC.Load();
            contactsLoaded++;
        }

        System.out.println("Contacts Loaded: " + contactsLoaded);
        System.out.println("LoadContacts Elapsed Time: " + (System.currentTimeMillis()-start) + " ms\n");
    }

    public void LoadContactsThreaded() {
        long start = System.currentTimeMillis(); 
        int contactsLoaded = 0;

        ArrayList<Thread> threadList = new ArrayList<Thread>();
        for (ThunderbirdContact hrC : contactList) {
            threadList.add(new Thread(hrC));
        }        

        for (Thread t: threadList) {
            t.start();
            contactsLoaded++;
        }

        try {
            for (Thread t: threadList) {
                t.join();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        System.out.println("Contacts Loaded: " + contactsLoaded);
        System.out.println("LoadContacts Elapsed Time = " + (System.currentTimeMillis()-start) + " ms\n");
    }

    public void ValidateContacts() {
        long start = System.currentTimeMillis();
        System.out.println("Validating Contacts:");
        for (ThunderbirdContact hrC : contactList) {
            hrC.Validate();
        }
        System.out.println("ValidateContacts Elapsed Time = " + (System.currentTimeMillis()-start) + " ms\n");
    }
    
    public String toString() {
        // The super class contains the Index of contacts.
        String returnString = "Index:\n" + super.toString();

        returnString = returnString + "\nPrinting Contacts:\n";
        for (ThunderbirdContact contact : contactList) {
            returnString = returnString + contact + "\n";
        } 
        return returnString;
    }

    public ThunderbirdContact findContactInSeat(int seat) {
        ThunderbirdContact returnContact = null; 
        for (ThunderbirdContact contact : contactList) {        
            if (contact.getSeat() == seat) {
                returnContact = contact;
            }
        }
        return returnContact; 
    } 
}
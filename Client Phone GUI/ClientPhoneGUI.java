import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Formatter;

class Client
{
   private String name;
   private String phone;
   private boolean dataValid;

   public Client (String name, String phone)
   {
      dataValid = false;

      if (name.length() == 0)
      {
         JOptionPane.showMessageDialog(null, "Name cannot be blank.",
              "Name Error:", JOptionPane.ERROR_MESSAGE);

         //throw new Exception ("Invalid Name");
      }
      else if (phone.length() == 0)
      {
         JOptionPane.showMessageDialog(null, "Phone cannot be blank.",
              "Phone Error:", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         // OK, no errors, all is OK.
         dataValid  = true;
         this.name  = name;
         this.phone = phone;
      }
   }

   public String getName ()
   {
      return name;
   }

   public String getPhone ()
   {
      return phone;
   }

   public boolean isDataValid ()
   {
      return dataValid;
   }
}


public class ClientPhoneGUI extends JFrame
{
   public static final String DATA_FILE_NAME = "clientphone.txt";

   JTextArea textArea      = new JTextArea ();
   JButton   displayButton = new JButton ("Display");
   JButton   clearButton   = new JButton ("Clear");
   JButton   saveButton    = new JButton ("Save");
   JButton   addButton     = new JButton ("Add");
   JButton   editButton    = new JButton ("Edit");

   private ArrayList<Client> clientArrayList = new ArrayList<Client> ();

   public ClientPhoneGUI ()
   {
      setTitle ("Client Phone App v0.03");

      JPanel flowPanel = new JPanel (new FlowLayout (FlowLayout.CENTER));

      add (textArea,      BorderLayout.CENTER);


      flowPanel.add (displayButton);
      flowPanel.add (addButton);
      flowPanel.add (editButton);
      flowPanel.add (clearButton);
      flowPanel.add (saveButton);

      add (flowPanel, BorderLayout.SOUTH);

      displayButton.addActionListener (event -> readFile (DATA_FILE_NAME));
      clearButton.addActionListener   (event -> clearData ());
      saveButton.addActionListener    (event -> writeFile (DATA_FILE_NAME));
      addButton.addActionListener     (event -> addData ());
      editButton.addActionListener    (event -> editData ());

      readFile (DATA_FILE_NAME);

      setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);

      addWindowListener (new WindowAdapter ()
      {
         public void windowClosing (WindowEvent e)
         {
            exitApplication ();
         }
      });

   }

   private void addTextData ()
   {
      textArea.setText ("Hello World" + "\n\n" +
         "The 'Display' button is working !");
   }

   private void addData ()
   {
      String name  = JOptionPane.showInputDialog ("Enter name:");
      String phone = JOptionPane.showInputDialog ("Enter phone:");

      Client newClient = new Client (name, phone);

      if (newClient.isDataValid () == true)
      {
    	  clientArrayList.add (newClient);

         System.out.println ("New client member added, now: " +
        		 clientArrayList.size() + " records.");

         refreshTextArea ();
      }
   }

   private void editData ()
   {
      /*
        Prompt user for a name
        Search out ArrayList for that name
        if Match found
           - prompt for new name
           - prompt for phone number
           - create new Client object
           - Overwrite the old client member in the ArrayList
        if Match not found - display an error.
      */
      String searchName  = JOptionPane.showInputDialog ("Enter name to edit:");

      boolean matchFound = false;
      int     location   = -1;

      for (int k = 0; k < clientArrayList.size(); k++)
      {
         if (searchName.compareTo (clientArrayList.get (k).getName ()) == 0)
         {
            matchFound = true;
            location   = k;
         }
      }

      if (matchFound == false)
      {
         JOptionPane.showMessageDialog(null,
              "Error: client '" + searchName + "' could not be found.",
              "Phone Error:", JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         // A match was found - the client member IS in the ArrayList
         String name  = JOptionPane.showInputDialog ("Enter new name:");
         String phone = JOptionPane.showInputDialog ("Enter new phone:");

         Client newClient = new Client (name, phone);

         if (newClient.isDataValid () == true)
         {
        	 clientArrayList.set (location, newClient);

            System.out.println ("Client member updated !");

            refreshTextArea ();
         }
      }
   }

   private void readFile (String fileName)
   {
      String fileDataStr = "";

      clientArrayList.clear ();

      try
      {
         FileReader fileReader = new FileReader (fileName);
         Scanner inFile = new Scanner (fileReader);

         while (inFile.hasNext() == true)
         {
            String[] parts = inFile.nextLine().split (", ");
            if (parts.length == 2)
            {
            	Client newClient = new Client (parts [0], parts [1]);

               clientArrayList.add (newClient);

            }
            else
            {
               throw new IOException ();
            }
         }

         inFile.close();
         fileReader.close();

         refreshTextArea ();

         System.out.println (clientArrayList.size() + " records read.");

      }
      catch (IOException err)
      {
         textArea.setText ("Error: file could not be read.");
         //System.exit(-1); // Error.
      }
   }


   private void refreshTextArea ()
   {
      textArea.setText ("");
      for (int k = 0; k < clientArrayList.size(); k++)
      {
         textArea.append (clientArrayList.get (k).getName ()
            + "\t" + clientArrayList.get (k).getPhone ()
            + "\n");
      }

      textArea.append ("\n\n" + clientArrayList.size() + " records read." + "\n");
   }


   private void writeFile (String fileName)
   {
      try
      {
         Formatter outFile = new Formatter (fileName);

         for (int k = 0; k < clientArrayList.size(); k++)
         {
            outFile.format ("%s, %s\n", clientArrayList.get(k).getName(),
            		clientArrayList.get(k).getPhone());
         }

         outFile.close();

         System.out.println (clientArrayList.size() + " records written.");
      }
      catch (IOException err)
      {
         textArea.setText ("Error: file could not be written.");
         //System.exit(-1); // Error.
      }
   }

   private void clearData ()
   {
	   clientArrayList.clear ();
      textArea.setText ("");
   }

   private void exitApplication ()
   {
      writeFile (DATA_FILE_NAME);

      System.exit (0); // All is OK
   }

   public static void main (String[] args)
   {
	  ClientPhoneGUI app = new ClientPhoneGUI ();
      app.setVisible (true);
      app.setSize (500, 310);
      app.setLocation (200, 200);
      //app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
   }
} // public class ClientPhoneGUI

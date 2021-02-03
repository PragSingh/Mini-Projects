import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class EmployeeEmailGUI extends JFrame
{
   // Constants:

   // GUI Componet:
   JTextArea  employeeTextArea   = new JTextArea ();

   JLabel     idLabel           = new JLabel     ("ID: ");
   JTextField idTextField       = new JTextField (10);
   JLabel     nameLabel         = new JLabel     ("Name: ");
   JTextField nameTextField     = new JTextField (10);

   JButton    testDataButton    = new JButton ("Test Data");
   JButton    addButton         = new JButton ("Add");
   JButton    deleteButton      = new JButton ("Delete");
   JButton    editButton        = new JButton ("Edit");
   JButton    editSaveButton    = new JButton ("Save");
   JButton    displayAllButton  = new JButton ("Display All");
   JButton    exitButton        = new JButton ("Exit");


   // Class Instance Data:
   private LinkedList<EmployeeEmail> employeeLinkedList = new LinkedList<EmployeeEmail> ();
   private int editIndex;


   public EmployeeEmailGUI ()
   {
      JPanel flow1Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
      JPanel flow2Panel = new JPanel (new FlowLayout (FlowLayout.CENTER));
      JPanel gridPanel  = new JPanel (new GridLayout (2, 1));

      employeeTextArea.setEditable (false);

      flow1Panel.add (idLabel);
      flow1Panel.add (idTextField);
      flow1Panel.add (nameLabel);
      flow1Panel.add (nameTextField);

      flow2Panel.add (testDataButton);
      flow2Panel.add (addButton);
      flow2Panel.add (editButton);
      flow2Panel.add (editSaveButton);
      flow2Panel.add (deleteButton);
      flow2Panel.add (displayAllButton);
      flow2Panel.add (exitButton);

      gridPanel.add (flow1Panel);
      gridPanel.add (flow2Panel);

      editSaveButton.setEnabled (false);

      add (employeeTextArea, BorderLayout.CENTER);
      add (gridPanel,       BorderLayout.SOUTH);


      addButton.addActionListener        (event -> addEmployee ());
      displayAllButton.addActionListener (event -> displayAll ());
      editButton.addActionListener       (event -> editEmployee ());
      editSaveButton.addActionListener   (event -> editSaveEmployee ());
      exitButton.addActionListener       (event -> exitApplication ());
      deleteButton.addActionListener     (event -> deleteEmployee ());
      testDataButton.addActionListener   (event -> addTestData ());

      setTitle ("Employee Linked List - v0.02");
   }

   private boolean isEmployeeIdInLinkedList (String idStr)
   {
      boolean inList = false;

      for (EmployeeEmail empl : employeeLinkedList)
      {
         if (empl.getId ().compareToIgnoreCase (idStr) == 0)
         {
            inList = true;
         }
      }

      return inList;
   }

   private void addEmployee ()
   {
      if (isEmployeeIdInLinkedList (idTextField.getText()) == true)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                              "Error: employee ID is already in the database.");
      }
      else
      {
         try
         {
            EmployeeEmail empl = new EmployeeEmail (nameTextField.getText(),
                                                  idTextField.getText());

            employeeLinkedList.add (empl);

            displayAll ();

            nameTextField.setText("");
            idTextField.setText("");


         }
         catch (EmployeeEmailException error)
         {
            JOptionPane.showMessageDialog (EmployeeEmailGUI.this, error.toString ());
            // myLabel.setText (error.toString ());


         }

      }
   }

   private void deleteEmployee ()
   {
      if (employeeLinkedList.size() == 0)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                        "Error: Database is empty.");
      }
      else if (isEmployeeIdInLinkedList (idTextField.getText()) == false)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                       "Error: employee ID is not in the database.");
      }
      else
      {
         for (int s = 0; s < employeeLinkedList.size(); s++)
         {
            String currId = employeeLinkedList.get (s).getId ();

            if (currId.compareToIgnoreCase (idTextField.getText()) == 0)
            {
               employeeLinkedList.remove (s);
            }
         }

         displayAll ();

         nameTextField.setText("");
         idTextField.setText("");
      }
   }

   private void editEmployee ()
   {
      if (employeeLinkedList.size() == 0)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                        "Error: Database is empty.");
      }
      else if (isEmployeeIdInLinkedList (idTextField.getText()) == false)
      {
         JOptionPane.showMessageDialog (EmployeeEmailGUI.this,
                                        "Error: employee ID is not in the database.");
      }
      else
      {
         editIndex = -1;

         for (int s = 0; s < employeeLinkedList.size(); s++)
         {
            String currId = employeeLinkedList.get (s).getId ();

            if (currId.compareToIgnoreCase (idTextField.getText()) == 0)
            {
               editIndex = s;
               s = employeeLinkedList.size(); // Exit Loop
            }
         }

         // index cannot be less than 0, because we checked if the Empl Id was in
         // the linked list before we looped above.
         if (editIndex >= 0)
         {
            editSaveButton.setEnabled   (true);

            editButton.setEnabled       (false);
            testDataButton.setEnabled   (false);
            addButton.setEnabled        (false);
            deleteButton.setEnabled     (false);
            displayAllButton.setEnabled (false);
            exitButton.setEnabled       (false);

            nameTextField.setText (employeeLinkedList.get (editIndex).getName () );
            //idTextField.setText   (employeeLinkedList.get (editIndex).getId () );
         }


      }

   }

   private void editSaveEmployee ()
   {
      // This code will preserve the changes the user made to the employee
      // they were editing - and save them back into the Linked List.

	   employeeLinkedList.get (editIndex).setName (nameTextField.getText() );
	   employeeLinkedList.get (editIndex).setId   (idTextField.getText() );

      displayAll ();

      nameTextField.setText ("");
      idTextField.setText   ("");

      editSaveButton.setEnabled   (false);

      editButton.setEnabled       (true);
      testDataButton.setEnabled   (true);
      addButton.setEnabled        (true);
      deleteButton.setEnabled     (true);
      displayAllButton.setEnabled (true);
      exitButton.setEnabled       (true);
   }

   private void addTestData ()
   {
      nameTextField.setText ("Prag");
      idTextField.setText   ("s123");
      addEmployee ();

      nameTextField.setText ("Rahul");
      idTextField.setText   ("s111");
      addEmployee ();

      nameTextField.setText ("Karan");
      idTextField.setText   ("s789");
      addEmployee ();
   }

   private void displayAll ()
   {
	   employeeTextArea.setText ("");

      for (EmployeeEmail empl :employeeLinkedList)
      {
    	  employeeTextArea.append (empl + "\n");
      }
   }

   private void exitApplication ()
   {
      System.exit (0); // All is OK.
   }

   public static void main (String[] args)
   {
      EmployeeEmailGUI app = new EmployeeEmailGUI ();
      app.setVisible  (true);
      app.setSize     (500, 310);
      app.setLocation (200, 100);
      app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
   }
} // public class EmployeeEmailGUI

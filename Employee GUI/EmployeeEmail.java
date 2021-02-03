public class EmployeeEmail
{
   public static final String EMAIL_EMPL = "@my.email";

   private String name;
   private String id;

   public EmployeeEmail ()
   {
      name = "";
      id   = "";
   }

   public EmployeeEmail (String name, String id)
                        throws EmployeeEmailException
   {
      // Remove elading and trailing spaces, tabs.
      name = name.trim ();
      id   = id.trim ();

      if (name.length () == 0)
      {
         //JOptionPane.showMessageDialog (null, "Error: name cannot be blank.");
         throw new EmployeeEmailException ("Error: name cannot be blank.");
      }

      else if (id.length () == 0)
      {
         //JOptionPane.showMessageDialog (null, "Error: Id cannot be blank.");
         throw new EmployeeEmailException ("Error: Id cannot be blank.");
      }

      else
      {
         // All is OK, set class data to the values passed in.
         this.name = name;
         this.id   = id;
      }
   }

   public String getName ()
   {
      return name;
   }

   public String getId ()
   {
      return id;
   }

   public void setName (String name)
   {
      this.name = name;
   }

   public void setId (String id)
   {
      this.id = id;
   }

   @Override
   public String toString ()
   {
      return id + "\t" + name + "\t" + id + EMAIL_EMPL;
   }

} // public class EmployeeEmail

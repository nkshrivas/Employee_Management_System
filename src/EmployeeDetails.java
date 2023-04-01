import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class EmployeeDetails extends JFrame implements ActionListener{
    JPanel panel;
    JButton add,edit,delete,update,find,save;
    JLabel name,dob,age,doj,mobile,address,city,state,country;
    JTextField namef,dobf,agef,dojf,mobilef,addressf,cityf,statef,countryf;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel overallPercentageLabel;

    public EmployeeDetails(){
        this.setSize(720,720);
        panel=new JPanel();
        panel.setLayout(null);
        

         //Creating Buttons

         add=new JButton("Add");
         add.addActionListener(this);
         add.setBounds(20,30,100,40);
 
         edit=new JButton("Edit");
         edit.addActionListener(this);
         edit.setBounds(160,30,100,40);
 
         delete=new JButton("Delete");
         delete.addActionListener(this);
         delete.setBounds(280,30,100,40);
 
         find=new JButton("Find");
         find.addActionListener(this);
         find.setBounds(400,30,100,40);
 
         save=new JButton("Save");
         save.addActionListener(this);
         save.setBounds(520,30,100,40);


         //creating JLabel for the interface

         name=new JLabel("Name");
         name.setBounds(20,100,100,40);

         
         dob=new JLabel("Date of Birth");
         dob.setBounds(20,150,100,40);

         
         age=new JLabel("Age");
         age.setBounds(380,150,100,40);



         doj=new JLabel("Date of Joining");
         doj.setBounds(20,200,100,40);

         
         mobile=new JLabel("Mobile");
         mobile.setBounds(380,200,100,40);

         
         address=new JLabel("Adress");
         address.setBounds(20,250,100,40);
         
         city=new JLabel("City");
         city.setBounds(20,300,100,40);
         
         state=new JLabel("State");
         state.setBounds(20,350,100,40);
       
         country=new JLabel("Country");
         country.setBounds(380,350,100,40);


        //  Adding Text Feild to the system

        namef=new JTextField();
        namef.setBounds(150,110,200,30);

        dobf=new JTextField("yyyy-mm-dd");
        dobf.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Perform action when Enter key is pressed
                    // ...
                    String dobString = dobf.getText();
                    LocalDate dob = LocalDate.parse(dobString); // parse the user input into a LocalDate object
                    LocalDate now = LocalDate.now(); // get the current date
            
                    Period age = Period.between(dob, now); // calculate the period between the DOB and the current date
            
                    int ageInYears = age.getYears(); 
            
                    agef.setText(ageInYears+"");
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                // This method is not used, but must be implemented due to KeyListener interface
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                // This method is not used, but must be implemented due to KeyListener interface
            }
        });
        dobf.addActionListener(this);
        dobf.setBounds(150,160,200,30);

        

        dojf=new JTextField();
        dojf.setBounds(150,210,200,30);
        

        mobilef=new JTextField();
        mobilef.setBounds(460,210,200,30);
        
        
        agef=new JTextField();
        agef.setBounds(460,160,200,30);
        
        addressf=new JTextField();
        addressf.setBounds(150,260,400,30);
        
        cityf=new JTextField();
        cityf.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Perform action when Enter key is pressed
                    // ...

                    Connection con=new Connection();
                    String sql="SELECT * from cities where city='"+cityf.getText()+"'";
            try{
                java.sql.Statement st = con.conn.createStatement();
                ResultSet res=st.executeQuery(sql);
                while(res.next()){
                 String scode=  res.getString("statecode");
                   String ccode = res.getString("countrycode");
                   try {
                    String ssql="Select * from states where statecode = "+scode;
                    java.sql.Statement sst = con.conn.createStatement();
                    ResultSet sres=sst.executeQuery(ssql);
                    while(sres.next()){
                        statef.setText(sres.getString("statename"));
                    }
                   } catch (Exception err) {
                    err.printStackTrace();
                    // TODO: handle exception
                   }
                   try {
                    String csql="Select * from countries where countrycode = "+ccode;
                    java.sql.Statement cst = con.conn.createStatement();
                    ResultSet cres=cst.executeQuery(csql);
                    while(cres.next()){
                        countryf.setText(cres.getString("countryname"));
                    }
                   } catch (Exception err) {
                    err.printStackTrace();
                    // TODO: handle exception
                   }

                }

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    


                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                // This method is not used, but must be implemented due to KeyListener interface
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                // This method is not used, but must be implemented due to KeyListener interface
            }
        });


        String[] columnNames = {"Class", "College", "Total Marks", "Obtained Marks", "Percentage"};
        Object[][] data = {{"10th", "", 600, null, null},
                {"12th", "", 1200, null, null},
                {"Graduation", "", 4000, null, null},
                {"Post Graduation", "", 2000, null, null}};
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells editable except the Percentage and Overall Percentage columns
                return column != 4 && column != 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Use Integer class for Total Marks and Obtained Marks columns
                if (columnIndex == 2 || columnIndex == 3) {
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        table = new JTable(tableModel);

        // Add a button to calculate percentages and overall percentage
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate percentages and overall percentage
                ArrayList<Double> percentages = new ArrayList<>();
                double totalMarks = 0.0;
                double obtainedMarks = 0.0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int total = (int) tableModel.getValueAt(i, 2);
                    Integer obtained = (Integer) tableModel.getValueAt(i, 3);
                    double percentage = 0.0;
                    if (obtained != null) {
                        percentage = (double) obtained / total * 100.0;
                        percentages.add(percentage);
                        totalMarks += total;
                        obtainedMarks += obtained;
                    }
                    DecimalFormat df = new DecimalFormat("#.##");
                    tableModel.setValueAt(df.format(percentage) + "%", i, 4);
                }
                double overallPercentage = obtainedMarks / totalMarks * 100.0;
                DecimalFormat df = new DecimalFormat("#.##");
                overallPercentageLabel.setText("Overall Percentage: " + df.format(overallPercentage) + "%");
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,400,650,100);

        calculateButton.setBounds(10,550,100,40);

        overallPercentageLabel = new JLabel("Overall Percentage: ");

        overallPercentageLabel.setBounds(350,550,150,40);

        panel.add(calculateButton);
        panel.add(overallPercentageLabel);
        panel.add(scrollPane);


       







        cityf.setBounds(150,310,200,30);
        
        statef=new JTextField();
        statef.setBounds(150,360,200,30);
        
             
        countryf=new JTextField();
        countryf.setBounds(460,360,200,30);


         panel.add(add);
         panel.add(edit);
         panel.add(delete);
         panel.add(find);
         panel.add(save);



         panel.add(name);
         panel.add(dob);
         panel.add(age);
         panel.add(doj);
         panel.add(mobile);
         panel.add(address);
         panel.add(city);
         panel.add(state);
         panel.add(country);


         panel.add(namef);
         panel.add(dobf);
         panel.add(dojf);
         panel.add(addressf);
         panel.add(cityf);
         panel.add(statef);
         panel.add(agef);
         panel.add(mobilef);
         panel.add(countryf);

         




         this.add(panel);
         this.setVisible(true);

         
 



    }

    @Override
    public void actionPerformed(ActionEvent e) {
     
        if(e.getSource()==add){

            try {
                Connection con = new  Connection();
                java.sql.Statement stmt = con.conn.createStatement();
                CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
    
               
             
    
    
                crs.setCommand("INSERT INTO employeeDetails (empid,name,dob,age,doj,mobile,address,city,state,country,qualiid) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                
                crs.setString(1, mobilef.getText());
                
                crs.setString(2, namef.getText());
                crs.setString(3, dobf.getText());
                crs.setString(4, agef.getText());
                crs.setString(5, dojf.getText());
                crs.setString(6, mobilef.getText());
                crs.setString(7, addressf.getText());
                crs.setString(8, cityf.getText());
                crs.setString(9, statef.getText());

                crs.setString(10, countryf.getText());
                crs.setString(11, mobilef.getText());
                crs.execute(con.conn);


                

                
    
                crs.close();
                stmt.close();
                con.conn.close();
    
                namef.setText("");
                dobf.setText("");
                agef.setText("");
                addressf.setText("");

                dojf.setText("");

                cityf.setText("");
                countryf.setText("");
                mobilef.setText("");
                
                
                
    
                JOptionPane.showMessageDialog(null, "Added Successfully");
    
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    
         }
    


         else if(e.getSource()== find){
            try {
                Connection con = new Connection();
                java.sql.Statement stmt = con.conn.createStatement();
                CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                String sname=namef.getText();
                String sdob=dobf.getText();
                String query="SELECT * FROM employeedetails where name='"+sname+"' AND dob='"+sdob+"'";
                System.out.println(query);
                crs.setCommand(query);
                crs.execute(con.conn);
    
                // int rowCount = crs.size();
                // String[][] data = new String[rowCount][5];
                // crs.beforeFirst();
          while(crs.next()){
                    cityf.setText(crs.getString("city"));
                    agef.setText(crs.getString("age"));
                    dojf.setText(crs.getString("doj"));
                    addressf.setText(crs.getString("address"));
                    statef.setText(crs.getString("state"));
                    countryf.setText(crs.getString("country"));
                    mobilef.setText(crs.getString("mobile"));                    
                   
          }
              
          
          agef.setEditable(false);
          dojf.setEditable(false);
          addressf.setEditable(false);
          cityf.setEditable(false);
          statef.setEditable(false);
          mobilef.setEditable(false);
          countryf.setEditable(false);

                
    
                crs.close();
                stmt.close();
                con.conn.close();


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"User not exist");
                
                ex.printStackTrace();
            }
         }


else if(e.getSource()==edit){
    agef.setEditable(true);
    dojf.setEditable(true);
    addressf.setEditable(true);
    cityf.setEditable(true);
    statef.setEditable(true);
    mobilef.setEditable(true);
    countryf.setEditable(true);
}

else if(e.getSource()==save){

    try {
        Connection con = new Connection();
                // java.sql.Statement stmt = con.conn.createStatement();
                // CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                String sname=namef.getText();
                String sdob=dobf.getText();
                String query="UPDATE employeedetails SET doj=?,mobile=?,address=?,city=?,state=?,country=? where name='"+sname+"' AND dob='"+sdob+"'";

                PreparedStatement stm=con.conn.prepareStatement(query);
                stm.setString(1, dojf.getText());
                stm.setString(2, mobilef.getText());
                stm.setString(3, addressf.getText());
                stm.setString(4, cityf.getText());
                stm.setString(5, statef.getText());

                stm.setString(6, countryf.getText());


                int rowupdated=stm.executeUpdate();

                if(rowupdated>0){
                 JOptionPane.showMessageDialog(null,"Successfully Updated");

                }
                else{
            {JOptionPane.showMessageDialog(null,"failed");}

                }
                agef.setEditable(false);
                dojf.setEditable(false);
                addressf.setEditable(false);
                cityf.setEditable(false);
                statef.setEditable(false);
                mobilef.setEditable(false);
                countryf.setEditable(false);

                


    }catch(Exception err)
{
    err.printStackTrace();

}
}

else if(e.getSource()==delete){

    try {
        Connection con = new Connection();
                // java.sql.Statement stmt = con.conn.createStatement();
                // CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                String sname=namef.getText();
                String sdob=dobf.getText();
                String query="DELETE  FROM employeedetails where name='"+sname+"' AND dob='"+sdob+"'";
                // System.out.println(query);
                // crs.setCommand(query);
                // crs.updateRow();
                // crs.execute(con.conn);

                PreparedStatement stm= con.conn.prepareStatement(query);



                int rowsDeleted=stm.executeUpdate();


                namef.setText("");
                dobf.setText("");
                agef.setText("");
                addressf.setText("");

                dojf.setText("");

                cityf.setText("");
                countryf.setText("");
                mobilef.setText("");

                
if(rowsDeleted>0)
            {JOptionPane.showMessageDialog(null,"Successfully Deleted");}
            else{
        JOptionPane.showMessageDialog(null,"Deletion Failed !!");

            }

    
    } catch (Exception err) {
        JOptionPane.showMessageDialog(null,"Deletion Failed");

        // TODO: handle exception
    }
    
}


    }
    
}

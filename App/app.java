import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class second {
    public static void main(String[] args) {
        new Epp();

    }
}
class DataBaseApp{
    Connection con;
    String tablename,database;
    boolean connect(String database){
        this.database=database;
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database,"root","password");
            return true;
        }catch (SQLException ex){
            return false;
        }
    }
    boolean getTable(String tablename){
        this.tablename=tablename;
        try{
            con.createStatement().execute("SELECT * FROM "+tablename);
            return true;
        }catch (SQLException ex){
            return false;
        }
    }

    String[] collectField(){
        String query="SELECT * FROM "+tablename;
        String[] field=null;
        int SIZE;
        try(Statement stmt= con.createStatement()){
            ResultSet resultset=stmt.executeQuery(query);
            ResultSetMetaData metadata= resultset.getMetaData();
            SIZE=metadata.getColumnCount();
            field=new String[SIZE];
            for(int i=1;i<=SIZE;i++){
                field[i-1]=metadata.getColumnName(i);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return field;
    }
    java.util.List<String[]> collectRecords(){
        String query="SELECT * FROM "+tablename;
        List<String[]> records= new LinkedList<>();
        try(Statement stmt= con.createStatement()){
            ResultSet resultset=stmt.executeQuery(query);
            while(resultset.next()){
                ResultSetMetaData metadata= resultset.getMetaData();
                String[] temp= new String[metadata.getColumnCount()];
                for(int i=1;i<=metadata.getColumnCount();i++){
                    if(metadata.getColumnType(i)==Types.LONGVARCHAR)
                        temp[i-1]=resultset.getString(i);
                    else if(metadata.getColumnType(i)==Types.INTEGER)
                        temp[i-1]=Integer.toString(resultset.getInt(i));
                    else if(metadata.getColumnType(i)==Types.DATE)
                        temp[i-1]= resultset.getDate(i).toString();
                    else if(metadata.getColumnType(i)==Types.REAL)
                        temp[i-1]= Float.toString(resultset.getFloat(i));
                    else if(metadata.getColumnType(i)==Types.SMALLINT)
                        temp[i-1]= Short.toString(resultset.getShort(i));
                    else if(metadata.getColumnType(i)==Types.VARCHAR)
                        temp[i-1]= resultset.getString(i);
                    else if(metadata.getColumnType(i)==Types.TIMESTAMP)
                        temp[i-1]= resultset.getTimestamp(i).toString();
                }
                records.add(temp);
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return records;
    }
}
class Epp{
    JFrame frame;
    JTable table;
    JScrollPane sp;
    JPanel detailspanel,container;
    DataBaseApp db= new DataBaseApp();
    Epp(){
        run();

    }
    void run(){
        frame=new JFrame();
        container = new JPanel();
        detailspanel = new JPanel();

        detailspanel.setLayout((new BoxLayout(detailspanel,BoxLayout.Y_AXIS)));

        JLabel dblabel=new JLabel("database");
        JLabel tablelabel=new JLabel("table");
        JLabel connectlabel=new JLabel("connected");

        JTextField dbfield=new JTextField();
        dbfield.setMaximumSize(new Dimension(150,10));
        JTextField tablefield=new JTextField();
        tablefield.setMaximumSize(new Dimension(150,30));
        JButton connectbtn=new JButton("connect");

        container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
        table=new JTable();
        sp= new JScrollPane(table);

        JButton button= new JButton("refresh");

        ActionListener ac=(ActionEvent ae)->{
            table.setModel(generateModel());

        };
        button.addActionListener(ac);

        ActionListener connectac=(ActionEvent ae)->{
            if(db.connect(dbfield.getText()))
                connectlabel.setForeground(Color.green);
            else{
                connectlabel.setText("wrong db");
                connectlabel.setForeground(Color.RED);}

            if(db.getTable(tablefield.getText()))
                connectlabel.setForeground(Color.green);
            else{
                connectlabel.setText("wrong table");
            connectlabel.setForeground(Color.RED);}

        };
        connectbtn.addActionListener(connectac);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        detailspanel.add(dblabel);
        detailspanel.add(dbfield);
        detailspanel.add(tablelabel);
        detailspanel.add(tablefield);
        detailspanel.add(connectbtn);
        detailspanel.add(connectlabel);

        detailspanel.add(button);

        container.add(detailspanel);
        container.add(sp);
        frame.add(container);
        frame.setSize(400,400);
        frame.setVisible(true);


    }
    DefaultTableModel generateModel(){
        DefaultTableModel model= new DefaultTableModel();
        for(String name:db.collectField())
            model.addColumn(name);

        for(String[]record:db.collectRecords())
            model.addRow(record);
        return model;
    }

}

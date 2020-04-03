import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class Program {
    private JFrame window;
    private TopMenu topMenu;
    private JPanel sideMenu;
    private JTable dataTable;
    public Connection dbConnection;

    public Program() {
        window = new JFrame();
        window.setSize(new Dimension(1400,800));
        topMenu = new TopMenu(this);
        window.add(topMenu,BorderLayout.NORTH);
        setTable(new JTable());
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            dbConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/304proj",
                    "root",
                    ""
            );
        } catch (SQLException e) {
            System.out.println(";-;");
            System.out.println(e.getSQLState());
        }
        window.setVisible(true);


    }


    public static void main(String[] args) {
        Program p = new Program();
    }


    public void setTable(JTable table) {
        if(dataTable!=null){
            window.remove(dataTable);
        }
        table.setPreferredSize(new Dimension(500,500));
        dataTable = table;
        window.add(dataTable,BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }

    public void changeState(DisplayTableSelction displayTableSelction){
        if(sideMenu!=null){
            window.remove(sideMenu);
        }
        sideMenu = displayTableSelction.sidePanel;
        sideMenu.setPreferredSize(new Dimension(300,600));
        window.add(sideMenu,BorderLayout.EAST);
        setTable(displayTableSelction.getTable());

        window.repaint();
    }

}

//elections, countries, municiples, provinces, policies, candidates ,electoral districts, donations
//parties, leaders, campiegn events
//
//top menu is buttons for what you wanna see,
//side menu is things to filter what you see,
//each of the top menu items has their own filters
//

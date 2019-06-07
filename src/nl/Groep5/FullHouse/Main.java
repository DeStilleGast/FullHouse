package nl.Groep5.FullHouse;

import nl.Groep5.FullHouse.UI.InlogScherm;
import nl.Groep5.FullHouse.database.MySQLConnector;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class Main {

    private static MySQLConnector mysqlConnection;

    public static void main(String[] args) {
        if (!createDbConnection()) {
            return; // sluit applicatie
        }

        try {
            // zet de style naar de operatie systeem style (voor meeste van ons, windows style)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            System.out.println("Unsupported Look and feel, defaulting to normal");
        }

        new InlogScherm();

    }

    public static boolean createDbConnection() {
        if (mysqlConnection == null) {
            Properties props = new Properties();
            try {
                props.load(new FileInputStream("config.config"));
            } catch (IOException e) {
//            e.printStackTrace();

                try {
                    PrintWriter writer = new PrintWriter("config.config", "UTF-8");

                    writer.println("host=");
                    writer.println("database=");
                    writer.println("username=");
                    writer.println("password=");
                    writer.close();
                    System.out.println("config bestand gemaakt, bewerk deze voor gebruik");
                    return false;
                } catch (FileNotFoundException | UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }

            if (props.containsKey("host") && props.containsKey("database") && props.containsKey("username") && props.containsKey("password")) {
                mysqlConnection = new MySQLConnector(props.getProperty("host"), props.getProperty("database"), props.getProperty("username"), props.getProperty("password"));

                // Als de applicatie sluit, sluit dan ook de connectie met DB op een nette manier
                Runtime.getRuntime().addShutdownHook(new Thread(Main.mysqlConnection::close));

            } else {
                System.out.println("Missing mysql info, exiting");
                return false;
            }
        }
        // Geen fouten of db connectie bestaat al
        return true;
    }

    public static MySQLConnector getMySQLConnection() {
        return mysqlConnection;
    }

    /*
     Project medemogelijk gemaakt door:

        8888888b.            .d8888b.  888    d8b 888 888           .d8888b.                    888
        888  "Y88b          d88P  Y88b 888    Y8P 888 888          d88P  Y88b                   888
        888    888          Y88b.      888        888 888          888    888                   888
        888    888  .d88b.   "Y888b.   888888 888 888 888  .d88b.  888         8888b.  .d8888b  888888
        888    888 d8P  Y8b     "Y88b. 888    888 888 888 d8P  Y8b 888  88888     "88b 88K      888
        888    888 88888888       "888 888    888 888 888 88888888 888    888 .d888888 "Y8888b. 888
        888  .d88P Y8b.     Y88b  d88P Y88b.  888 888 888 Y8b.     Y88b  d88P 888  888      X88 Y88b.
        8888888P"   "Y8888   "Y8888P"   "Y888 888 888 888  "Y8888   "Y8888P88 "Y888888  88888P'  "Y888



        8888888b.
        888   Y88b
        888    888
        888   d88P  .d88b.  88888b.  .d8888b
        8888888P"  d8P  Y8b 888 "88b 88K
        888 T88b   88888888 888  888 "Y8888b.
        888  T88b  Y8b.     888  888      X88
        888   T88b  "Y8888  888  888  88888P'



        8888888888 d8b                   888 888    888                  d8b
        888        Y8P                   888 888    888                  Y8P
        888                              888 888    888
        8888888    888 88888b.   8888b.  888 8888888888  .d88b.  888d888 888 88888888  .d88b.  88888b.
        888        888 888 "88b     "88b 888 888    888 d88""88b 888P"   888    d88P  d88""88b 888 "88b
        888        888 888  888 .d888888 888 888    888 888  888 888     888   d88P   888  888 888  888
        888        888 888  888 888  888 888 888    888 Y88..88P 888     888  d88P    Y88..88P 888  888
        888        888 888  888 "Y888888 888 888    888  "Y88P"  888     888 88888888  "Y88P"  888  888



        8888888b.          d8b
        888   Y88b         Y8P
        888    888
        888   d88P 888d888 888 888  888  8888b.
        8888888P"  888P"   888 888  888     "88b
        888        888     888 888  888 .d888888
        888        888     888 Y88b 888 888  888
        888        888     888  "Y88888 "Y888888
                                    888
                               Y8b d88P
                                "Y88P"
     */
}

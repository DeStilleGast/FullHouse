package nl.Groep5.FullHouse.UI;

import nl.Groep5.FullHouse.UI.Masterclass.OverzichtInschrijvingenMasterclass;
import nl.Groep5.FullHouse.UI.Masterclass.SpelerMasterclassInschrijven;
import nl.Groep5.FullHouse.UI.Toernooi.OverzichtInschrijvingenToernooi;
import nl.Groep5.FullHouse.UI.Toernooi.SpelerToernooiInschrijven;
import nl.Groep5.FullHouse.UI.Toernooi.ToernooiResultaatScherm;
import nl.Groep5.FullHouse.UI.Toernooi.ToernooiTafelIndelingScherm;
import nl.Groep5.FullHouse.database.DatabaseHelper;
import nl.Groep5.FullHouse.database.impl.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainScherm {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel pnlSpelerOverzicht;
    private JTextField txtSpelerVoornaam;
    private JTextField txtSpelerTussenvoegsel;
    private JTextField txtSpelerAchternaam;
    private JTextField txtSpelerAdres;
    private JFormattedTextField txtSpelerPostcode;
    private JTextField txtSpelerWoonplaats;
    private JFormattedTextField txtSpelerTelefoonnummer;
    private JFormattedTextField txtSpelerGeboorteDatum;
    private JComboBox cbSpelerGeslacht;
    private TextFieldWithPlaceholder txtZoekSpelerInLijst;
    private JButton btnSpelerUitvoeren;

    private JTextField txtToernooiNaam;
    private JFormattedTextField txtToernooiDatum;
    private JFormattedTextField txtToernooiBeginTijd;
    private JFormattedTextField txtToernooiEindTijd;
    private JTextArea txtToernooiBeschrijving;
    private JFormattedTextField txtToernooiMaxInschrijvingen;
    private JTextField txtToernooiInleggeld;
    private JTextField txtToernooiSluitingInschrijving;
    private TextFieldWithPlaceholder txtToernooiZoeken;

    private JTextField txtMasterClassDatum;
    private JTextField txtMasterClassBeginTijd;
    private JTextField txtMasterClassEindDatum;
    private JTextField txtMasterClassKosten;
    private JTextField txtMasterClassMinRating;
    private JButton btnMasterclassUitvoeren;
    private TextFieldWithPlaceholder txtMasterClassZoeken;
    private JTable spelerTabel;
    private JScrollPane spelerScroll;
    private JScrollPane toernooiScroll;
    private JTable toernooiTabel;
    private JButton btnZoekenSpeler;
    private JComboBox spelerCombo;
    private JFormattedTextField txtSpelerRating;
    private JButton btnResetSpeler;
    private JTextField txtSpelerEmail;
    private JTextField txtSpelerGewonnenInleggeld;

    private JButton btnResetToernooi;
    private JButton btnZoekenToernooi;
    private JButton btnToernooiUitvoeren;
    private JComboBox cbToernooiUitvoeren;
    private JButton btnVerwerkWinnaars;
    private JComboBox cbToernooiLocaties;
    private JScrollPane masterclassScroll;
    private JTable masterclassTabel;
    private JButton btnMasterclassReset;
    private JButton btnMasterclassZoeken;
    private JComboBox cbMasterclassUitvoeren;
    private JScrollPane bekendeScroll;
    private JTable bekendeTabel;
    private TextFieldWithPlaceholder txtBekendeZoeken;
    private JButton btnBekendeZoeken;
    private JButton btnBekendeReset;
    private JButton btnBekendeUitvoeren;
    private JComboBox cbBekende;
    private JTextField txtBekendeSpelerNaam;
    private JButton btnSpelerUitloggen;
    private JButton btnToernooiUitloggen;


    public MainScherm() {
        JFrame frame = new JFrame("FullHouse");
        frame.add(mainPanel);
        frame.setMinimumSize(new Dimension(900, 500));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            new MaskFormatter("####UU").install(txtSpelerPostcode);
            new MaskFormatter("####-##-##").install(txtSpelerGeboorteDatum);
            new MaskFormatter("####-##-##").install(txtToernooiDatum);
            new MaskFormatter("##:##").install(txtToernooiBeginTijd);
            new MaskFormatter("##:##").install(txtToernooiEindTijd);
        }catch(ParseException fieldFormatError){
            fieldFormatError.printStackTrace();
        }
        spelerTabel.setModel(bouwSpelerTabel());

        tabbedPane1.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                switch(tabbedPane1.getSelectedIndex()){ //TODO: voeg update logic per tabblad toe.
                    case 0:
                        spelerTabel.setModel(bouwSpelerTabel());
                        break;
                    case 1:
                        toernooiTabel.setModel(bouwToernooienTabel());
                        break;
                    case 2:
                        masterclassTabel.setModel(bouwMasterclassTabel());
                        break;
                    case 3:
                        bekendeTabel.setModel(bouwBekendeSpelerTabel());
                }
            }
        });

        /*
        SPELER DEEL
         */
        spelerTabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try{
                    int selectedIndex = (int) spelerTabel.getValueAt(spelerTabel.getSelectedRow(), 0);
                    Speler geselecteerdeSpeler = DatabaseHelper.verkrijgSpelerBijId(selectedIndex);
                    txtSpelerVoornaam.setText(geselecteerdeSpeler.getVoornaam());
                    txtSpelerTussenvoegsel.setText(geselecteerdeSpeler.getTussenvoegsel());
                    txtSpelerAchternaam.setText(geselecteerdeSpeler.getAchternaam());
                    txtSpelerAdres.setText(geselecteerdeSpeler.getAdres());
                    txtSpelerPostcode.setText(geselecteerdeSpeler.getPostcode());
                    txtSpelerWoonplaats.setText(geselecteerdeSpeler.getWoonplaats());
                    txtSpelerTelefoonnummer.setText(geselecteerdeSpeler.getTelefoonnummer());
                    txtSpelerGeboorteDatum.setText(geselecteerdeSpeler.getGeboortedatum().toLocalDate().toString());
                    txtSpelerEmail.setText(geselecteerdeSpeler.getEmail());
                    txtSpelerRating.setText(String.valueOf(geselecteerdeSpeler.getRating()));
                    cbSpelerGeslacht.setSelectedItem((geselecteerdeSpeler.getGeslacht() == 'M')?"Man":"Vrouw");
                    txtSpelerGewonnenInleggeld.setText(DatabaseHelper.verkrijgToernooiUikomsten(geselecteerdeSpeler).stream().mapToDouble(ToernooiUitkomst::getPrijs).sum() + "");
                }catch(SQLException q){
                    q.printStackTrace();
                }
            }
        });
        btnSpelerUitvoeren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spelerTabel.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(frame, "U moet eerst een selectie maken voordat u een bewerking kunt doen.", "Waarschuwing",JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int selectedIndex = (int) spelerTabel.getValueAt(spelerTabel.getSelectedRow(), 0);
                        Speler geselecteerdeSpeler = DatabaseHelper.verkrijgSpelerBijId(selectedIndex);
                        switch (String.valueOf(spelerCombo.getItemAt(spelerCombo.getSelectedIndex()))) {
                            case "Bewerken":
                                boolean validated = true;
                                try {
                                    geselecteerdeSpeler.setVoornaam(txtSpelerVoornaam.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setTussenvoegsel(txtSpelerTussenvoegsel.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setAchternaam(txtSpelerAchternaam.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setAdres(txtSpelerAdres.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setPostcode(txtSpelerPostcode.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setWoonplaats(txtSpelerWoonplaats.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setTelefoonnummer(txtSpelerTelefoonnummer.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setGeboortedatum(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtSpelerGeboorteDatum.getText()).getTime()));
                                }catch(ParseException dateParseError){
                                    validated = false;
                                    dateParseError.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "De geboortedatum is incorrect ingevuld.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setRating(Double.parseDouble(txtSpelerRating.getText()));
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                geselecteerdeSpeler.setGeslacht(((cbSpelerGeslacht.getItemAt(cbSpelerGeslacht.getSelectedIndex()) == "Man")?'M':'V'));
                                if(validated && geselecteerdeSpeler.Update()) {
                                    JOptionPane.showMessageDialog(frame, "Bewerking succesvol uitgevoerd.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Registreren":
                                boolean reValidated = true;
                                try {
                                    geselecteerdeSpeler.setVoornaam(txtSpelerVoornaam.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setTussenvoegsel(txtSpelerTussenvoegsel.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setAchternaam(txtSpelerAchternaam.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setAdres(txtSpelerAdres.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setPostcode(txtSpelerPostcode.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setWoonplaats(txtSpelerWoonplaats.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setTelefoonnummer(txtSpelerTelefoonnummer.getText());
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setGeboortedatum(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtSpelerGeboorteDatum.getText()).getTime()));
                                }catch(ParseException dateParseError){
                                    reValidated = false;
                                    dateParseError.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "De geboortedatum is incorrect ingevuld.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdeSpeler.setRating(Double.parseDouble(txtSpelerRating.getText()));
                                }catch(Exception error){
                                    reValidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                geselecteerdeSpeler.setGeslacht(((cbSpelerGeslacht.getItemAt(cbSpelerGeslacht.getSelectedIndex()) == "Man")?'M':'V'));
                                if(reValidated && geselecteerdeSpeler.Save()) {
                                    JOptionPane.showMessageDialog(frame, "Gebruiker succesvol aangemaakt.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Verwijderen":
                                if(geselecteerdeSpeler.AVGClear()){
                                    JOptionPane.showMessageDialog(frame, "Bewerking succesvol uitgevoerd.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Fout bij het verwijderen.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Inschrijven voor toernooi":
                                try {
                                    SpelerToernooiInschrijven.show(geselecteerdeSpeler);
                                }catch (SQLException ex){
                                    JOptionPane.showMessageDialog(mainPanel, "Er is een fout opgetreden tijdens het ophalen van toernooi/speler gegevens", "Woeps", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace();
                                }
                                break;
                        }
                    } catch (SQLException uitvoerFout) {
                        uitvoerFout.printStackTrace();
                    }
                    int selectedRow = spelerTabel.getSelectedRow();
                    spelerTabel.setModel(bouwSpelerTabel());
                    spelerTabel.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });
        btnZoekenSpeler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spelerTabel.setModel(bouwSpelerZoekResultaten(txtZoekSpelerInLijst));
            }
        });
        btnResetSpeler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spelerTabel.setModel(bouwSpelerTabel());
            }
        });
        btnSpelerUitloggen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new InlogScherm();
            }
        });

        /*
         TOERNOOI DEEL
        **/
        toernooiTabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try{
                    int selectedIndex = (int) toernooiTabel.getValueAt(toernooiTabel.getSelectedRow(), 0);
                    Toernooi geselecteerdToernooi = DatabaseHelper.verkrijgToernooiById(selectedIndex);
                    List<Locatie> locatieLijst = DatabaseHelper.verkrijgLocatieLijst();
                    Locatie locatie = DatabaseHelper.verkrijgLocatieById(geselecteerdToernooi.getLocatieID());

                    txtToernooiNaam.setText(geselecteerdToernooi.getNaam());
                    txtToernooiDatum.setText(String.valueOf(geselecteerdToernooi.getDatum()));
                    txtToernooiBeginTijd.setText(geselecteerdToernooi.getBeginTijd());
                    txtToernooiEindTijd.setText(geselecteerdToernooi.getEindTijd());
                    txtToernooiBeschrijving.setText(geselecteerdToernooi.getBeschrijving());
                    txtToernooiMaxInschrijvingen.setText(String.valueOf(geselecteerdToernooi.getMaxAantalInschrijvingen()));
                    txtToernooiInleggeld.setText(String.valueOf(geselecteerdToernooi.getInleg()));
                    txtToernooiSluitingInschrijving.setText(String.valueOf(geselecteerdToernooi.getUitersteInschrijfDatum()));
                    cbToernooiLocaties.removeAllItems();
                    ArrayList<Integer> idIndex = new ArrayList<>();
                    for(Locatie element : locatieLijst){
                        cbToernooiLocaties.addItem(new ComboItem(element.getNaam(),String.valueOf(element.getID())));
                        idIndex.add(element.getID());
                    }
                    cbToernooiLocaties.setSelectedIndex(idIndex.indexOf(geselecteerdToernooi.getLocatieID()));
                }catch(SQLException q){
                    q.printStackTrace();
                }
            }
        });
        btnToernooiUitvoeren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toernooiTabel.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(frame, "U moet eerst een selectie maken voordat u een bewerking kunt doen.", "Waarschuwing",JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int selectedIndex = (int) toernooiTabel.getValueAt(toernooiTabel.getSelectedRow(), 0);
                        Toernooi geselecteerdToernooi = DatabaseHelper.verkrijgToernooiById(selectedIndex);
                        switch (String.valueOf(cbToernooiUitvoeren.getItemAt(cbToernooiUitvoeren.getSelectedIndex()))) {
                            case "Bewerken":
                                boolean validated = true;
                                try {
                                    geselecteerdToernooi.setNaam(txtToernooiNaam.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setDatum(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtToernooiDatum.getText()).getTime()));
                                }catch(ParseException dateParseError){
                                    validated = false;
                                    dateParseError.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "De datum is incorrect ingevuld.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setBeginTijd(txtToernooiBeginTijd.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setEindTijd(txtToernooiEindTijd.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setBeschrijving(txtToernooiBeschrijving.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setMaxInschrijvingen(Integer.valueOf(txtToernooiMaxInschrijvingen.getText()));
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setUitersteInschrijfDatum(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtToernooiSluitingInschrijving.getText()).getTime()));
                                }catch(ParseException dateParseError){
                                    validated = false;
                                    dateParseError.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "De inschrijf deadline datum is incorrect ingevuld.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setInleg(Double.parseDouble(txtToernooiInleggeld.getText()));
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try{
                                    String value = ((ComboItem)cbToernooiLocaties.getSelectedItem()).getValue();
                                    geselecteerdToernooi.setLocatieID(Integer.valueOf(value));
                                }catch (Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE); }
                                if(validated && geselecteerdToernooi.Update()) {
                                    JOptionPane.showMessageDialog(frame, "Bewerking succesvol uitgevoerd.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Aanmaken":
                                boolean Revalidated = true;
                                try {
                                    geselecteerdToernooi.setNaam(txtToernooiNaam.getText());
                                }catch(Exception error){
                                    Revalidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setDatum(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtToernooiDatum.getText()).getTime()));
                                }catch(ParseException dateParseError){
                                    Revalidated = false;
                                    dateParseError.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "De datum is incorrect ingevuld.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setBeginTijd(txtToernooiBeginTijd.getText());
                                }catch(Exception error){
                                    Revalidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setEindTijd(txtToernooiEindTijd.getText());
                                }catch(Exception error){
                                    Revalidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setBeschrijving(txtToernooiBeschrijving.getText());
                                }catch(Exception error){
                                    Revalidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setMaxInschrijvingen(Integer.valueOf(txtToernooiMaxInschrijvingen.getText()));
                                }catch(Exception error){
                                    Revalidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setUitersteInschrijfDatum(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(txtToernooiSluitingInschrijving.getText()).getTime()));
                                }catch(ParseException dateParseError){
                                    Revalidated = false;
                                    dateParseError.printStackTrace();
                                    JOptionPane.showMessageDialog(frame, "De inschrijf deadline datum is incorrect ingevuld.","Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                try {
                                    geselecteerdToernooi.setInleg(Double.parseDouble(txtToernooiInleggeld.getText()));
                                }catch(Exception error){
                                    Revalidated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                if(Revalidated && geselecteerdToernooi.Save()) {
                                    JOptionPane.showMessageDialog(frame, "Nieuw toernooi succesvol aangemaakt.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Speler inschrijven":
                                try {
                                    SpelerToernooiInschrijven.show(geselecteerdToernooi);
                                }catch (SQLException ex){
                                    JOptionPane.showMessageDialog(mainPanel, "Er is een fout opgetreden tijdens het ophalen van toernooi/speler gegevens", "Woeps", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace();
                                }
                                break;
                            case "Overzicht inschrijvingen":
                                OverzichtInschrijvingenToernooi.show(geselecteerdToernooi);
                                break;
                            case "Overzicht tafelindeling":
                                ToernooiTafelIndelingScherm.show(geselecteerdToernooi);
                                break;
                            case "Verwerk winnaars":
                                try {
                                    if(DatabaseHelper.verkrijgToernooiUikomsten(geselecteerdToernooi).isEmpty()) {
                                        ToernooiResultaatScherm.show(geselecteerdToernooi);
                                    }else {
                                        JOptionPane.showMessageDialog(mainPanel, "Er zijn al resultaten ingevoerd !", "Fout", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                } catch (SQLException | NullPointerException ex) {
                                    JOptionPane.showMessageDialog(mainPanel, "Er is een fout opgetreden tijdens het ophalen van toernooi gegevens", "Woeps", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace();
                                }
                                break;
                        }
                    } catch (SQLException uitvoerFout) {
                        uitvoerFout.printStackTrace();
                    }
                    int selectedRow = toernooiTabel.getSelectedRow();
                    toernooiTabel.setModel(bouwToernooienTabel());
                    toernooiTabel.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });
        btnZoekenToernooi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toernooiTabel.setModel(bouwToernooiZoekResultaten(txtToernooiZoeken));
            }
        });
        btnResetToernooi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toernooiTabel.setModel(bouwToernooienTabel());
            }
        });
        btnToernooiUitloggen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new InlogScherm();
            }
        });

        /*
        *MASTERCLASS DEEL
         */
        btnMasterclassUitvoeren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (masterclassTabel.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(frame, "U moet eerst een selectie maken voordat u een bewerking kunt doen.", "Waarschuwing", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int selectedIndex = (int) masterclassTabel.getValueAt(masterclassTabel.getSelectedRow(), 0);
                        MasterClass geselecteerdeMasterclass = DatabaseHelper.verkrijgMasterClassById(selectedIndex);
                        switch (String.valueOf(cbMasterclassUitvoeren.getItemAt(cbMasterclassUitvoeren.getSelectedIndex()))) {
                            case "Bewerken":
                                break;
                            case "Aanmaken":
                                break;
                            case "Speler inschrijven":
                                SpelerMasterclassInschrijven.show(geselecteerdeMasterclass);
                                break;
                            case "Overzicht inschrijvingen":
                                OverzichtInschrijvingenMasterclass.show(geselecteerdeMasterclass);
                                break;
                        }
                    } catch (SQLException uitvoerFout) {
                        uitvoerFout.printStackTrace();
                    }
                    int selectedRow = masterclassTabel.getSelectedRow();
                    masterclassTabel.setModel(bouwMasterclassTabel());
                    masterclassTabel.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });
        btnMasterclassZoeken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                masterclassTabel.setModel(bouwMasterclassZoekResultaten(txtMasterClassZoeken));
            }
        });
        btnMasterclassReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                masterclassTabel.setModel(bouwMasterclassTabel());
            }
        });

        /*
        *BEKENDE SPELER DEEL
         */
        bekendeTabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try{
                    int selectedIndex = (int) bekendeTabel.getValueAt(bekendeTabel.getSelectedRow(), 0);
                    BekendeSpeler geselecteerdeBekendeSpeler = DatabaseHelper.verkrijgBekendeSpelerBijId(selectedIndex);
                    txtBekendeSpelerNaam.setText(geselecteerdeBekendeSpeler.getPseudonaam());
                }catch(SQLException q){
                    q.printStackTrace();
                }
            }
        });
        btnBekendeUitvoeren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bekendeTabel.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(frame, "U moet eerst een selectie maken voordat u een bewerking kunt doen.", "Waarschuwing", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int selectedIndex = (int) bekendeTabel.getValueAt(bekendeTabel.getSelectedRow(), 0);
                        BekendeSpeler geselecteerdeBekendeSpeler = DatabaseHelper.verkrijgBekendeSpelerBijId(selectedIndex);
                        boolean validated = true;
                        switch (String.valueOf(cbBekende.getItemAt(cbBekende.getSelectedIndex()))) {
                            case "Bewerken":
                                try{
                                    geselecteerdeBekendeSpeler.setPseudonaam(txtBekendeSpelerNaam.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                if(validated && geselecteerdeBekendeSpeler.Update()) {
                                    JOptionPane.showMessageDialog(frame, "Gebruiker succesvol bewerkt.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Aanmaken":
                                try{
                                    geselecteerdeBekendeSpeler.setPseudonaam(txtBekendeSpelerNaam.getText());
                                }catch(Exception error){
                                    validated = false;
                                    JOptionPane.showMessageDialog(frame, error.getMessage(),"Fout", JOptionPane.ERROR_MESSAGE);
                                }
                                if(validated && geselecteerdeBekendeSpeler.Save()) {
                                    JOptionPane.showMessageDialog(frame, "Gebruiker succesvol aangemaakt.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Verwijderen":
                                if(geselecteerdeBekendeSpeler.Delete()) {
                                    JOptionPane.showMessageDialog(frame, "Gebruiker succesvol verwijdert.","Bericht", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(frame, "Database error! Neem contact op met een beheerder.","Fatal", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                        }
                    } catch (SQLException uitvoerFout) {
                        uitvoerFout.printStackTrace();
                    }
                    int selectedRow = bekendeTabel.getSelectedRow();
                    bekendeTabel.setModel(bouwBekendeSpelerTabel());
                    bekendeTabel.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });

    }


    public static DefaultTableModel bouwSpelerTabel(){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> spelerData = new Vector<>();
        try {
            List<Speler> spelerLijst = DatabaseHelper.verkrijgAlleSpelers();
            kollomNamen.add("ID");
            kollomNamen.add("Voornaam");
            kollomNamen.add("Tussenvoegsel");
            kollomNamen.add("Achternaam");
            kollomNamen.add("Rating");
            for (Speler element : spelerLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getID());
                vector.add(element.getVoornaam());
                vector.add(element.getTussenvoegsel());
                vector.add(element.getAchternaam());
                vector.add(element.getRating());
                spelerData.add(vector);
            }
        }catch(SQLException e){
        e.printStackTrace();
        }
        return new DefaultTableModel(spelerData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

    public static DefaultTableModel bouwSpelerZoekResultaten(TextFieldWithPlaceholder veld){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> spelerData = new Vector<>();
        try {
            List<Speler> spelerLijst = DatabaseHelper.verkrijgAlleSpelers(veld.getText());
            kollomNamen.add("ID");
            kollomNamen.add("Voornaam");
            kollomNamen.add("Tussenvoegsel");
            kollomNamen.add("Achternaam");
            kollomNamen.add("Rating");
            for (Speler element : spelerLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getID());
                vector.add(element.getVoornaam());
                vector.add(element.getTussenvoegsel());
                vector.add(element.getAchternaam());
                vector.add(element.getRating());
                spelerData.add(vector);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new DefaultTableModel(spelerData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

    public static DefaultTableModel bouwToernooienTabel(){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> toernooiData = new Vector<>();
        try {
            List<Toernooi> toernooienLijst = DatabaseHelper.verkrijgToernooien();
            kollomNamen.add("ID");
            kollomNamen.add("Naam");
            kollomNamen.add("Datum");
            kollomNamen.add("Begintijd");
            kollomNamen.add("Eindtijd");
            kollomNamen.add("Inschrijvingslimiet");
            kollomNamen.add("Inleg");
            kollomNamen.add("Inschrijfdeadline");
            kollomNamen.add("Locatie");
            for (Toernooi element : toernooienLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getID());
                vector.add(element.getNaam());
                vector.add(element.getDatum());
                vector.add(element.getBeginTijd());
                vector.add(element.getEindTijd());
                vector.add(element.getMaxAantalInschrijvingen());
                vector.add(element.getInleg());
                vector.add(String.valueOf(element.getUitersteInschrijfDatum()));
                vector.add(element.getLocatie().getNaam());
                toernooiData.add(vector);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new DefaultTableModel(toernooiData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

    public static DefaultTableModel bouwToernooiZoekResultaten(TextFieldWithPlaceholder veld){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> toernooiData = new Vector<>();
        try {
            List<Toernooi> toernooienLijst = DatabaseHelper.verkrijgToernooien(veld.getText());
            kollomNamen.add("ID");
            kollomNamen.add("Naam");
            kollomNamen.add("Datum");
            kollomNamen.add("Begintijd");
            kollomNamen.add("Eindtijd");
            kollomNamen.add("Inschrijvingslimiet");
            kollomNamen.add("Inleg");
            kollomNamen.add("Inschrijfdeadline");
            kollomNamen.add("Locatie");
            for (Toernooi element : toernooienLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getID());
                vector.add(element.getNaam());
                vector.add(element.getDatum());
                vector.add(element.getBeginTijd());
                vector.add(element.getEindTijd());
                vector.add(element.getMaxAantalInschrijvingen());
                vector.add(element.getInleg());
                vector.add(String.valueOf(element.getUitersteInschrijfDatum()));
                vector.add(element.getLocatie().getNaam());
                toernooiData.add(vector);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new DefaultTableModel(toernooiData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

    public static DefaultTableModel bouwMasterclassTabel(){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> masterclassData = new Vector<>();
        try {
            List<MasterClass> masterclassLijst = DatabaseHelper.verkrijgMasterClasses();
            kollomNamen.add("ID");
            kollomNamen.add("Datum");
            kollomNamen.add("beginTijd");
            kollomNamen.add("eindTijd");
            kollomNamen.add("kosten");
            kollomNamen.add("minRating");
            kollomNamen.add("maxInschrijvingen");
            for (MasterClass element : masterclassLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getID());
                vector.add(element.getDatum());
                vector.add(element.getBeginTijd());
                vector.add(element.getEindTijd());
                vector.add(element.getKosten());
                vector.add(element.getMinRating());
                vector.add(element.getMaxAantalInschrijvingen());
                masterclassData.add(vector);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new DefaultTableModel(masterclassData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

    public static DefaultTableModel bouwMasterclassZoekResultaten(TextFieldWithPlaceholder veld){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> masterclassData = new Vector<>();
        try {
            List<MasterClass> masterclassLijst = DatabaseHelper.verkrijgMasterClasses(veld.getText());
            kollomNamen.add("ID");
            kollomNamen.add("Datum");
            kollomNamen.add("beginTijd");
            kollomNamen.add("eindTijd");
            kollomNamen.add("kosten");
            kollomNamen.add("minRating");
            kollomNamen.add("maxInschrijvingen");
            for (MasterClass element : masterclassLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getID());
                vector.add(element.getDatum());
                vector.add(element.getBeginTijd());
                vector.add(element.getEindTijd());
                vector.add(element.getKosten());
                vector.add(element.getMinRating());
                vector.add(element.getMaxAantalInschrijvingen());
                masterclassData.add(vector);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new DefaultTableModel(masterclassData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

    public static DefaultTableModel bouwBekendeSpelerTabel(){
        Vector<String> kollomNamen = new Vector<>();
        Vector<Vector<Object>> bekendeSpelerData = new Vector<>();
        try {
            List<BekendeSpeler> masterclassLijst = DatabaseHelper.verkrijgBekendeSpelers();
            kollomNamen.add("ID");
            kollomNamen.add("Naam");
            for (BekendeSpeler element : masterclassLijst){
                Vector<Object> vector = new Vector<>();
                vector.add(element.getId());
                vector.add(element.getPseudonaam());
                bekendeSpelerData.add(vector);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new DefaultTableModel(bekendeSpelerData, kollomNamen){
            @Override
            public boolean isCellEditable(int row, int clumn){
                return false;
            }
        };
    }

}



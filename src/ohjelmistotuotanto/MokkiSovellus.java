package ohjelmistotuotanto;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;
import com.spire.doc.fields.Field;
import java.io.FileOutputStream;

import java.time.temporal.Temporal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.FontPosture;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import javax.swing.plaf.synth.SynthToggleButtonUI;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static ohjelmistotuotanto.LaskuLuokka.writeDataToDocument;

public class MokkiSovellus extends Application {

    private ObservableList<ObservableList> data;

    private TableView tableview;

    private final Button btMerkitsemaksetuksi = new Button("Merkitse lasku maksetuksi");

    private final Button btPoistavaraus = new Button("Poista varaus");

    private final Button btEimaksettu = new Button("Merkitse ei maksetuksi");

    private final Button btPoistalasku = new Button("Poista lasku");

    private final Button btLahetamuistutus = new Button("Lähetä muistutusmaksu");

    private final Button btSyotaSaatavuus = new Button("Syötä mökin saatavuustiedot");

    private final Button btHaelaskut = new Button("Hae kaikki laskut");

    private final Button btHaetiedot = new Button("Hae tiedot");

    private final Button btLuoLasku = new Button("Luo lasku");

    private final Button btLuoUusiLasku = new Button("Luo uusi lasku");

    private final Button btPoistaLasku = new Button("Poista lasku");

    private final Button btLaskujenSeuranta = new Button("Laskujen seuranta");

    private final Button ButtonCell = new Button("Varaustenhallinta");

    private final Button btTallenna = new Button("Tallenna");

    private final Button btTallennaPaivitetytTiedot = new Button("Tallenna");

    private final Button btSulje = new Button("Sulje ikkuna");

    private final Button btSulje2 = new Button("Sulje ikkuna");

    private final Button btSulje1 = new Button("Sulje ohjelma");

    private final Button btOk = new Button("Hae tiedot");

    private final Button btPdfraportti = new Button("Tee pdf raportti");

    private final Button btAsiakastiedot = new Button("Asiakastiedot");

    private final Button btMokkientiedot = new Button("Mökkien hallinta");

    private final Button btLaskujenhallinta = new Button("Laskujen hallinta");

    private final Button btPalveluidenhallinta = new Button("Palveluiden hallinta");

    private final Button btRaportointi = new Button("Raportin luominen");

    private final Button btAsiakas = new Button("Lisää uusi asiakas");

    private final Button btMuokkaaAsiakas = new Button("Muokkaa asiakastietoja");

    private final Button btVaraaMokki = new Button("Varaa mökki");

    private final Button btLisaaAlue = new Button("Lisää alue");

    private final Button btLisaaMokki = new Button("Lisää mökki");

    private final Button btlisaapalvelu = new Button("Lisää palvelu");

    private final Button btvaraapalvelu = new Button("Varaa palvelu");

    private final Button btMuokkaamokkia = new Button("Muokkaa mökin varausta");

    private final Button btMuokkaapalvelua = new Button("Muokkaa palvelujen varausta");

    private final Button btVaraustenhallinta = new Button("Varaustenhallinta");

    private final TextField tfLaskuid = new TextField();

    private final TextField tfEtunimi = new TextField();

    private final TextField tfSukunimi = new TextField();

    private final TextField tfLahiosoite = new TextField();

    private final TextField tfPostinumero = new TextField();

    private final TextField tfPuhelinnumero = new TextField();

    private final TextField tfSahkoposti = new TextField();

    private final TextField tfPuhelinnumero1 = new TextField();

    private final TextField tfAsiakasID = new TextField();

    private final TextField tfalue = new TextField();

    private final TextField tfpalvelunimi = new TextField();

    private final TextField tfpalvelutyyppi = new TextField();

    private final TextField tfVarausid = new TextField();

    private final TextField tfpalvelukuvaus = new TextField();

    private final TextField tfpalveluhinta = new TextField();

    private final TextField tfpalvelulkm = new TextField();

    private final TextField tfAlkupvm = new TextField();

    private final TextField tfLoppupvm = new TextField();

    private final Button btHaeTiedot = new Button("Hae tiedot");

    private final Button btHaeVaraukset = new Button("Hae varaukset");

    private final Button btPoistaTiedot = new Button("Poista asiakastiedot");

    private final Button btPoistaVaraus = new Button("Poista varaus");

    private final Button btTakaisinMokkienhallintaan = new Button("Takaisin");

    private final Button btTakaisinMokkienhallintaan1 = new Button("Takaisin");

    private final Button btTallennaVaraus = new Button("Tallenna varaus");

    private final Button btLuomokkiraportti = new Button("Majoittautumisten raportointi");

    private final Button btLuopalveluraportti = new Button("Palveluiden raportointi");

    private static final String SYOTA_PALVELU_SQL = "INSERT INTO palvelu"
            + " (alue_id, nimi, kuvaus, hinta, alv) VALUES"
            + "(?,?,?,?,?)";

    private static final String SYOTA_ASIAKAS_SQL = "INSERT INTO asiakas"
            + " (postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) VALUES"
            + " (?, ?, ?, ?, ?, ?);";

    private static final String SYOTA_ALUE_SQL = "INSERT INTO alue (nimi) VALUES (?)";

    private static final String TALLENNA_VARAUS_SQL = "INSERT INTO varaus"
            + "(asiakas_id, mokki_mokki_id, varattu_pvm, vahvistus_pvm, varattu_alkupvm, varattu_loppupvm) VALUES"
            + "(?, ?, ?, ?, ?, ?)";

    private static final String TALLENNA_SAATAVUUS_SQL = "INSERT INTO saatavuus"
            + "(mokki_mokki_id, saatavuus_alkupvm, saatavuus_loppupvm) VALUES"
            + "(?, ?, ?)";

    private static final String SYOTA_MOKKI_SQL = "INSERT INTO mokki"
            + "(alue_id, postinro, mokkinimi, katuosoite, hinta, kuvaus, henkilomaara, varustelu) VALUES"
            + "(?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String VALITTU_ASIAKAS_ID_SQL = "SELECT*FROM asiakas WHERE puhelinnro = ? ";

    private static final String VALITTU_MOKKI_ID_SQL = "SELECT*FROM mokki WHERE mokkinimi = ?";

    private static final String HAE_VARAUKSET_PUHELINNUMEROLLA_SQL = "SELECT varaus.varaus_id, asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, varaus.varattu_alkupvm, varaus.varattu_loppupvm, mokki.mokkinimi FROM asiakas JOIN varaus ON asiakas.asiakas_id = varaus.asiakas_id JOIN mokki ON varaus.mokki_mokki_id = mokki.mokki_id WHERE asiakas.puhelinnro = (?)";

    private static final String HAE_VARAUKSET_KAIKKI_SQL = "SELECT varaus.varaus_id, asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, varaus.varattu_alkupvm, varaus.varattu_loppupvm, mokki.mokkinimi FROM asiakas JOIN varaus ON asiakas.asiakas_id = varaus.asiakas_id JOIN mokki ON varaus.mokki_mokki_id = mokki.mokki_id";

    private static final String HAE_PALVELU_VARAUKSET_PUHELINNUMEROLLA_SQL = "SELECT varauksen_palvelut.varaus_id, asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, palvelu.nimi,varauksen_palvelut.lkm, palvelu.kuvaus, palvelu.hinta FROM varauksen_palvelut JOIN varaus varaus ON varauksen_palvelut.varaus_id = varaus.varaus_id JOIN asiakas asiakas ON varaus.asiakas_id = asiakas.asiakas_id JOIN palvelu palvelu ON varauksen_palvelut.palvelu_id = palvelu.palvelu_id WHERE asiakas.puhelinnro = ?";

    private static final String HAE_ALUEET_SQL = "SELECT*FROM alue";

    private static final String HAE_ASIAKKAAT_SQL = "SELECT*FROM asiakas";

    private static final String HAE_MOKIT_SQL = "SELECT mokki_id, mokkinimi from mokki";

    private static final String HAE_LASKUT_SQL = "Select lasku.lasku_id, lasku.summa, lasku.laskupvm, lasku.erapvm, asiakas.etunimi, asiakas.sukunimi, asiakas.lahiosoite, asiakas.email, lasku.maksusuoritus, lasku.maksumuistutus from lasku JOIN varaus varaus ON lasku.varaus_id = varaus.varaus_id JOIN asiakas asiakas ON varaus.asiakas_id = asiakas.asiakas_id";

    private static final String VALITTU_VARAUS_ID_SQL = "SELECT*FROM varaus WHERE varaus_id = ?";

    private static final String VALITTU_PALVELU_ID_SQL = "SELECT*FROM palvelu WHERE nimi = ?";

    private static final String TALLENNA_PALVELU_VARAUS_SQL = "INSERT INTO varauksen_palvelut (varaus_id, palvelu_id, lkm, paivamaara) VALUES (?,?,?,?)";

    private static final String EI_MAKSETTU_SQL = "UPDATE lasku SET maksusuoritus = 'Ei maksettu' WHERE lasku_id = ?";

    private static final String MAKSETTU_SQL = "UPDATE lasku SET maksusuoritus = 'Maksettu' WHERE lasku_id = ?";

    private static final String MOKKI_RAPORTTI_SQL = "select mokki.mokkinimi, COUNT(*) as 'Varattujen kertojen lkm', SUM(varaus.varauksen_kesto) as 'Varattujen öiden lkm', SUM(varaus.varauksen_kesto*mokki.hinta) AS 'tuotto' from mokki mokki JOIN varaus varaus on mokki.mokki_id = varaus.mokki_mokki_id";

    private static final String MOKKI_RAPORTTI_BETWEEN_SQL = "select mokki.mokkinimi, COUNT(*) as 'Varattujen kertojen lkm', SUM(varaus.varauksen_kesto) as 'Varattujen öiden lkm', SUM(varaus.varauksen_kesto*mokki.hinta) AS tuotto from mokki mokki join varaus varaus on varaus.mokki_mokki_id = mokki.mokki_id JOIN alue alue on mokki.alue_id = alue.alue_id where nimi = ? and (varattu_alkupvm between ? and  ?)";

    private static final String PALVELU_RAPORTTI_SQL = "select palvelu.nimi, COUNT(*) as 'Ostettujen palveluiden lkm', SUM(varauksen_palvelut.lkm) AS 'Asiakkaiden lkm', SUM(varauksen_palvelut.lkm*palvelu.hinta) as 'Tuotto' from palvelu palvelu JOIN varauksen_palvelut on varauksen_palvelut.palvelu_id = palvelu.palvelu_id JOIN alue alue ON palvelu.alue_id = alue.alue_id";

    private static final String PALVELU_RAPORTTI_BETWEEN_SQL = "select palvelu.nimi, COUNT(*) as 'Ostettujen palveluiden lkm', SUM(varauksen_palvelut.lkm) AS 'Asiakkaiden lkm', SUM(varauksen_palvelut.lkm*palvelu.hinta) as Tuotto from palvelu palvelu JOIN varauksen_palvelut on varauksen_palvelut.palvelu_id = palvelu.palvelu_id JOIN alue alue ON palvelu.alue_id = alue.alue_id where alue.nimi=? and (paivamaara BETWEEN ? and ?)";

    /**
     * Ohjelmaikkunan käynnistykseen ja toiminnallisuuden määrittely
     */
    @Override
    public void start(Stage primaryStage) {

        //Tehdään HBox, johon lisätään buttonit. Nämä näkyvät etusivulla.
        VBox asettelu = new VBox(15);
        asettelu.setPadding(new Insets(0, 0, 0, 0));
        btAsiakastiedot.setMaxSize(125, 200);
        btMokkientiedot.setMaxSize(125, 200);
        btLaskujenhallinta.setMaxSize(125, 200);
        btPalveluidenhallinta.setMaxSize(125, 200);
        btRaportointi.setMaxSize(125, 200);
        btSulje1.setMaxSize(125, 200);

        asettelu.getChildren().add(btAsiakastiedot);
        asettelu.getChildren().add(btMokkientiedot);
        asettelu.getChildren().add(btLaskujenhallinta);
        asettelu.getChildren().add(btPalveluidenhallinta);
        asettelu.getChildren().add(btRaportointi);
        asettelu.getChildren().add(btSulje1);

        //sijoitetaan hbox eli tässä tapauksessa buttonit vasemmalle alhaalle
        asettelu.setAlignment(Pos.CENTER);

        //tehdään toinen HBox johon sijoitetaan tervetulo teksti
        HBox toinenhbox = new HBox(15);
        Label uusi = new Label("Tervetuloa käyttämään Mokille.fi sovellusta!");
        uusi.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
        toinenhbox.getChildren().add(uusi);

        toinenhbox.setAlignment(Pos.TOP_CENTER);

        //tehdään stackpane, johon lisätään molemmat hboxit
        StackPane root = new StackPane();
        root.getChildren().addAll(toinenhbox, asettelu);

        //tehdään scene
        Scene scene = new Scene(root, 700, 500);

        primaryStage.setTitle("Mokille.fi");
        primaryStage.setScene(scene);
        primaryStage.show();

        //tehdään sulje1 buttonille ominaisuus, että se sulkee pääohjelman
        btSulje1.setOnAction(e -> {
            primaryStage.close();
        });

        //Mökkien tietoja
        btMokkientiedot.setOnAction((ActionEvent event) -> {
            GridPane mokkienHallintapaneeli = new GridPane();
            mokkienHallintapaneeli.add(btLisaaAlue, 0, 1);
            mokkienHallintapaneeli.add(btLisaaMokki, 0, 2);
            mokkienHallintapaneeli.add(btSyotaSaatavuus, 0, 3);
            mokkienHallintapaneeli.add(btVaraaMokki, 0, 4);
            mokkienHallintapaneeli.add(btTakaisinMokkienhallintaan1, 0, 5);

            btLisaaAlue.setMaxSize(400, 400);
            btLisaaMokki.setMaxSize(400, 400);
            btVaraaMokki.setMaxSize(400, 400);
            btTakaisinMokkienhallintaan1.setMaxSize(400, 400);

            mokkienHallintapaneeli.setAlignment(Pos.CENTER);
            mokkienHallintapaneeli.setPadding(new Insets(10));
            mokkienHallintapaneeli.setHgap(15);
            mokkienHallintapaneeli.setVgap(15);

            StackPane kolmasikkuna = new StackPane();

            kolmasikkuna.getChildren().addAll(mokkienHallintapaneeli);

            Scene uusiScene = new Scene(kolmasikkuna, 500, 300);

            // uusi ikkuna
            Stage mokkienHallintaikkuna = new Stage();
            mokkienHallintaikkuna.setTitle("Mökkienhallinta");
            mokkienHallintaikkuna.setScene(uusiScene);
            mokkienHallintaikkuna.show();

            btTakaisinMokkienhallintaan1.setOnAction(eE -> {
                mokkienHallintaikkuna.close();
            });

            btLisaaAlue.setOnAction(e -> {
                GridPane lisaaAluePaneeli = new GridPane();
                lisaaAluePaneeli.setAlignment(Pos.CENTER);
                lisaaAluePaneeli.setPadding(new Insets(10));
                lisaaAluePaneeli.setHgap(15);
                lisaaAluePaneeli.setVgap(15);

                TextField tfSyotaUusiAlue = new TextField();
                Label syotaUudenAlueenNimi = new Label("Syötä uuden alueen nimi");
                syotaUudenAlueenNimi.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label lisaysOnnistui = new Label("Lisäys onnistui");
                lisaysOnnistui.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Button btLisaa = new Button("Lisää");

                btLisaa.setOnAction(eX -> {
                    String alueNimi = tfSyotaUusiAlue.getText();

                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                            PreparedStatement preparedStatement = connection.prepareStatement(SYOTA_ALUE_SQL)) {
                        preparedStatement.setString(1, alueNimi);

                        System.out.println(preparedStatement);
                        preparedStatement.executeUpdate();

                        lisaaAluePaneeli.add(lisaysOnnistui, 0, 4);

                    } catch (SQLException ex) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

                btTakaisinMokkienhallintaan.setOnAction(eE -> {
                    mokkienHallintaikkuna.setScene(uusiScene);
                });

                lisaaAluePaneeli.add(syotaUudenAlueenNimi, 0, 1);
                lisaaAluePaneeli.add(tfSyotaUusiAlue, 0, 2);
                lisaaAluePaneeli.add(btLisaa, 0, 3);
                lisaaAluePaneeli.add(btTakaisinMokkienhallintaan, 0, 5);

                Scene lisaaAlueIkkuna = new Scene(lisaaAluePaneeli, 500, 300);
                mokkienHallintaikkuna.setScene(lisaaAlueIkkuna);

            });

            btLisaaMokki.setOnAction(e -> {
                GridPane lisaaMokkiPaneeli = new GridPane();
                lisaaMokkiPaneeli.setAlignment(Pos.CENTER);
                lisaaMokkiPaneeli.setPadding(new Insets(10));
                lisaaMokkiPaneeli.setHgap(15);
                lisaaMokkiPaneeli.setVgap(15);

                final SearchableComboBox alueenvalintaComboBox = new SearchableComboBox();

                //Lista alueille
                ObservableList<String> alueetValittava
                        = FXCollections.observableArrayList();

                //Yhteyden avaus ja alueet kannasta listaan
                try {

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    PreparedStatement preparedStatement = connection.prepareStatement(HAE_ALUEET_SQL);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        int alueId = resultSet.getInt("alue_id");
                        String alueNimi = resultSet.getString("nimi");
                        String alue = alueId + "\t" + alueNimi;
                        alueetValittava.add(alue);

                    }
                    alueenvalintaComboBox.setItems(alueetValittava);
                    resultSet.close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                DatePicker saatavuusAlku = new DatePicker(LocalDate.now());

                DatePicker saatavuusLoppu = new DatePicker(LocalDate.now());

                Label valitseAlue = new Label("Valitse alue jolle haluat lisätä mökin");
                valitseAlue.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiNimi = new TextField();
                Label syotaUudenMokinNimi = new Label("Syötä uuden mökin nimi");
                syotaUudenMokinNimi.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiKatuOsoite = new TextField();
                Label syotaUudenMokinKatuOsoite = new Label("Syötä katuosoite");
                syotaUudenMokinKatuOsoite.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiPostinumero = new TextField();
                Label syotaUudenMokinPostinumero = new Label("Syötä postinumero");
                syotaUudenMokinKatuOsoite.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiKuvaus = new TextField();
                tfSyotaUusiMokkiKuvaus.setMinWidth(100);
                Label syotaUudenMokinKuvaus = new Label("Syötä kuvaus");
                syotaUudenMokinKuvaus.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiVarustelu = new TextField();
                tfSyotaUusiMokkiVarustelu.setMinWidth(100);
                Label syotaUudenMokinVarustelu = new Label("Syötä varustelu");
                syotaUudenMokinVarustelu.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiHenkilomaara = new TextField();
                Label syotaUudenMokinHenkilomaara = new Label("Syötä max. henkilömäärä");
                syotaUudenMokinHenkilomaara.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                TextField tfSyotaUusiMokkiHinta = new TextField();
                Label syotaUudenMokinHinta = new Label("Syötä mökin hinta/yö");
                syotaUudenMokinHinta.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

                Label syotaUudenMokinSaatavuus = new Label("Syötä saatavuus:");

                Label lisaysOnnistui = new Label("Lisäys onnistui");
                lisaysOnnistui.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Button btLisaa = new Button("Lisää");

                btTakaisinMokkienhallintaan.setOnAction(eE -> {
                    mokkienHallintaikkuna.setScene(uusiScene);
                });

                lisaaMokkiPaneeli.add(valitseAlue, 0, 0);
                lisaaMokkiPaneeli.add(alueenvalintaComboBox, 0, 1);
                lisaaMokkiPaneeli.add(syotaUudenMokinNimi, 0, 2);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiNimi, 0, 3);
                lisaaMokkiPaneeli.add(syotaUudenMokinKatuOsoite, 0, 4);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiKatuOsoite, 0, 5);
                lisaaMokkiPaneeli.add(syotaUudenMokinPostinumero, 0, 6);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiPostinumero, 0, 7);
                lisaaMokkiPaneeli.add(syotaUudenMokinKuvaus, 0, 8);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiKuvaus, 0, 9);
                lisaaMokkiPaneeli.add(syotaUudenMokinVarustelu, 0, 10);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiVarustelu, 0, 11);
                lisaaMokkiPaneeli.add(syotaUudenMokinHenkilomaara, 0, 12);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiHenkilomaara, 0, 13);
                lisaaMokkiPaneeli.add(syotaUudenMokinHinta, 0, 14);
                lisaaMokkiPaneeli.add(tfSyotaUusiMokkiHinta, 0, 15);
                lisaaMokkiPaneeli.add(btLisaa, 0, 16);
                lisaaMokkiPaneeli.add(btTakaisinMokkienhallintaan, 0, 17);

                btLisaa.setOnAction(eX -> {
                    StringBuilder sb4 = new StringBuilder(alueenvalintaComboBox.getValue().toString());
                    String alueenTiedotValittu = sb4.toString();
                    String[] aluelista = alueenTiedotValittu.split("\\t");
                    int alueenIdValittu = parseInt(aluelista[0]);

                    String mokkiNimi = tfSyotaUusiMokkiNimi.getText();
                    String mokkiOsoite = tfSyotaUusiMokkiKatuOsoite.getText();
                    String mokkiPostinro = tfSyotaUusiMokkiPostinumero.getText();
                    String mokkiKuvaus = tfSyotaUusiMokkiKuvaus.getText();
                    String mokkiVarustelu = tfSyotaUusiMokkiVarustelu.getText();
                    int mokkiHenkilot = parseInt(tfSyotaUusiMokkiHenkilomaara.getText());
                    double mokkiHinta = parseDouble(tfSyotaUusiMokkiHinta.getText());

                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                            PreparedStatement preparedStatement = connection.prepareStatement(SYOTA_MOKKI_SQL)) {
                        preparedStatement.setInt(1, alueenIdValittu);
                        preparedStatement.setString(2, mokkiPostinro);
                        preparedStatement.setString(3, mokkiNimi);
                        preparedStatement.setString(4, mokkiOsoite);
                        preparedStatement.setDouble(5, mokkiHinta);
                        preparedStatement.setString(6, mokkiKuvaus);
                        preparedStatement.setInt(7, mokkiHenkilot);
                        preparedStatement.setString(8, mokkiVarustelu);
                        preparedStatement.executeUpdate();

                        lisaaMokkiPaneeli.add(lisaysOnnistui, 0, 17);

                    } catch (SQLException ex) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                Scene lisaaMokkiIkkuna = new Scene(lisaaMokkiPaneeli, 500, 700);
                mokkienHallintaikkuna.setScene(lisaaMokkiIkkuna);

            });

            btSyotaSaatavuus.setOnAction(e -> {

                GridPane mokkienSaatavuusHakuPaneeli = new GridPane();
                DatePicker saatavuusAlku = new DatePicker(LocalDate.now());
                DatePicker saatavuusLoppu = new DatePicker(LocalDate.now());
                final SearchableComboBox mokinvalintaComboBox = new SearchableComboBox();

                mokkienSaatavuusHakuPaneeli.add(new Label("Hae mökki: "), 0, 1);
                mokkienSaatavuusHakuPaneeli.add(mokinvalintaComboBox, 0, 2);
                mokkienSaatavuusHakuPaneeli.add(new Label("Syötä mökin saatavuus: "), 0, 3);
                mokkienSaatavuusHakuPaneeli.add(saatavuusAlku, 0, 4);
                mokkienSaatavuusHakuPaneeli.add(saatavuusLoppu, 0, 5);
                mokkienSaatavuusHakuPaneeli.add(btTallenna, 0, 6);

                mokkienSaatavuusHakuPaneeli.setAlignment(Pos.CENTER);
                mokkienSaatavuusHakuPaneeli.setPadding(new Insets(10));
                mokkienSaatavuusHakuPaneeli.setHgap(15);
                mokkienSaatavuusHakuPaneeli.setVgap(15);

                //Lista alueille
                ObservableList<String> alueetValittava
                        = FXCollections.observableArrayList();

                int mokkiIdValittu = 0;
                try {

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    PreparedStatement preparedStatement = connection.prepareStatement(HAE_MOKIT_SQL);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        int mokki_id = resultSet.getInt("mokki_id");
                        String mokkiNimi = resultSet.getString("mokkinimi");
                        String alue = mokki_id + "\t" + mokkiNimi;
                        alueetValittava.add(alue);

                    }
                    mokinvalintaComboBox.setItems(alueetValittava);
                    resultSet.close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                btTallenna.setOnAction(ex -> {
                    int mokki_id2 = 0;

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement2 = connection.prepareStatement(HAE_MOKIT_SQL);

                        ResultSet resultSet2 = preparedStatement2.executeQuery();

                        System.out.println(HAE_MOKIT_SQL);

                        while (resultSet2.next()) {
                            mokki_id2 = resultSet2.getInt("mokki_id");
                        }
                        resultSet2.close();

                        PreparedStatement preparedStatement = connection.prepareStatement(TALLENNA_SAATAVUUS_SQL);
                        {
                            preparedStatement.setInt(1, mokki_id2);
                            preparedStatement.setDate(2, Date.valueOf(saatavuusAlku.getValue()));
                            preparedStatement.setDate(3, Date.valueOf(saatavuusLoppu.getValue()));

                            preparedStatement.executeUpdate();
                        }
                        System.out.println(TALLENNA_SAATAVUUS_SQL);
                        //mokkienVarauspaneeli.add(varausOnnistui, 0, 11);
                    } catch (SQLException exe) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, exe);
                    }

                });

                Scene MokkiSaatavuusIkkuna = new Scene(mokkienSaatavuusHakuPaneeli, 400, 400);
                mokkienHallintaikkuna.setScene(MokkiSaatavuusIkkuna);

            });

            //Varaa mökki namiskan toiminnot
            btVaraaMokki.setOnAction(e -> {
                GridPane mokkienHakuPaneeli = new GridPane();
                Scene scene3 = new Scene(mokkienHakuPaneeli, 500, 600);

                Label asiakasLabel = new Label("Valitse asiakas");
                asiakasLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label alueLabel = new Label("Valitse alue");
                alueLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label mokkiLabel = new Label("Valitse mökki");
                mokkiLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label alkuLabel = new Label("Valitse alkupäivämäärä");
                alkuLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label loppuLabel = new Label("Valitse loppupäivämäärä");
                loppuLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label hintaMinLabel = new Label("Valitse min hinta");
                hintaMinLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label hintaMaxLabel = new Label("Valitse max hinta");
                hintaMaxLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));
                Label varusteluLabel = new Label("Valitse varustelu");
                varusteluLabel.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));

                TextField tfHintaMax = new TextField();
                TextField tfHintaMin = new TextField();
                TextField tfVarustelu = new TextField();

                //Alueiden valinta
                final SearchableComboBox alueComboBox = new SearchableComboBox();

                //Lista alueille
                ObservableList<String> alueet
                        = FXCollections.observableArrayList();

                //Yhteyden avaus ja alueet kannasta listaan
                try {

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    Statement statement = connection.createStatement();

                    PreparedStatement preparedStatement = connection.prepareStatement(HAE_ALUEET_SQL);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        alueet.add(resultSet.getString("nimi"));
                        alueComboBox.setItems(alueet);

                    }
                    resultSet.close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                //Mökkien valinta
                final SearchableComboBox mokkiComboBox = new SearchableComboBox();

                //Lista mökeille
                ObservableList<String> mokit
                        = FXCollections.observableArrayList();

                //mökkien haku sen jälkeen kun alue on valittu
                //kalenterit varauksen alku ja loppupvm valinnalle
                DatePicker varausAlku = new DatePicker(LocalDate.now());

                DatePicker varausLoppu = new DatePicker(LocalDate.now());

                //varauksen tallentaminen
                btTakaisinMokkienhallintaan.setOnAction(eE -> {
                    mokkienHallintaikkuna.setScene(uusiScene);
                });

                //varauspaneeliin comboboxit, labelit ja tallenna varaus namiska
                mokkienHakuPaneeli.add(alueLabel, 0, 0);
                mokkienHakuPaneeli.add(alueComboBox, 0, 1);

                mokkienHakuPaneeli.add(hintaMinLabel, 0, 2);
                mokkienHakuPaneeli.add(tfHintaMin, 0, 3);

                mokkienHakuPaneeli.add(hintaMaxLabel, 0, 4);
                mokkienHakuPaneeli.add(tfHintaMax, 0, 5);

                mokkienHakuPaneeli.add(varusteluLabel, 0, 6);
                mokkienHakuPaneeli.add(tfVarustelu, 0, 7);

                mokkienHakuPaneeli.add(alkuLabel, 0, 8);
                mokkienHakuPaneeli.add(varausAlku, 0, 9);

                mokkienHakuPaneeli.add(loppuLabel, 0, 10);
                mokkienHakuPaneeli.add(varausLoppu, 0, 11);

                Button btHaeMokit = new Button("Hae mökit");

                btHaeMokit.setOnAction(eeee -> {
                    GridPane mokkienVarausPaneeli = new GridPane();
                    Scene scene4 = new Scene(mokkienVarausPaneeli, 500, 600);
                    mokkienHallintaikkuna.setScene(scene4);

                    try {
                        //tämä komento kaataa ohjelman jos vaihtaa aluetta ja hakee mökit uudestaan??
                        mokit.clear();

                        String alueValittu = String.valueOf(alueComboBox.getValue());

                        final String HAE_ALUE_VALITTU = "SELECT * FROM `alue` WHERE `nimi`='" + alueValittu + "'";

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement1 = connection.prepareStatement(HAE_ALUE_VALITTU);

                        ResultSet resultSet1 = preparedStatement1.executeQuery();

                        int alueIdValittu = 0;

                        while (resultSet1.next()) {
                            alueIdValittu = resultSet1.getInt("alue_id");
                        }

                        final String HAE_MOKIT_EHDOILLA_SQL = "SELECT distinct mokki.mokkinimi"
                                + "                            FROM mokki"
                                + "                            JOIN saatavuus"
                                + "                            ON mokki.mokki_id = saatavuus.mokki_mokki_id "
                                + "                            JOIN varaus "
                                + "                            ON varaus.mokki_mokki_id = mokki.mokki_id"
                                + "                            WHERE "
                                + "                            (hinta between ? and ?)"
                                + "                            AND (?  between saatavuus_alkupvm and saatavuus_loppupvm)"
                                + "                            AND (?  between saatavuus_alkupvm and saatavuus_loppupvm)"
                                + "                            AND NOT (varattu_alkupvm between ? and ?)"
                                + "                            AND NOT (varattu_loppupvm between ? and ?)"
                                + "                            AND (alue_id = ?)"
                                + "                            AND (varustelu LIKE ?)";

                        PreparedStatement preparedStatement2 = connection.prepareStatement(HAE_MOKIT_EHDOILLA_SQL);

                        preparedStatement2.setDouble(1, parseDouble(tfHintaMin.getText()));
                        preparedStatement2.setDouble(2, parseDouble(tfHintaMax.getText()));
                        preparedStatement2.setDate(3, Date.valueOf(varausAlku.getValue()));
                        preparedStatement2.setDate(4, Date.valueOf(varausLoppu.getValue()));
                        preparedStatement2.setDate(5, Date.valueOf(varausAlku.getValue()));
                        preparedStatement2.setDate(6, Date.valueOf(varausLoppu.getValue()));
                        preparedStatement2.setDate(7, Date.valueOf(varausAlku.getValue()));
                        preparedStatement2.setDate(8, Date.valueOf(varausLoppu.getValue()));
                        preparedStatement2.setInt(9, alueIdValittu);
                        preparedStatement2.setString(10, ("%" + tfVarustelu.getText() + "%"));

                        ResultSet resultSet = preparedStatement2.executeQuery();
                        System.out.println(preparedStatement2);

                        while (resultSet.next()) {
                            String mnimi = resultSet.getString("mokkinimi");
                            mokit.add(mnimi);
                        }

                        resultSet.close();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    mokkiComboBox.setItems(mokit);

                    //Asiakkaan valinta, combobox etsi ominaisuudella
                    final SearchableComboBox asiakasComboBox = new SearchableComboBox();

                    //Lista comboboxille
                    ObservableList<String> asiakkaat
                            = FXCollections.observableArrayList();

                    //yhteyden avaus ja asiakkaiden tiedot listaan
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement = connection.prepareStatement(HAE_ASIAKKAAT_SQL);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            String enimi = resultSet.getString("etunimi");
                            String snimi = resultSet.getString("sukunimi");
                            String pnro = resultSet.getString("puhelinnro");
                            String em = resultSet.getString("email");
                            String asiakas = enimi + "\t" + snimi + "\t" + pnro + "\t" + em;
                            asiakkaat.add(asiakas);
                        }
                        asiakasComboBox.setItems(asiakkaat);

                        resultSet.close();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    btTallennaVaraus.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            StringBuilder sb1 = new StringBuilder(mokkiComboBox.getValue().toString());
                            String mokinTiedotValittu = sb1.toString();
                            String[] mokkilista = mokinTiedotValittu.split("\\t");
                            String mokinNimiValittu = mokkilista[0];

                            StringBuilder sb2 = new StringBuilder(asiakasComboBox.getValue().toString());
                            String asiakkaanTiedotValittu = sb2.toString();
                            String[] asiakaslista = asiakkaanTiedotValittu.split("\\t");
                            String asiakkaanPuhNroValittu = asiakaslista[2];

                            int asiakasIdValittu = 0;
                            int mokkiIdValittu = 0;

                            Label varausOnnistui = new Label("Varaus onnistui");
                            varausOnnistui.setFont(Font.font("Verdana", FontPosture.REGULAR, 14));

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                                PreparedStatement preparedStatement1 = connection.prepareStatement(VALITTU_ASIAKAS_ID_SQL);
                                preparedStatement1.setString(1, asiakkaanPuhNroValittu);
                                ResultSet resultSet1 = preparedStatement1.executeQuery();

                                if (resultSet1.next()) {
                                    asiakasIdValittu = resultSet1.getInt("asiakas_id");

                                }
                                PreparedStatement preparedStatement2 = connection.prepareStatement(VALITTU_MOKKI_ID_SQL);
                                preparedStatement2.setString(1, mokinNimiValittu);
                                ResultSet resultSet2 = preparedStatement2.executeQuery();

                                if (resultSet2.next()) {
                                    mokkiIdValittu = resultSet2.getInt("mokki_id");
                                }

                                PreparedStatement preparedStatement = connection.prepareStatement(TALLENNA_VARAUS_SQL);
                                {
                                    preparedStatement.setInt(1, asiakasIdValittu);
                                    preparedStatement.setInt(2, mokkiIdValittu);
                                    preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
                                    preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
                                    preparedStatement.setDate(5, Date.valueOf(varausAlku.getValue()));
                                    preparedStatement.setDate(6, Date.valueOf(varausLoppu.getValue()));

                                    preparedStatement.executeUpdate();
                                }
                                //mokkienVarauspaneeli.add(varausOnnistui, 0, 11);
                            } catch (SQLException ex) {
                                Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

                    TextArea tAmokeille = new TextArea();
                    tAmokeille.setEditable(false);
                    tAmokeille.setOnMouseClicked(eeeee -> {
                        try {

                            StringBuilder sb5 = new StringBuilder(mokkiComboBox.getValue().toString());
                            String mokinTiedotValittu = sb5.toString();
                            String[] mokkilista = mokinTiedotValittu.split("\\t");
                            String mokinNimiValittu = mokkilista[0];

                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                            String HAE_MOKKIEN_TIEDOT_SQL = "select mokkinimi, katuosoite, hinta, kuvaus, varustelu, henkilomaara from mokki where mokkinimi = ?";

                            PreparedStatement preparedStatement = connection.prepareStatement(HAE_MOKKIEN_TIEDOT_SQL);

                            preparedStatement.setString(1, mokinNimiValittu);

                            ResultSet resultSet = preparedStatement.executeQuery();

                            while (resultSet.next()) {
                                tAmokeille.clear();
                                tAmokeille.appendText("Mökin nimi: ");
                                tAmokeille.appendText(resultSet.getString("mokkinimi") + " \nKatuosoite: ");
                                tAmokeille.appendText(resultSet.getString("katuosoite") + " \nHinta per yö: ");
                                tAmokeille.appendText(resultSet.getString("hinta") + " \nMökin kuvaus: ");
                                tAmokeille.appendText(resultSet.getString("kuvaus") + " \nVarustelu: ");
                                tAmokeille.appendText(resultSet.getString("varustelu") + " \nMax. henkilömäärä: ");
                                tAmokeille.appendText(resultSet.getString("henkilomaara") + " \n");

                            }
                            resultSet.close();

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                    });

                    mokkienVarausPaneeli.add(mokkiLabel, 0, 0);
                    mokkienVarausPaneeli.add(mokkiComboBox, 0, 1);

                    mokkienVarausPaneeli.add(tAmokeille, 0, 2);

                    mokkienVarausPaneeli.add(asiakasLabel, 0, 3);
                    mokkienVarausPaneeli.add(asiakasComboBox, 0, 4);

                    mokkienVarausPaneeli.add(btTallennaVaraus, 0, 5);
                    mokkienVarausPaneeli.add(btTakaisinMokkienhallintaan, 0, 6);

                    mokkienVarausPaneeli.setAlignment(Pos.CENTER);
                    mokkienVarausPaneeli.setPadding(new Insets(10));
                    mokkienVarausPaneeli.setHgap(15);
                    mokkienVarausPaneeli.setVgap(15);

                });

                mokkienHakuPaneeli.add(btHaeMokit, 0, 12);
                mokkienHakuPaneeli.add(btTakaisinMokkienhallintaan, 0, 13);

                //paneelin muotoilua
                mokkienHakuPaneeli.setAlignment(Pos.CENTER);
                mokkienHakuPaneeli.setPadding(new Insets(10));
                mokkienHakuPaneeli.setHgap(15);
                mokkienHakuPaneeli.setVgap(15);

                mokkienHallintaikkuna.setScene(scene3);

            });

            //tää ehkä turha
            btTallenna.setOnAction(e -> {

            });

            btSulje.setOnAction(e -> {
                mokkienHallintaikkuna.close();
            });
        });

        //tähän tulee laskujen hallinta
        btLaskujenhallinta.setOnAction((ActionEvent event) -> {
            GridPane laskujenHallintaGridPaneeli = new GridPane();
            laskujenHallintaGridPaneeli.setAlignment(Pos.CENTER);

            StackPane laskujenHallintaStackPane = new StackPane();
            laskujenHallintaStackPane.getChildren().addAll(laskujenHallintaGridPaneeli);
            Scene laskujenHallintaScene = new Scene(laskujenHallintaStackPane, 600, 400);

            Stage laskujenHallintaIkkuna = new Stage();
            laskujenHallintaIkkuna.setTitle("Laskujenhallinta");
            laskujenHallintaIkkuna.setScene(laskujenHallintaScene);
            laskujenHallintaIkkuna.show();

            btLuoUusiLasku.setOnAction(e -> {
                GridPane luoUusiLaskuGridPaneeli = new GridPane();
                Scene uusiLaskuScene = new Scene(luoUusiLaskuGridPaneeli, 600, 400);
                laskujenHallintaIkkuna.setScene(uusiLaskuScene);

                tfPuhelinnumero1.clear();

                GridPane lisaaMuokkaaMokkiPaneeli = new GridPane();
                lisaaMuokkaaMokkiPaneeli.add(new Label("Puhelinnumero: "), 9, 0);
                lisaaMuokkaaMokkiPaneeli.add(btLuoLasku, 9, 1);
                lisaaMuokkaaMokkiPaneeli.add(btHaeVaraukset, 9, 2);
                lisaaMuokkaaMokkiPaneeli.add(btTakaisinMokkienhallintaan, 9, 3);
                lisaaMuokkaaMokkiPaneeli.setHgap(10);
                lisaaMuokkaaMokkiPaneeli.setVgap(10);

                Label label = new Label("Mökkivaraukset");
                label.setFont(Font.font("Calibri", 36));

                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().add(label);

                TableView tableView = new TableView();
                tableView.setPrefWidth(450);
                tableView.setPrefHeight(450);

                VBox vbox = new VBox(20);
                vbox.setPadding(new Insets(25, 25, 25, 25));
                vbox.getChildren().addAll(hb, tableView, lisaaMuokkaaMokkiPaneeli);

                data = FXCollections.observableArrayList();
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    String SQL = "SELECT varaus.varaus_id, asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, varaus.varattu_alkupvm, varaus.varattu_loppupvm, mokki.mokkinimi FROM asiakas asiakas JOIN varaus varaus ON asiakas.asiakas_id = varaus.asiakas_id JOIN mokki ON varaus.mokki_mokki_id = mokki.mokki_id";

                    ResultSet rs = connection.createStatement().executeQuery(SQL);

                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            }
                        });

                        tableView.getColumns().addAll(col);
                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("eipä tainnu onnistua");
                }

                tableView.setOnMouseClicked(eee -> {

                    System.out.println(tableView.getSelectionModel().getSelectedItem());

                });

                String puhelinnro = tfPuhelinnumero1.getText();

                data = FXCollections.observableArrayList();
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    PreparedStatement preparedStatement2 = connection.prepareStatement(HAE_VARAUKSET_KAIKKI_SQL);
                    //preparedStatement2.setString(1, puhelinnro);
                    ResultSet rs = preparedStatement2.executeQuery();

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                        data.add(row);

                    }
                    tableView.setItems(data);

                } catch (Exception ax) {
                    ax.printStackTrace();
                    System.out.println("eipä tainnu onnistua");
                }

                btLuoLasku.setOnAction(eee -> {

                    //tableView.getSelectionModel().getSelectedItem();
                    StringBuilder sb3 = new StringBuilder(String.valueOf(tableView.getSelectionModel().getSelectedItem()));
                    sb3.deleteCharAt(sb3.indexOf("["));
                    sb3.deleteCharAt(sb3.indexOf("]"));

                    String varauksenTiedotValittu = sb3.toString();
                    String[] varausTiedotLista = varauksenTiedotValittu.split(", ");

                    int varausId = parseInt(varausTiedotLista[0]);
                    String asiakasEnimi = varausTiedotLista[1];
                    String asiakasSnimi = varausTiedotLista[2];
                    String asiakasPuhNro = varausTiedotLista[3];
                    String asiakasOsoite = null;
                    String asiakasPostinro = null;
                    String asiakasKunta = null;
                    String mokkiNimi = null;
                    String palveluNimi = null;

                    double alv = 24;
                    double hinta = 0;
                    double hinta2 = 0;
                    int lukumaara = 0;
                    int vuorokaudet = 0;
                    int laskuId = 0;

                    //ei toimi vielä vaan kysely kaataa ohjelman
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        final String VARAUKSEN_KESTO_SQL = "SELECT varauksen_kesto FROM varaus WHERE varaus_id = (?)";
                        PreparedStatement s = connection.prepareStatement(VARAUKSEN_KESTO_SQL);
                        s.setInt(1, varausId);

                        ResultSet resultSet = s.executeQuery();

                        if (resultSet.next()) {
                            vuorokaudet = (resultSet.getInt("varauksen_kesto"));
                        }

                        final String ASIAKASTIEDOT_LASKUUN_SQL = "SELECT mokkinimi, lahiosoite, asiakas.postinro, toimipaikka, hinta from varaus varaus join mokki mokki on varaus.mokki_mokki_id = mokki_id join asiakas asiakas on varaus.asiakas_id = asiakas.asiakas_id\n"
                                + "join posti posti on asiakas.postinro = posti.postinro where varaus_id = (?)";
                        PreparedStatement s2 = connection.prepareStatement(ASIAKASTIEDOT_LASKUUN_SQL);

                        s2.setInt(1, varausId);

                        ResultSet resultSet2 = s2.executeQuery();

                        if (resultSet2.next()) {
                            mokkiNimi = (resultSet2.getString("mokkinimi"));
                            asiakasOsoite = (resultSet2.getString("lahiosoite"));
                            asiakasPostinro = (resultSet2.getString("postinro"));
                            asiakasKunta = (resultSet2.getString("toimipaikka"));
                            hinta = (resultSet2.getDouble("hinta"));

                        }
                        double summa = hinta * vuorokaudet;

                        final String PALVELUT_LASKUUN_SQL = "select palvelu.nimi, palvelu.hinta, varauksen_palvelut.lkm\n"
                                + "from palvelu\n"
                                + "join varauksen_palvelut on palvelu.palvelu_id = varauksen_palvelut.palvelu_id where varaus_id = ?;";
                        PreparedStatement s5 = connection.prepareStatement(PALVELUT_LASKUUN_SQL);

                        s5.setInt(1, varausId);

                        ResultSet resultSet5 = s5.executeQuery();

                        if (resultSet5.next()) {
                            hinta2 = (resultSet5.getDouble("hinta"));
                            lukumaara = (resultSet5.getInt("lkm"));
                            palveluNimi = (resultSet5.getString("nimi"));
                            summa = summa + hinta2;

                        }

                        LocalDate tanaan = LocalDate.now();
                        java.util.Date utilNykyinen = Date.valueOf(tanaan);
                        java.sql.Date sqlLaskupaiva = new java.sql.Date(utilNykyinen.getTime());

                        LocalDate eraPaiva = tanaan.plusDays(30);
                        java.util.Date utilEraPaiva = Date.valueOf(eraPaiva);
                        java.sql.Date sqlEraPaiva = new java.sql.Date(utilEraPaiva.getTime());

                        final String LUO_UUSI_LASKU_SQL = "INSERT INTO lasku (varaus_id, summa, alv, laskupvm, erapvm) values (?, ?, ?, ?, ?)";
                        PreparedStatement s3 = connection.prepareStatement(LUO_UUSI_LASKU_SQL);
                        s3.setInt(1, varausId);
                        s3.setDouble(2, summa);
                        s3.setDouble(3, alv);
                        s3.setDate(4, sqlLaskupaiva);
                        s3.setDate(5, sqlEraPaiva);
                        s3.executeUpdate();

                        final String HAE_LASKU_ID_SQL = "SELECT lasku_id FROM lasku where varaus_id = ?";
                        PreparedStatement s4 = connection.prepareStatement(HAE_LASKU_ID_SQL);

                        s4.setInt(1, varausId);

                        ResultSet resultSet4 = s4.executeQuery();

                        if (resultSet4.next()) {
                            laskuId = (resultSet4.getInt("lasku_id"));
                        }

                        //Laskun kirjoitus ja muotoilu
                        Document doc = new Document();

                        //load the template file
                        doc.loadFromFile("C:\\Lasku_Pohja.docx");

                        //replace text in the document
                        doc.replace("#Lasku_id", String.valueOf(laskuId), true, true);
                        doc.replace("#AsiakasNimi", asiakasEnimi + " " + asiakasSnimi, true, true);
                        doc.replace("#AsiakasOsoite", asiakasOsoite, true, true);
                        doc.replace("#AsiakasPostinro", asiakasPostinro, true, true);
                        doc.replace("#AsiakasKunta", asiakasKunta, true, true);
                        doc.replace("#AsiakasMatkapuhelin", asiakasPuhNro, true, true);
                        doc.replace("#LaskuPvm", String.valueOf(LocalDate.now()), true, true);

                        //define purchase data
                        String[][] purchaseData = {
                            new String[]{mokkiNimi, String.valueOf(vuorokaudet), String.valueOf(hinta)},
                            new String[]{palveluNimi, String.valueOf(lukumaara), String.valueOf(hinta2)},};

                        writeDataToDocument(doc, purchaseData);

                        //update fields
                        doc.isUpdateFields(true);

                        //save file in pdf format
                        doc.saveToFile("Lasku." + laskuId + "." + LocalDate.now() + ".pdf", FileFormat.PDF);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                });

                Scene lisaaAlueIkkuna = new Scene(vbox, 600, 500);
                laskujenHallintaIkkuna.setScene(lisaaAlueIkkuna);

            });

            btLaskujenSeuranta.setOnAction(e -> {
                GridPane laskujenSeurantaGridPaneeli = new GridPane();
                Scene laskujenSeurantaScene = new Scene(laskujenSeurantaGridPaneeli, 800, 700);
                laskujenHallintaIkkuna.setScene(laskujenSeurantaScene);

                laskujenSeurantaGridPaneeli.add(new Label("Syötä lasku ID: "), 1, 8);
                laskujenSeurantaGridPaneeli.add(tfLaskuid, 1, 9);
                laskujenSeurantaGridPaneeli.add(btHaelaskut, 1, 6);
                laskujenSeurantaGridPaneeli.add(btMerkitsemaksetuksi, 1, 10);
                laskujenSeurantaGridPaneeli.add(btEimaksettu, 1, 11);
                laskujenSeurantaGridPaneeli.add(btPoistalasku, 1, 12);
                laskujenSeurantaGridPaneeli.add(btLahetamuistutus, 1, 13);
                laskujenSeurantaGridPaneeli.setHgap(10);
                laskujenSeurantaGridPaneeli.setVgap(10);

                laskujenSeurantaGridPaneeli.setAlignment(Pos.CENTER);

                btHaelaskut.setMaxSize(200, 200);
                btMerkitsemaksetuksi.setMaxSize(200, 200);
                btEimaksettu.setMaxSize(200, 200);
                btPoistalasku.setMaxSize(200, 200);
                btLahetamuistutus.setMaxSize(200, 200);

                Label palvelu = new Label("Laskujen seuranta");
                palvelu.setFont(Font.font("Calibri", 36));

                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().add(palvelu);

                TableView tableViewLaskut = new TableView();
                tableViewLaskut.setPrefWidth(450);
                tableViewLaskut.setPrefHeight(450);

                VBox vbox1 = new VBox(20);
                vbox1.setPadding(new Insets(25, 25, 25, 25));
                vbox1.getChildren().addAll(hbox, tableViewLaskut, laskujenSeurantaGridPaneeli);

                data = FXCollections.observableArrayList();
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    String SQL = "Select lasku.lasku_id, lasku.summa, lasku.laskupvm, lasku.erapvm, asiakas.etunimi, asiakas.sukunimi, asiakas.lahiosoite, asiakas.email, lasku.maksusuoritus, lasku.maksumuistutus from lasku JOIN varaus varaus ON lasku.varaus_id = varaus.varaus_id JOIN asiakas asiakas ON varaus.asiakas_id = asiakas.asiakas_id";

                    ResultSet rs = connection.createStatement().executeQuery(SQL);

                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            }
                        });

                        tableViewLaskut.getColumns().addAll(col);
                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }

                    }

                } catch (Exception exe) {
                    exe.printStackTrace();
                    System.out.println("eipä tainnu onnistua");
                }
                btHaelaskut.setOnAction(a
                        -> {

                    data = FXCollections.observableArrayList();
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement2 = connection.prepareStatement(HAE_LASKUT_SQL);
                        ResultSet rs = preparedStatement2.executeQuery();

                        while (rs.next()) {
                            ObservableList<String> row = FXCollections.observableArrayList();
                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                row.add(rs.getString(i));
                            }
                            data.add(row);

                        }
                        tableViewLaskut.setItems(data);

                    } catch (Exception ax) {
                        ax.printStackTrace();
                        System.out.println("eipä tainnu onnistua");
                    }

                });

                btMerkitsemaksetuksi.setOnAction(a
                        -> {

                    int lasku_id = parseInt(tfLaskuid.getText());

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement update = connection.prepareStatement(MAKSETTU_SQL);

                        update.setInt(1, lasku_id);
                        System.out.println(update);
                        update.executeUpdate();

                    } catch (SQLException ex) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

                btEimaksettu.setOnAction(a
                        -> {

                    int lasku_id = parseInt(tfLaskuid.getText());

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement update = connection.prepareStatement(EI_MAKSETTU_SQL);

                        update.setInt(1, lasku_id);
                        System.out.println(update);
                        update.executeUpdate();

                    } catch (SQLException ex) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

                btPoistalasku.setOnAction(a
                        -> {
                    int lasku_id = parseInt(tfLaskuid.getText());

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement update = connection.prepareStatement("DELETE FROM lasku WHERE lasku_id = ?");

                        update.setInt(1, lasku_id);
                        System.out.println(update);
                        update.executeUpdate();

                    } catch (SQLException ex) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

                btLahetamuistutus.setOnAction(a
                        -> {

                    int lasku_id = parseInt(tfLaskuid.getText());

                    int varausId = 0;

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        final String PAIVITA_LASKU_MUISTUTUS_SQL = "UPDATE lasku SET maksumuistutus = '10' WHERE lasku_id = ?";

                        PreparedStatement update = connection.prepareStatement(PAIVITA_LASKU_MUISTUTUS_SQL);

                        update.setInt(1, lasku_id);
                        System.out.println(update);
                        update.executeUpdate();

                        final String HAE_VARAUS_ID_SQL = "SELECT varaus.varaus_id FROM varaus JOIN lasku ON lasku.varaus_id = varaus.varaus_id where lasku_id = ?";
                        PreparedStatement s5 = connection.prepareStatement(HAE_VARAUS_ID_SQL);

                        s5.setInt(1, lasku_id);

                        ResultSet resultSet5 = s5.executeQuery();

                        if (resultSet5.next()) {
                            varausId = (resultSet5.getInt("varaus_id"));
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String asiakasEnimi = null;
                    String asiakasSnimi = null;
                    String asiakasPuhNro = null;
                    String asiakasOsoite = null;
                    String asiakasPostinro = null;
                    String asiakasKunta = null;
                    String mokkiNimi = null;
                    String palveluNimi = null;

                    double alv = 24;
                    double hinta = 0;
                    double hinta2 = 0;
                    int lukumaara = 0;
                    int vuorokaudet = 0;
                    int laskuId = 0;

                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        final String VARAUKSEN_KESTO_SQL = "SELECT varauksen_kesto FROM varaus WHERE varaus_id = (?)";
                        PreparedStatement s = connection.prepareStatement(VARAUKSEN_KESTO_SQL);
                        s.setInt(1, varausId);

                        ResultSet resultSet = s.executeQuery();

                        if (resultSet.next()) {
                            vuorokaudet = (resultSet.getInt("varauksen_kesto"));
                        }

                        final String ASIAKASTIEDOT_MUISTUTUKSEEN_SQL = "SELECT asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, mokkinimi, asiakas.lahiosoite, asiakas.postinro, toimipaikka, hinta from varaus varaus join mokki mokki on varaus.mokki_mokki_id = mokki_id join asiakas asiakas on varaus.asiakas_id = asiakas.asiakas_id\n"
                                + "join posti posti on asiakas.postinro = posti.postinro where varaus_id = (?)";
                        PreparedStatement s3 = connection.prepareStatement(ASIAKASTIEDOT_MUISTUTUKSEEN_SQL);

                        s3.setInt(1, varausId);

                        ResultSet resultSet3 = s3.executeQuery();

                        if (resultSet3.next()) {
                            asiakasEnimi = (resultSet3.getString("etunimi"));
                            asiakasSnimi = (resultSet3.getString("sukunimi"));
                            asiakasPuhNro = (resultSet3.getString("puhelinnro"));
                            mokkiNimi = (resultSet3.getString("mokkinimi"));
                            asiakasOsoite = (resultSet3.getString("lahiosoite"));
                            asiakasPostinro = (resultSet3.getString("postinro"));
                            asiakasKunta = (resultSet3.getString("toimipaikka"));
                            hinta = (resultSet3.getDouble("hinta"));

                        }
                        double summa = hinta * vuorokaudet;

                        final String PALVELUT_LASKUUN_SQL = "select palvelu.nimi, palvelu.hinta, varauksen_palvelut.lkm\n"
                                + "from palvelu\n"
                                + "join varauksen_palvelut where varaus_id = ?;";
                        PreparedStatement s5 = connection.prepareStatement(PALVELUT_LASKUUN_SQL);

                        s5.setInt(1, varausId);

                        ResultSet resultSet5 = s5.executeQuery();

                        if (resultSet5.next()) {
                            hinta2 = (resultSet5.getDouble("hinta"));
                            lukumaara = (resultSet5.getInt("lkm"));
                            palveluNimi = (resultSet5.getString("nimi"));
                            summa = summa + hinta2;

                        }

                        LocalDate tanaan = LocalDate.now();
                        java.util.Date utilNykyinen = Date.valueOf(tanaan);
                        java.sql.Date sqlLaskupaiva = new java.sql.Date(utilNykyinen.getTime());

                        LocalDate eraPaiva = tanaan.plusDays(30);
                        java.util.Date utilEraPaiva = Date.valueOf(eraPaiva);
                        java.sql.Date sqlEraPaiva = new java.sql.Date(utilEraPaiva.getTime());
                        /*
                                final String PAIVITA_LASKU_MUISTUTUS_SQL = "UPDATE lasku SET maksumuistutus = '10' WHERE lasku_id = ?";
                                PreparedStatement s3 = connection.prepareStatement(PAIVITA_LASKU_MUISTUTUS_SQL);
                                s3.setInt(1, laskuId);
                                s3.executeUpdate();

                         */

                        final String HAE_LASKU_ID_SQL = "SELECT lasku_id FROM lasku where varaus_id = ?";
                        PreparedStatement s4 = connection.prepareStatement(HAE_LASKU_ID_SQL);

                        s4.setInt(1, varausId);

                        ResultSet resultSet4 = s4.executeQuery();

                        if (resultSet4.next()) {
                            laskuId = (resultSet4.getInt("lasku_id"));
                        }

                        //Laskun kirjoitus ja muotoilu
                        Document doc = new Document();

                        //load the template file
                        doc.loadFromFile("C:\\Lasku_Pohja.docx");

                        //replace text in the document
                        doc.replace("#Lasku_id", String.valueOf(laskuId), true, true);
                        doc.replace("#AsiakasNimi", asiakasEnimi + " " + asiakasSnimi, true, true);
                        doc.replace("#AsiakasOsoite", asiakasOsoite, true, true);
                        doc.replace("#AsiakasPostinro", asiakasPostinro, true, true);
                        doc.replace("#AsiakasKunta", asiakasKunta, true, true);
                        doc.replace("#AsiakasMatkapuhelin", asiakasPuhNro, true, true);
                        doc.replace("#LaskuPvm", String.valueOf(LocalDate.now()), true, true);

                        //define purchase data
                        String[][] purchaseData = {
                            new String[]{mokkiNimi, String.valueOf(vuorokaudet), String.valueOf(hinta)},
                            new String[]{palveluNimi, String.valueOf(lukumaara), String.valueOf(hinta2)},
                            new String[]{"Muistutusmaksu", String.valueOf(1), String.valueOf(10)}};

                        writeDataToDocument(doc, purchaseData);

                        //update fields
                        doc.isUpdateFields(true);

                        //save file in pdf format
                        doc.saveToFile("Lasku.muistutus." + laskuId + "." + LocalDate.now() + ".pdf", FileFormat.PDF);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                });

                Scene lisaaAlueIkkuna = new Scene(vbox1, 800, 600);
                laskujenHallintaIkkuna.setScene(lisaaAlueIkkuna);

            });

            laskujenHallintaGridPaneeli.add(btLuoUusiLasku, 0, 0);
            laskujenHallintaGridPaneeli.add(btLaskujenSeuranta, 0, 1);
            laskujenHallintaGridPaneeli.add(btTakaisinMokkienhallintaan1, 0, 2);
            laskujenHallintaGridPaneeli.setAlignment(Pos.CENTER);

            btLuoUusiLasku.setMaxSize(125, 200);
            btPoistaLasku.setMaxSize(125, 200);
            btLaskujenSeuranta.setMaxSize(125, 200);
            btTakaisinMokkienhallintaan1.setMaxSize(125, 200);

            laskujenHallintaGridPaneeli.setPadding(new Insets(10));
            laskujenHallintaGridPaneeli.setHgap(15);
            laskujenHallintaGridPaneeli.setVgap(15);

            btTakaisinMokkienhallintaan1.setOnAction(eE -> {
                laskujenHallintaIkkuna.close();
            });

        }
        );

        //Palveluidenhallinta
        btPalveluidenhallinta.setOnAction(
                (ActionEvent event) -> {
                    GridPane paneeli = new GridPane();
                    paneeli.setHgap(10);
                    paneeli.setVgap(10);
                    paneeli.add(btlisaapalvelu, 0, 1);
                    paneeli.add(btvaraapalvelu, 0, 2);
                    paneeli.add(btTakaisinMokkienhallintaan1, 0, 3);
                    paneeli.setAlignment(Pos.CENTER);

                    btlisaapalvelu.setMaxSize(125, 200);
                    btvaraapalvelu.setMaxSize(125, 200);
                    btLaskujenSeuranta.setMaxSize(125, 200);
                    btTakaisinMokkienhallintaan1.setMaxSize(125, 200);

                    paneeli.setPadding(new Insets(10));
                    paneeli.setHgap(15);
                    paneeli.setVgap(15);

                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER);
                    hbox.getChildren().add(paneeli);

                    StackPane toinenikkuna = new StackPane();

                    toinenikkuna.getChildren().add(hbox);

                    Scene secondScene = new Scene(toinenikkuna, 400, 300);

                    // uusi ikkuna
                    Stage uusiIkkuna = new Stage();
                    uusiIkkuna.setTitle("Palveluidenhallinta");
                    uusiIkkuna.setScene(secondScene);

                    uusiIkkuna.show();

                    btTakaisinMokkienhallintaan1.setOnAction(e -> {
                        uusiIkkuna.close();
                    });
                }
        );

        //Tästä namiskasta lisätään palvelu
        btlisaapalvelu.setOnAction((ActionEvent event) -> {
                    GridPane paneeli = new GridPane();
                    paneeli.setHgap(10);
                    paneeli.setVgap(10);

                    final SearchableComboBox alueenvalintaComboBox = new SearchableComboBox();

                    //Lista alueille
                    ObservableList<String> alueetValittava
                    = FXCollections.observableArrayList();

                    //Yhteyden avaus ja alueet kannasta listaan
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement = connection.prepareStatement(HAE_ALUEET_SQL);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            int alueId = resultSet.getInt("alue_id");
                            String alueNimi = resultSet.getString("nimi");
                            String alue = alueId + "\t" + alueNimi;
                            alueetValittava.add(alue);

                        }
                        alueenvalintaComboBox.setItems(alueetValittava);
                        resultSet.close();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    paneeli.add(new Label("valitse alue: "), 0, 0);
                    paneeli.add(alueenvalintaComboBox, 1, 0);
                    paneeli.add(new Label("Nimi: "), 0, 1);
                    paneeli.add(tfpalvelunimi, 1, 1);
                    paneeli.add(new Label("Kuvaus: "), 0, 2);
                    paneeli.add(tfpalvelukuvaus, 1, 2);
                    paneeli.add(new Label("Hinta: "), 0, 3);
                    paneeli.add(tfpalveluhinta, 1, 3);

                    paneeli.add(btTallenna, 1, 4);
                    paneeli.add(btSulje2, 0, 4);
                    paneeli.setAlignment(Pos.CENTER);

                    StackPane toinenikkuna = new StackPane();

                    toinenikkuna.getChildren().add(paneeli);

                    Scene secondScene = new Scene(toinenikkuna, 500, 500);

                    // uusi ikkuna
                    Stage uusiIkkuna = new Stage();
                    uusiIkkuna.setTitle("Lisää Palvelu");
                    uusiIkkuna.setScene(secondScene);

                    uusiIkkuna.show();

                    //tallennetaan palvelu kantaan tästä napista
                    btTallenna.setOnAction(e -> {

                        StringBuilder sb4 = new StringBuilder(alueenvalintaComboBox.getValue().toString());
                        String alueenTiedotValittu = sb4.toString();
                        String[] aluelista = alueenTiedotValittu.split("\\t");
                        int alueenIdValittu = parseInt(aluelista[0]);

                        String nimi = tfpalvelunimi.getText();
                        String kuvaus = tfpalvelukuvaus.getText();
                        Double hinta = Double.valueOf(tfpalveluhinta.getText());
                        Double alv = 24.0;

                        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                                PreparedStatement preparedStatement = connection.prepareStatement(SYOTA_PALVELU_SQL)) {
                            preparedStatement.setInt(1, alueenIdValittu);
                            preparedStatement.setString(2, nimi);
                            preparedStatement.setString(3, kuvaus);
                            preparedStatement.setDouble(4, hinta);
                            preparedStatement.setDouble(5, alv);

                            System.out.println(preparedStatement);
                            preparedStatement.executeUpdate();

                        } catch (SQLException ex) {
                            Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                    btSulje2.setOnAction(e -> {
                        uusiIkkuna.close();
                    });
                }
        );
        btvaraapalvelu.setOnAction((ActionEvent event) -> {
                    GridPane paneeli = new GridPane();

                    //Asiakkaan valinta, combobox etsi ominaisuudella
                    final SearchableComboBox asiakasComboBox2 = new SearchableComboBox();

                    //Lista comboboxille
                    ObservableList<String> asiakkaat2
                    = FXCollections.observableArrayList();

                    //yhteyden avaus ja asiakkaiden tiedot listaan
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        Statement statement = connection.createStatement();

                        ResultSet resultSet = statement.executeQuery("select * from asiakas");

                        while (resultSet.next()) {
                            String enimi = resultSet.getString("etunimi");
                            String snimi = resultSet.getString("sukunimi");
                            String pnro = resultSet.getString("puhelinnro");
                            String em = resultSet.getString("email");
                            String asiakas2 = enimi + "\t" + snimi + "\t" + pnro + "\t" + em;
                            asiakkaat2.add(asiakas2);
                        }
                        asiakasComboBox2.setItems(asiakkaat2);

                        resultSet.close();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    //valikko ja lista varauksille
                    final SearchableComboBox varausComboBox = new SearchableComboBox();

                    ObservableList<String> varaukset = FXCollections.observableArrayList();

                    asiakasComboBox2.setOnAction(e -> {
                        try {
                            StringBuilder sb2 = new StringBuilder(String.valueOf(asiakasComboBox2.getValue().toString()));
                            String asiakkaanTiedotValittu2 = sb2.toString();
                            String[] asiakaslista2 = asiakkaanTiedotValittu2.split("\\t");
                            String asiakkaanPuhNroValittu = asiakaslista2[2];

                            int asiakasIdValittu = 0;
                            int mokki_idvalittu = 0;
                            String varaus = "";

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                                PreparedStatement preparedStatement1 = connection.prepareStatement(VALITTU_ASIAKAS_ID_SQL);
                                preparedStatement1.setString(1, asiakkaanPuhNroValittu);
                                ResultSet resultSet1 = preparedStatement1.executeQuery();

                                if (resultSet1.next()) {
                                    asiakasIdValittu = resultSet1.getInt("asiakas_id");
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            try {
                                varaukset.clear();

                                int varausidvalittu = 0;
                                Date pvm = new Date(0);

                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                                Statement statement1 = connection.createStatement();

                                ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM `varaus` WHERE `asiakas_id`='" + asiakasIdValittu + "'");

                                while (resultSet1.next()) {
                                    varausidvalittu = resultSet1.getInt("varaus_id");
                                    mokki_idvalittu = resultSet1.getInt("mokki_mokki_id");
                                    pvm = resultSet1.getDate("varattu_alkupvm");

                                    varaus = varausidvalittu + "\t" + "\t" + pvm.toString();
                                }

                                resultSet1.close();

                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                            try {
                                String mokkivalittu = "";

                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                                Statement statement1 = connection.createStatement();

                                ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM `mokki` WHERE `mokki_id`='" + mokki_idvalittu + "'");

                                while (resultSet1.next()) {
                                    mokkivalittu = resultSet1.getString("mokkinimi");
                                    varaus = varaus + "\t" + mokkivalittu;
                                    varaukset.add(varaus);
                                }

                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                            varausComboBox.setItems(varaukset);
                        } catch (Exception ex) {
                            System.out.println("älä välitä tästä");
                        }

                    });

                    //palvelunvalitsemislaatikko
                    final SearchableComboBox palveluComboBox = new SearchableComboBox();

                    ObservableList<String> palvelut = FXCollections.observableArrayList();

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        Statement statement1 = connection.createStatement();

                        ResultSet resultSet = statement1.executeQuery("SELECT p.nimi, a.nimi from palvelu p join alue a on p.alue_id = a.alue_id;");

                        while (resultSet.next()) {
                            String palvelu = resultSet.getString("p.nimi");
                            String alue = resultSet.getString("a.nimi");

                            //String palvelualue = "palvelu: " + palvelu + "\t" + "alue: " + alue;
                            String palvelualue = palvelu + "\t" + alue;
                            palvelut.add(palvelualue);
                        }
                        palveluComboBox.setItems(palvelut);
                        resultSet.close();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    DatePicker varausAlku = new DatePicker(LocalDate.now());
                    paneeli.setHgap(10);
                    paneeli.setVgap(10);
                    paneeli.add(new Label("Asiakas: "), 0, 0);
                    paneeli.add(asiakasComboBox2, 1, 0);
                    paneeli.add(new Label("Varaus: "), 0, 1);
                    paneeli.add(varausComboBox, 1, 1);
                    paneeli.add(new Label("Palvelu: "), 0, 2);
                    paneeli.add(palveluComboBox, 1, 2);
                    paneeli.add(new Label("lkm: "), 0, 3);
                    paneeli.add(new Label("Päivämäärä:"), 0, 4);
                    paneeli.add(varausAlku, 1, 4);

                    paneeli.add(tfpalvelulkm, 1, 3);
                    paneeli.add(btTallenna, 1, 5);
                    paneeli.add(btSulje2, 0, 5);
                    paneeli.setAlignment(Pos.CENTER);

                    StackPane toinenikkuna = new StackPane();

                    toinenikkuna.getChildren().add(paneeli);

                    Scene secondScene = new Scene(toinenikkuna, 500, 500);

                    // uusi ikkuna
                    Stage uusiIkkuna = new Stage();
                    uusiIkkuna.setTitle("Varaa Palvelu");
                    uusiIkkuna.setScene(secondScene);
                    uusiIkkuna.show();

                    //toiminto palvelun varauksen tallentamiselle
                    btTallenna.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            StringBuilder sb1 = new StringBuilder(varausComboBox.getValue().toString());
                            String varausvalittu = sb1.toString();
                            String[] varauslista = varausvalittu.split("\\t");
                            String varausnimivalittu = varauslista[0];
                            System.out.println(varausnimivalittu);

                            StringBuilder sb2 = new StringBuilder(palveluComboBox.getValue().toString());
                            String palveluvalittu = sb2.toString();
                            String[] palvelulista = palveluvalittu.split("\\t");
                            String palvelunimivalittu = palvelulista[0];
                            System.out.println(palvelunimivalittu);

                            int varausidvalittu = 0;
                            int palveluidvalittu = 0;
                            Integer lkm = Integer.parseInt(tfpalvelulkm.getText());

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                                PreparedStatement preparedStatement1 = connection.prepareStatement(VALITTU_VARAUS_ID_SQL);
                                preparedStatement1.setString(1, varausnimivalittu);
                                ResultSet resultSet1 = preparedStatement1.executeQuery();

                                if (resultSet1.next()) {
                                    varausidvalittu = resultSet1.getInt("varaus_id");

                                }
                                PreparedStatement preparedStatement2 = connection.prepareStatement(VALITTU_PALVELU_ID_SQL);
                                preparedStatement2.setString(1, palvelunimivalittu);
                                ResultSet resultSet2 = preparedStatement2.executeQuery();

                                if (resultSet2.next()) {
                                    palveluidvalittu = resultSet2.getInt("palvelu_id");
                                }

                                PreparedStatement preparedStatement = connection.prepareStatement(TALLENNA_PALVELU_VARAUS_SQL);
                                {
                                    preparedStatement.setInt(1, varausidvalittu);
                                    preparedStatement.setInt(2, palveluidvalittu);
                                    preparedStatement.setInt(3, lkm);
                                    preparedStatement.setDate(4, Date.valueOf(varausAlku.getValue()));

                                    System.out.println(preparedStatement);
                                    preparedStatement.executeUpdate();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

                    btSulje2.setOnAction(e -> {
                        uusiIkkuna.close();
                    });
                }
        );

        //Asiakastiedot
        btAsiakastiedot.setOnAction((ActionEvent event) -> {
                    GridPane asiakasHallintapaneeli = new GridPane();
                    btAsiakas.setMaxSize(400, 400);
                    btMuokkaaAsiakas.setMaxSize(400, 400);
                    btVaraustenhallinta.setMaxSize(400, 400);
                    btTakaisinMokkienhallintaan1.setMaxSize(400, 400);

                    asiakasHallintapaneeli.add(btAsiakas, 0, 1);
                    asiakasHallintapaneeli.add(btMuokkaaAsiakas, 0, 2);
                    asiakasHallintapaneeli.add(btVaraustenhallinta, 0, 3);
                    asiakasHallintapaneeli.add(btTakaisinMokkienhallintaan1, 0, 4);
                    asiakasHallintapaneeli.setAlignment(Pos.CENTER);
                    asiakasHallintapaneeli.setPadding(new Insets(10));
                    asiakasHallintapaneeli.setHgap(15);
                    asiakasHallintapaneeli.setVgap(15);

                    StackPane kolmasikkuna = new StackPane();

                    kolmasikkuna.getChildren().addAll(asiakasHallintapaneeli);

                    Scene uusiScene = new Scene(kolmasikkuna, 600, 500);

                    // uusi ikkuna
                    Stage asiakasHallintaikkuna = new Stage();
                    asiakasHallintaikkuna.setTitle("Asiakashallinta");
                    asiakasHallintaikkuna.setScene(uusiScene);
                    asiakasHallintaikkuna.show();

                    btTakaisinMokkienhallintaan1.setOnAction(eE -> {
                        asiakasHallintaikkuna.close();
                    });

                    //Täältä pystytään hallitsemaan asikkaiden varauksia
                    btVaraustenhallinta.setOnAction(e -> {

                        GridPane lisaaVarausPaneeli = new GridPane();
                        lisaaVarausPaneeli.setAlignment(Pos.CENTER);
                        lisaaVarausPaneeli.setPadding(new Insets(10));
                        lisaaVarausPaneeli.setHgap(15);
                        lisaaVarausPaneeli.setVgap(15);

                        lisaaVarausPaneeli.add(btMuokkaamokkia, 0, 0);
                        lisaaVarausPaneeli.add(btMuokkaapalvelua, 0, 1);
                        lisaaVarausPaneeli.add(btTakaisinMokkienhallintaan, 0, 2);

                        btMuokkaamokkia.setMaxSize(400, 400);
                        btMuokkaapalvelua.setMaxSize(400, 400);
                        btTakaisinMokkienhallintaan.setMaxSize(400, 400);

                        VBox hb = new VBox();
                        hb.setAlignment(Pos.CENTER);
                        hb.getChildren().addAll(lisaaVarausPaneeli);

                        Scene lisaaAlueIkkuna = new Scene(hb, 600, 500);
                        asiakasHallintaikkuna.setScene(lisaaAlueIkkuna);

                        btTakaisinMokkienhallintaan.setOnAction(eE -> {
                            asiakasHallintaikkuna.setScene(uusiScene);
                        });

                    });

                    btMuokkaamokkia.setOnAction(e
                            -> {

                        tfPuhelinnumero1.clear();

                        GridPane lisaaMuokkaaMokkiPaneeli = new GridPane();
                        lisaaMuokkaaMokkiPaneeli.add(new Label("Puhelinnumero: "), 9, 0);
                        lisaaMuokkaaMokkiPaneeli.add(tfPuhelinnumero1, 9, 1);
                        lisaaMuokkaaMokkiPaneeli.add(btHaeVaraukset, 9, 2);
                        lisaaMuokkaaMokkiPaneeli.add(btTakaisinMokkienhallintaan, 9, 3);
                        lisaaMuokkaaMokkiPaneeli.add(new Label("Syötä varaus id, jonka haluat poistaa: "), 10, 0);
                        lisaaMuokkaaMokkiPaneeli.add(btPoistavaraus, 10, 2);
                        lisaaMuokkaaMokkiPaneeli.add(tfVarausid, 10, 1);

                        lisaaMuokkaaMokkiPaneeli.setHgap(10);
                        lisaaMuokkaaMokkiPaneeli.setVgap(10);

                        Label label = new Label("Mökkivaraukset");
                        label.setFont(Font.font("Calibri", 36));

                        HBox hb = new HBox();
                        hb.setAlignment(Pos.CENTER);
                        hb.getChildren().add(label);

                        TableView tableView = new TableView();
                        tableView.setPrefWidth(450);
                        tableView.setPrefHeight(450);

                        VBox vbox = new VBox(20);
                        vbox.setPadding(new Insets(25, 25, 25, 25));
                        vbox.getChildren().addAll(hb, tableView, lisaaMuokkaaMokkiPaneeli);

                        data = FXCollections.observableArrayList();
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                            String SQL = "SELECT varaus.varaus_id, asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, varaus.varattu_alkupvm, varaus.varattu_loppupvm, mokki.mokkinimi FROM asiakas asiakas JOIN varaus varaus ON asiakas.asiakas_id = varaus.asiakas_id JOIN mokki ON varaus.mokki_mokki_id = mokki.mokki_id";

                            ResultSet rs = connection.createStatement().executeQuery(SQL);

                            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                                final int j = i;
                                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                        return new SimpleStringProperty(param.getValue().get(j).toString());
                                    }
                                });

                                tableView.getColumns().addAll(col);
                            }

                            while (rs.next()) {
                                ObservableList<String> row = FXCollections.observableArrayList();
                                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                    row.add(rs.getString(i));
                                }

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("eipä tainnu onnistua");
                        }

                        btHaeVaraukset.setOnAction(a
                                -> {

                            String puhelinnro = tfPuhelinnumero1.getText();

                            data = FXCollections.observableArrayList();
                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                                PreparedStatement preparedStatement2 = connection.prepareStatement(HAE_VARAUKSET_PUHELINNUMEROLLA_SQL);
                                preparedStatement2.setString(1, puhelinnro);
                                ResultSet rs = preparedStatement2.executeQuery();

                                while (rs.next()) {
                                    ObservableList<String> row = FXCollections.observableArrayList();
                                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                        row.add(rs.getString(i));
                                    }
                                    data.add(row);

                                }
                                tableView.setItems(data);

                            } catch (Exception ax) {
                                ax.printStackTrace();
                                System.out.println("eipä tainnu onnistua");
                            }

                        });

                        btPoistavaraus.setOnAction(a
                                -> {

                            int varaus_id = parseInt(tfVarausid.getText());
                            tfVarausid.clear();

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                                PreparedStatement update = connection.prepareStatement("DELETE FROM varaus WHERE varaus_id = ?");

                                update.setInt(1, varaus_id);
                                System.out.println(update);
                                update.executeUpdate();

                            } catch (SQLException ex) {
                                Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        btTakaisinMokkienhallintaan.setOnAction(eE -> {
                            asiakasHallintaikkuna.setScene(uusiScene);
                        });

                        Scene lisaaAlueIkkuna = new Scene(vbox, 600, 500);
                        asiakasHallintaikkuna.setScene(lisaaAlueIkkuna);
                    });

                    btMuokkaapalvelua.setOnAction(ex
                            -> {

                        tfPuhelinnumero1.clear();

                        GridPane lisaaMuokkaaPalveluaPaneeli = new GridPane();
                        lisaaMuokkaaPalveluaPaneeli.add(new Label("Puhelinnumero: "), 9, 0);
                        lisaaMuokkaaPalveluaPaneeli.add(tfPuhelinnumero1, 9, 1);
                        lisaaMuokkaaPalveluaPaneeli.add(btHaeVaraukset, 9, 2);
                        lisaaMuokkaaPalveluaPaneeli.add(btTakaisinMokkienhallintaan, 9, 3);
                        lisaaMuokkaaPalveluaPaneeli.add(new Label("Syötä varaus id, jonka haluat poistaa: "), 10, 0);
                        lisaaMuokkaaPalveluaPaneeli.add(btPoistavaraus, 10, 2);
                        lisaaMuokkaaPalveluaPaneeli.add(tfVarausid, 10, 1);
                        lisaaMuokkaaPalveluaPaneeli.setHgap(10);
                        lisaaMuokkaaPalveluaPaneeli.setVgap(10);

                        Label palvelu = new Label("Palveluvaraukset");
                        palvelu.setFont(Font.font("Calibri", 36));

                        HBox hbox = new HBox();
                        hbox.setAlignment(Pos.CENTER);
                        hbox.getChildren().add(palvelu);

                        TableView tableViewPalvelu = new TableView();
                        tableViewPalvelu.setPrefWidth(450);
                        tableViewPalvelu.setPrefHeight(450);

                        VBox vbox1 = new VBox(20);
                        vbox1.setPadding(new Insets(25, 25, 25, 25));
                        vbox1.getChildren().addAll(hbox, tableViewPalvelu, lisaaMuokkaaPalveluaPaneeli);

                        data = FXCollections.observableArrayList();
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                            String SQL = "SELECT varauksen_palvelut.varaus_id, asiakas.etunimi, asiakas.sukunimi, asiakas.puhelinnro, palvelu.nimi,varauksen_palvelut.lkm, palvelu.kuvaus, palvelu.hinta FROM varauksen_palvelut varauksen_palvelut JOIN varaus varaus ON varauksen_palvelut.varaus_id = varaus.varaus_id JOIN asiakas ON varaus.varaus_id = asiakas.asiakas_id JOIN palvelu ON varauksen_palvelut.palvelu_id = palvelu.palvelu_id";

                            ResultSet rs = connection.createStatement().executeQuery(SQL);

                            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                                final int j = i;
                                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                        return new SimpleStringProperty(param.getValue().get(j).toString());
                                    }
                                });

                                tableViewPalvelu.getColumns().addAll(col);
                            }

                            while (rs.next()) {
                                ObservableList<String> row = FXCollections.observableArrayList();
                                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                    row.add(rs.getString(i));
                                }

                            }

                        } catch (Exception exe) {
                            exe.printStackTrace();
                            System.out.println("eipä tainnu onnistua");
                        }

                        btHaeVaraukset.setOnAction(a
                                -> {

                            String puhelinnro = tfPuhelinnumero1.getText();

                            data = FXCollections.observableArrayList();
                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                                PreparedStatement preparedStatement2 = connection.prepareStatement(HAE_PALVELU_VARAUKSET_PUHELINNUMEROLLA_SQL);
                                preparedStatement2.setString(1, puhelinnro);
                                ResultSet rs = preparedStatement2.executeQuery();

                                while (rs.next()) {
                                    ObservableList<String> row = FXCollections.observableArrayList();
                                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                        row.add(rs.getString(i));
                                    }
                                    data.add(row);

                                }
                                tableViewPalvelu.setItems(data);

                            } catch (Exception ax) {
                                ax.printStackTrace();
                                System.out.println("eipä tainnu onnistua");
                            }

                        });

                        btPoistavaraus.setOnAction(a
                                -> {
                            int varaus_id = parseInt(tfVarausid.getText());
                            tfVarausid.clear();

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                                PreparedStatement update = connection.prepareStatement("DELETE FROM varauksen_palvelut WHERE varaus_id = ?");

                                update.setInt(1, varaus_id);
                                System.out.println(update);
                                update.executeUpdate();

                            } catch (SQLException exex) {
                                Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        btTakaisinMokkienhallintaan.setOnAction(eE -> {
                            asiakasHallintaikkuna.setScene(uusiScene);
                        });

                        Scene lisaaAlueIkkuna = new Scene(vbox1, 600, 500);
                        asiakasHallintaikkuna.setScene(lisaaAlueIkkuna);
                    });

                    //Asiakkaan tietoja muokataan tästä namiskasta
                    btMuokkaaAsiakas.setOnAction(e -> {

                        tfPuhelinnumero1.clear();
                        tfEtunimi.clear();
                        tfSukunimi.clear();
                        tfLahiosoite.clear();
                        tfPostinumero.clear();
                        tfPuhelinnumero.clear();
                        tfSahkoposti.clear();

                        GridPane lisaaMuokkaaAsiakasPaneeli = new GridPane();
                        lisaaMuokkaaAsiakasPaneeli.setAlignment(Pos.CENTER);
                        lisaaMuokkaaAsiakasPaneeli.setHgap(10);
                        lisaaMuokkaaAsiakasPaneeli.setVgap(10);

                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Kirjoita asiakkaan puhelinnumero: "), 0, 0);
                        lisaaMuokkaaAsiakasPaneeli.add(tfPuhelinnumero1, 1, 0);
                        lisaaMuokkaaAsiakasPaneeli.add(btHaeTiedot, 2, 0);

                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Etunimi: "), 0, 2);
                        lisaaMuokkaaAsiakasPaneeli.add(tfEtunimi, 1, 2);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Sukunimi: "), 0, 3);
                        lisaaMuokkaaAsiakasPaneeli.add(tfSukunimi, 1, 3);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Lähiosoite: "), 0, 4);
                        lisaaMuokkaaAsiakasPaneeli.add(tfLahiosoite, 1, 4);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Postinumero: "), 0, 5);
                        lisaaMuokkaaAsiakasPaneeli.add(tfPostinumero, 1, 5);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Puhelinnumero: "), 0, 6);
                        lisaaMuokkaaAsiakasPaneeli.add(tfPuhelinnumero, 1, 6);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Sähköposti: "), 0, 7);
                        lisaaMuokkaaAsiakasPaneeli.add(tfSahkoposti, 1, 7);

                        lisaaMuokkaaAsiakasPaneeli.add(btTallennaPaivitetytTiedot, 1, 9);
                        lisaaMuokkaaAsiakasPaneeli.add(btPoistaTiedot, 1, 10);
                        lisaaMuokkaaAsiakasPaneeli.add(btTakaisinMokkienhallintaan, 0, 9);

                        Scene lisaaMuokkaaAsiakasIkkuna = new Scene(lisaaMuokkaaAsiakasPaneeli, 600, 500);
                        asiakasHallintaikkuna.setScene(lisaaMuokkaaAsiakasIkkuna);

                        btTakaisinMokkienhallintaan.setOnAction(eE -> {
                            asiakasHallintaikkuna.setScene(uusiScene);
                        });
                    });

                    btHaeTiedot.setOnAction(a -> {

                        String puhelinnro = tfPuhelinnumero1.getText();

                        try {

                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                            String query = "SELECT asiakas_id, etunimi,sukunimi,lahiosoite,postinro,puhelinnro,email FROM asiakas WHERE puhelinnro = ?";
                            PreparedStatement s = connection.prepareStatement(query);

                            s.setString(1, puhelinnro);

                            ResultSet resultSet = s.executeQuery();

                            if (resultSet.next()) {
                                tfAsiakasID.setText(Integer.toString(resultSet.getInt("asiakas_id")));
                                tfEtunimi.setText(resultSet.getString("etunimi"));
                                tfSukunimi.setText(resultSet.getString("sukunimi"));
                                tfLahiosoite.setText(resultSet.getString("lahiosoite"));
                                tfPostinumero.setText(resultSet.getString("postinro"));
                                tfPuhelinnumero.setText(resultSet.getString("puhelinnro"));
                                tfSahkoposti.setText(resultSet.getString("email"));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    });

                    //Päivitettyjen asiakastietojen tallennus kantaan, lisää tähän asiakas_id kenttä näkyviin tai haku jotenkin :D
                    btTallennaPaivitetytTiedot.setOnAction(exe -> {

                        String etunimi = tfEtunimi.getText();
                        String sukunimi = tfSukunimi.getText();
                        String lahiosoite = tfLahiosoite.getText();
                        String email = tfSahkoposti.getText();
                        String puhelinnro = tfPuhelinnumero.getText();
                        int asiakas_id = parseInt(tfAsiakasID.getText());
                        String postinro = tfPostinumero.getText();

                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                            PreparedStatement update = connection.prepareStatement("UPDATE asiakas SET etunimi = ?, sukunimi = ?, lahiosoite = ?, postinro = ?, puhelinnro = ?, email = ?  WHERE asiakas_id = ? ");

                            update.setString(1, etunimi);
                            update.setString(2, sukunimi);
                            update.setString(3, lahiosoite);
                            update.setString(4, postinro);
                            update.setString(5, puhelinnro);
                            update.setString(6, email);
                            update.setInt(7, asiakas_id);
                            System.out.println(update);
                            update.executeUpdate();

                        } catch (SQLException ex) {
                            Logger.getLogger(MokkiSovellus.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                    //tästä namiskasta lähtee asiakkaan tiedot hevon helevettiin kannasta
                    btPoistaTiedot.setOnAction(exe -> {
                        String puhelinnro = tfPuhelinnumero1.getText();

                        try {

                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                            String query = "SELECT etunimi,sukunimi,lahiosoite,postinro,puhelinnro,email FROM asiakas WHERE puhelinnro = ?";
                            PreparedStatement s = connection.prepareStatement(query);

                            PreparedStatement st = connection.prepareStatement("DELETE FROM asiakas WHERE puhelinnro = ?");
                            st.setString(1, puhelinnro);
                            st.executeUpdate();

                        } catch (Exception exex) {
                            System.out.println(exex);
                        }
                    });

                    btAsiakas.setOnAction(e -> {

                        tfEtunimi.clear();
                        tfSukunimi.clear();
                        tfLahiosoite.clear();
                        tfPostinumero.clear();
                        tfPuhelinnumero.clear();
                        tfSahkoposti.clear();

                        GridPane lisaaMuokkaaAsiakasPaneeli = new GridPane();
                        lisaaMuokkaaAsiakasPaneeli.setAlignment(Pos.CENTER);
                        lisaaMuokkaaAsiakasPaneeli.setHgap(10);
                        lisaaMuokkaaAsiakasPaneeli.setVgap(10);

                        lisaaMuokkaaAsiakasPaneeli.setAlignment(Pos.CENTER);

                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Etunimi: "), 0, 0);
                        lisaaMuokkaaAsiakasPaneeli.add(tfEtunimi, 1, 0);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Sukunimi: "), 0, 2);
                        lisaaMuokkaaAsiakasPaneeli.add(tfSukunimi, 1, 2);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Lähiosoite: "), 0, 3);
                        lisaaMuokkaaAsiakasPaneeli.add(tfLahiosoite, 1, 3);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Postinumero: "), 0, 4);
                        lisaaMuokkaaAsiakasPaneeli.add(tfPostinumero, 1, 4);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Puhelinnumero: "), 0, 5);
                        lisaaMuokkaaAsiakasPaneeli.add(tfPuhelinnumero, 1, 5);
                        lisaaMuokkaaAsiakasPaneeli.add(new Label("Sähköposti: "), 0, 6);
                        lisaaMuokkaaAsiakasPaneeli.add(tfSahkoposti, 1, 6);

                        lisaaMuokkaaAsiakasPaneeli.add(btTallenna, 1, 7);
                        lisaaMuokkaaAsiakasPaneeli.add(btTakaisinMokkienhallintaan, 0, 7);

                        Scene lisaaAsiakasIkkuna = new Scene(lisaaMuokkaaAsiakasPaneeli, 600, 500);
                        asiakasHallintaikkuna.setScene(lisaaAsiakasIkkuna);

                        btTakaisinMokkienhallintaan.setOnAction(eE -> {
                            asiakasHallintaikkuna.setScene(uusiScene);
                        });

                    });

                    btTallenna.setOnAction(e -> {

                        String postinro = tfPostinumero.getText();
                        String etunimi = tfEtunimi.getText();
                        String sukunimi = tfSukunimi.getText();
                        String lahiosoite = tfLahiosoite.getText();
                        String email = tfSahkoposti.getText();
                        String puhelinnro = tfPuhelinnumero.getText();

                        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");
                                PreparedStatement preparedStatement = connection.prepareStatement(SYOTA_ASIAKAS_SQL)) {
                            preparedStatement.setString(1, postinro);
                            preparedStatement.setString(2, etunimi);
                            preparedStatement.setString(3, sukunimi);
                            preparedStatement.setString(4, lahiosoite);
                            preparedStatement.setString(5, email);
                            preparedStatement.setString(6, puhelinnro);

                            System.out.println(preparedStatement);
                            preparedStatement.executeUpdate();

                        } catch (SQLException ex) {
                            Logger.getLogger(MokkiSovellus.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                }
        );

        btRaportointi.setOnAction(a
                -> {
            GridPane Raportointipaneeli = new GridPane();

            Raportointipaneeli.setHgap(10);
            Raportointipaneeli.setVgap(10);

            Raportointipaneeli.setPadding(new Insets(10));

            Raportointipaneeli.add(btTakaisinMokkienhallintaan1, 0, 3);
            Raportointipaneeli.add(btLuomokkiraportti, 0, 2);
            Raportointipaneeli.add(btLuopalveluraportti, 0, 1);

            Raportointipaneeli.setAlignment(Pos.CENTER);
            btTakaisinMokkienhallintaan1.setMaxSize(400, 400);
            btLuomokkiraportti.setMaxSize(400, 400);
            btLuopalveluraportti.setMaxSize(400, 400);

            StackPane neljasikkuna = new StackPane();

            neljasikkuna.getChildren().addAll(Raportointipaneeli);

            Scene secondScene = new Scene(neljasikkuna, 600, 400);

            // uusi ikkuna
            Stage RaportointiIkkuna = new Stage();
            RaportointiIkkuna.setTitle("Raportointi");
            RaportointiIkkuna.setScene(secondScene);

            RaportointiIkkuna.show();

            btTakaisinMokkienhallintaan1.setOnAction(e -> {
                RaportointiIkkuna.close();
            });

            btLuomokkiraportti.setOnAction(e
                    -> {
                GridPane Mokkiraporttipaneeli = new GridPane();
                TableView tableViewMokkiRaportti = new TableView();
                Mokkiraporttipaneeli.setAlignment(Pos.CENTER);

                //kalenterit varauksen alku ja loppupvm valinnalle
                DatePicker varausAlku = new DatePicker(LocalDate.now());
                DatePicker varausLoppu = new DatePicker(LocalDate.now());

                final SearchableComboBox alueComboBox = new SearchableComboBox();
                Mokkiraporttipaneeli.add(new Label("Alkupäivämäärä: "), 0, 0);
                Mokkiraporttipaneeli.add(varausAlku, 1, 0);
                Mokkiraporttipaneeli.add(new Label("Loppupäivämäärä: "), 0, 1);
                Mokkiraporttipaneeli.add(varausLoppu, 1, 1);
                Mokkiraporttipaneeli.add(new Label("Valitse toiminta-alue: "), 1, 2);
                Mokkiraporttipaneeli.add(alueComboBox, 1, 3);
                Mokkiraporttipaneeli.add(btHaetiedot, 1, 4);
                Mokkiraporttipaneeli.add(btTakaisinMokkienhallintaan1, 1, 5);

                Mokkiraporttipaneeli.setHgap(10);
                Mokkiraporttipaneeli.setVgap(10);

                tableViewMokkiRaportti.setPrefWidth(450);
                tableViewMokkiRaportti.setPrefHeight(450);

                btHaetiedot.setMaxSize(400, 400);
                btTakaisinMokkienhallintaan1.setMaxSize(400, 400);

                VBox vbox = new VBox(20);
                vbox.setPadding(new Insets(25, 25, 25, 25));
                vbox.getChildren().addAll(tableViewMokkiRaportti, Mokkiraporttipaneeli);

                Scene HaeMokkiTiedot = new Scene(vbox, 600, 400);
                RaportointiIkkuna.setScene(HaeMokkiTiedot);

                ObservableList<String> alueet
                        = FXCollections.observableArrayList();

                //Yhteyden avaus ja alueet kannasta listaan
                try {

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    Statement statement = connection.createStatement();

                    PreparedStatement preparedStatement = connection.prepareStatement(HAE_ALUEET_SQL);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        alueet.add(resultSet.getString("nimi"));
                        alueComboBox.setItems(alueet);

                    }
                    resultSet.close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                data = FXCollections.observableArrayList();
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    String SQL = MOKKI_RAPORTTI_SQL;

                    ResultSet rs = connection.createStatement().executeQuery(SQL);

                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            }
                        });

                        tableViewMokkiRaportti.getColumns().addAll(col);
                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }

                    }

                } catch (Exception exex) {
                    exex.printStackTrace();
                    System.out.println("eipä tainnu onnistua");
                }

                btHaetiedot.setOnAction(ex
                        -> {

                    StringBuilder sb1 = new StringBuilder(alueComboBox.getValue().toString());
                    String varausvalittu = sb1.toString();
                    String[] varauslista = varausvalittu.split("\\t");
                    String varausnimivalittu = varauslista[0];
                    System.out.println(varausnimivalittu);

                    data = FXCollections.observableArrayList();
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement2 = connection.prepareStatement(MOKKI_RAPORTTI_BETWEEN_SQL);

                        preparedStatement2.setString(1, varausnimivalittu);
                        preparedStatement2.setDate(2, Date.valueOf(varausAlku.getValue()));
                        preparedStatement2.setDate(3, Date.valueOf(varausLoppu.getValue()));
                        ResultSet rs = preparedStatement2.executeQuery();

                        while (rs.next()) {
                            ObservableList<String> row = FXCollections.observableArrayList();
                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                row.add(rs.getString(i));
                            }
                            data.add(row);

                        }
                        tableViewMokkiRaportti.setItems(data);

                    } catch (Exception ax) {
                        ax.printStackTrace();
                        System.out.println("eipä tainnu onnistua");
                    }

                });

            });
            btLuopalveluraportti.setOnAction(ex
                    -> {

                GridPane Palveluraporttipaneeli = new GridPane();
                TableView tableViewPalveluRaportti = new TableView();
                Palveluraporttipaneeli.setAlignment(Pos.CENTER);
                final SearchableComboBox alueComboBox = new SearchableComboBox();
                //kalenterit varauksen alku ja loppupvm valinnalle
                DatePicker varausAlku = new DatePicker(LocalDate.now());

                DatePicker varausLoppu = new DatePicker(LocalDate.now());

                Palveluraporttipaneeli.add(new Label("Alkupäivämäärä: "), 0, 0);
                Palveluraporttipaneeli.add(varausAlku, 1, 0);
                Palveluraporttipaneeli.add(new Label("Loppupäivämäärä: "), 0, 1);
                Palveluraporttipaneeli.add(varausLoppu, 1, 1);
                Palveluraporttipaneeli.add(new Label("Valitse toiminta-alue: "), 1, 2);
                Palveluraporttipaneeli.add(alueComboBox, 1, 3);
                Palveluraporttipaneeli.add(btHaetiedot, 1, 4);
                Palveluraporttipaneeli.add(btTakaisinMokkienhallintaan1, 1, 5);

                Palveluraporttipaneeli.setHgap(10);
                Palveluraporttipaneeli.setVgap(10);

                tableViewPalveluRaportti.setPrefWidth(450);
                tableViewPalveluRaportti.setPrefHeight(450);

                btHaetiedot.setMaxSize(400, 400);
                btTakaisinMokkienhallintaan1.setMaxSize(400, 400);

                VBox vbox = new VBox(20);
                vbox.setPadding(new Insets(25, 25, 25, 25));
                vbox.getChildren().addAll(tableViewPalveluRaportti, Palveluraporttipaneeli);

                Scene HaeMokkiTiedot = new Scene(vbox, 600, 400);
                RaportointiIkkuna.setScene(HaeMokkiTiedot);

                data = FXCollections.observableArrayList();
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    String SQL = PALVELU_RAPORTTI_SQL;

                    ResultSet rs = connection.createStatement().executeQuery(SQL);

                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            }
                        });

                        tableViewPalveluRaportti.getColumns().addAll(col);
                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }

                    }

                } catch (Exception exex) {
                    exex.printStackTrace();
                    System.out.println("eipä tainnu onnistua");
                }

                ObservableList<String> alueet
                        = FXCollections.observableArrayList();

                //Yhteyden avaus ja alueet kannasta listaan
                try {

                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                    Statement statement = connection.createStatement();

                    PreparedStatement preparedStatement = connection.prepareStatement(HAE_ALUEET_SQL);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        alueet.add(resultSet.getString("nimi"));
                        alueComboBox.setItems(alueet);

                    }
                    resultSet.close();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                btHaetiedot.setOnAction(exe
                        -> {

                    StringBuilder sb1 = new StringBuilder(alueComboBox.getValue().toString());
                    String varausvalittu = sb1.toString();
                    String[] varauslista = varausvalittu.split("\\t");
                    String varausnimivalittu = varauslista[0];
                    System.out.println(varausnimivalittu);

                    data = FXCollections.observableArrayList();
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "root");

                        PreparedStatement preparedStatement2 = connection.prepareStatement(PALVELU_RAPORTTI_BETWEEN_SQL);

                        preparedStatement2.setString(1, varausnimivalittu);
                        preparedStatement2.setDate(2, Date.valueOf(varausAlku.getValue()));
                        preparedStatement2.setDate(3, Date.valueOf(varausLoppu.getValue()));
                        ResultSet rs = preparedStatement2.executeQuery();

                        while (rs.next()) {
                            ObservableList<String> row = FXCollections.observableArrayList();
                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                row.add(rs.getString(i));
                            }
                            data.add(row);

                        }
                        tableViewPalveluRaportti.setItems(data);

                    } catch (Exception ax) {
                        ax.printStackTrace();
                        System.out.println("eipä tainnu onnistua");
                    }

                });
            });

        });

    }

    public static void main(String[] args) {
        launch(args);

    }
}

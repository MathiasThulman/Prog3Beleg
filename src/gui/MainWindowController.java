package gui;

import automat.*;
import events.HerstellerNummer;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kuchen.KremkuchenImpl;
import kuchen.KuchenVerkaufsObjekt;
import kuchen.ObstkuchenImpl;
import kuchen.ObsttorteImpl;
import observer.Observer;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MainWindowController implements Observer{
    @FXML
    private RadioButton sortByHersteller;
    @FXML
    private RadioButton sortByFachnummer;
    @FXML
    private RadioButton sortByHaltbarkeit;
    @FXML
    private ToggleGroup toggleGroupSort;
    @FXML
    private Button buttonAddHersteller;
    @FXML
    private Button buttonAddKuchen;
    @FXML
    private Button buttonRemoveKuchen;
    @FXML
    private Button buttonRemoveHersteller;
    @FXML
    private Button buttonSetInspectionDate;
    @FXML
    private TextField herstellerField;
    @FXML
    private TextField fachnummerField;
    @FXML
    private String choiceObstkuchen;
    @FXML
    private String choiceKremkuchen;
    @FXML
    private String choiceObsttorte;
    @FXML
    private ListView<HerstellerNummer> listViewHersteller;
    @FXML
    private TextField fieldHersteller;
    @FXML
    private TextField fieldPreis;
    @FXML
    private TextField fieldNaehrwert;
    @FXML
    private TextField fieldHaltbarkeit;
    @FXML
    private TextArea fieldAllergene;
    @FXML
    private TextField fieldObstsorte;
    @FXML
    private TextField fieldKremsorte;
    @FXML
    private ListView<KuchenVerkaufsObjektImpl> listViewKuchen;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label errorText;
    //public StringProperty errorString = new SimpleStringProperty();
    @FXML
    private ChoiceBox choiceKuchen;
    private Automat automat;
    private Comparator<KuchenVerkaufsObjektImpl> fachnummerComp;
    private Comparator<KuchenVerkaufsObjekt> herstellerComp;
    private Comparator<KuchenVerkaufsObjektImpl> haltbarKeitComp;
    private ObservableList<KuchenVerkaufsObjektImpl> observableKuchenList;
    private SortedList<KuchenVerkaufsObjektImpl> sortedKuchenList;
    private ObservableList<HerstellerNummer> observableHerstellerList;


    public void initialize() {
        this.automat = new Automat(20);

        //just to test initial loading
        try {
            this.automat.addHersteller(new HerstellerImpl("Benjamin"));
            this.automat.addKuchen(new ObstkuchenImpl(new HerstellerImpl("Benjamin"), new HashSet<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(1), new BigDecimal(500), "mais"));
            this.automat.addKuchen(new ObstkuchenImpl(new HerstellerImpl("Benjamin"), new HashSet<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(2), new BigDecimal(500), "senf"));
        } catch (AlreadyExistsException | FullAutomatException e) {
            e.printStackTrace();
        }



        try {
            observableKuchenList = FXCollections.observableList(this.automat.checkKuchen());
            sortedKuchenList = new SortedList<>(this.observableKuchenList, fachnummerComp);
            listViewKuchen.setItems(sortedKuchenList);
            listViewHersteller.setItems(FXCollections.observableList(hashmapToList(this.automat.checkHersteller())));
        } catch (EmptyListException e) {
            //e.printStackTrace(); this will always happen when the automat is empty and means nothing
        }

        this.fachnummerComp = new Comparator<KuchenVerkaufsObjektImpl>() {
            @Override
            public int compare(KuchenVerkaufsObjektImpl o1, KuchenVerkaufsObjektImpl o2) {
                return o1.getFachnummer() - o2.getFachnummer();
            }
        };



//        this.herstellerComp = new Comparator<KuchenVerkaufsObjekt>() {
//            @Override
//            public int compare(KuchenVerkaufsObjekt o1, KuchenVerkaufsObjekt o2) {
//                return ;
//            }
//        };

        this.haltbarKeitComp = new Comparator<KuchenVerkaufsObjektImpl>() {
            @Override
            public int compare(KuchenVerkaufsObjektImpl o1, KuchenVerkaufsObjektImpl o2) {
                return o1.getHaltbarkeit().compareTo(o2.getHaltbarkeit());
            }
        };

        SortedList<KuchenVerkaufsObjektImpl> sortedList = new SortedList<KuchenVerkaufsObjektImpl>(listViewKuchen.getItems(), fachnummerComp);

//        toggleGroupSort.selectedToggleProperty().addListener(new ChangeListener<RadioButton>() {
//            @Override
//            public void changed(ObservableValue<? extends RadioButton> observableValue, RadioButton radioButton, RadioButton t1) {
////werte der radiobuttons vergleichen
//                if (sortByFachnummer.equals(t1)) {
//                    sortedList.setComparator(fachnummerComp);
//                } else if (sortByHaltbarkeit.equals(t1)) {
//                    sortedList.setComparator(haltbarKeitComp);
//                } else if (sortByHersteller.equals(t1)) {
//                    sortedList.setComparator(herstellerComp);
//                }
//            }
//        });

        //let all numeric fields only accept numbers source: https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        fachnummerField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    fachnummerField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        fieldPreis.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldPreis.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        fieldHaltbarkeit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldHaltbarkeit.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        fieldNaehrwert.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldNaehrwert.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void onPressAddHersteller() {
        try {
            this.automat.addHersteller(new HerstellerImpl(herstellerField.getText()));
            this.observableHerstellerList = FXCollections.observableList(hashmapToList(automat.checkHersteller()));

        } catch (AlreadyExistsException | EmptyListException e) {
            errorText.setText("Hersteller existiert bereits");
        }
        herstellerField.clear();
    }

    public void onPressRemoveHersteller() {
        try {
            this.automat.removeHersteller(herstellerField.getText());
        } catch (NoSuchElementException e) {
            errorText.setText("Hersteller nicht bekannt");
        }
        herstellerField.clear();
    }

    public void onPressSetInspection() {
        //convert localdate from datepicker to Date  source https://beginnersbook.com/2017/10/java-convert-localdate-to-date/
        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        try {
            this.automat.setInspectionDate(date, Integer.parseInt(fachnummerField.getText()));
        } catch (InvalidInputException e) {
            errorText.setText("ungültige Eingabe");
        }
    }

    public void onPressAddKuchen() {
        if (choiceKremkuchen.equals(choiceKuchen.getValue())) {
            KremkuchenImpl kremkuchen = new KremkuchenImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())), new BigDecimal(Integer.parseInt(fieldPreis.getText())), fieldKremsorte.getText());
            try {
                this.automat.addKuchen(kremkuchen);
            } catch (FullAutomatException e) {
                errorText.setText("Der Automat ist voll");
            } catch (NoSuchElementException e){
                errorText.setText("Hersteller existiert nicht");
            }
        } else if (choiceObstkuchen.equals(choiceKuchen.getValue())) {
            ObstkuchenImpl obstkuchen = new ObstkuchenImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())), new BigDecimal(Integer.parseInt(fieldPreis.getText())), fieldObstsorte.getText());
            try {
                this.automat.addKuchen(obstkuchen);
            } catch (FullAutomatException e) {
                errorText.setText("Der Automat ist voll");
            } catch (NoSuchElementException e){
                errorText.setText("Hersteller existiert nicht");
            }
        } else if (choiceObsttorte.equals(choiceKuchen.getValue())) {
            ObsttorteImpl obsttorte = new ObsttorteImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())), new BigDecimal(Integer.parseInt(fieldPreis.getText())), fieldKremsorte.getText(), fieldObstsorte.getText());
            try {
                this.automat.addKuchen(obsttorte);
            } catch (FullAutomatException e) {
                errorText.setText("Der Automat ist voll");
            } catch (NoSuchElementException e){
                errorText.setText("Hersteller existiert nicht");
            }
        } else {
            errorText.setText("fehler bei auswahl der Kuchensorte");
        }
        //TODO clear all buttons
    }

    public void onPressRemoveKuchen() {
        try {
            this.automat.removeKuchen(Integer.parseInt(fachnummerField.getText()));
        } catch (InvalidInputException e) {
            errorText.setText("Ungültige Eingabe");
        } catch (NoSuchElementException e) {
            errorText.setText("an dieser Fachnummer befindet sich kein Kuchen");
        }
        fachnummerField.clear();
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

    private HashSet<Allergen> stringToSet(String in) {
        HashSet<Allergen> set = null;
        try {
            set = new HashSet();
            String[] splitString = in.split(",");
            for (int i = 0; i < splitString.length; i++) {
                set.add(Allergen.valueOf(splitString[i]));
            }
        } catch (IllegalArgumentException e) {
            errorText.setText("fehler beim Einlesen der Allergene");
        }
        return set;
    }

    private List<HerstellerNummer> hashmapToList(HashMap<String, Integer> hashMap){
        LinkedList<HerstellerNummer> hnList = new LinkedList<>();       //source: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for(String key : hashMap.keySet()){
            hnList.add(new HerstellerNummer(key, hashMap.get(key)));
        }

        return hnList;
    }

    @Override
    public void update() throws EmptyListException, FullAutomatException, InvalidInputException, AlreadyExistsException {

    }
}

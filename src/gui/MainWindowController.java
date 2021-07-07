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

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class MainWindowController {
    @FXML
    private Button sortByHersteller;
    @FXML
    private Button sortByFachnummer;
    @FXML
    private Button sortByHaltbarkeit;
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
    private Label errorText; //TODO databinding maybe
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
//        try {
//            this.automat.addHersteller(new HerstellerImpl("Benjamin"));
//            this.automat.addKuchen(new ObstkuchenImpl(new HerstellerImpl("Benjamin"), new HashSet<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(1), new BigDecimal(500), "mais"));
//            this.automat.addKuchen(new ObstkuchenImpl(new HerstellerImpl("Benjamin"), new HashSet<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(2), new BigDecimal(500), "senf"));
//        } catch (AlreadyExistsException | FullAutomatException e) {
//            e.printStackTrace();
//        }


//probably not necessary if i dont have objekts to begin with
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

        this.haltbarKeitComp = new Comparator<KuchenVerkaufsObjektImpl>() {
            @Override
            public int compare(KuchenVerkaufsObjektImpl o1, KuchenVerkaufsObjektImpl o2) {
                return o1.getHaltbarkeit().compareTo(o2.getHaltbarkeit());
            }
        };

        SortedList<KuchenVerkaufsObjektImpl> sortedList = new SortedList<KuchenVerkaufsObjektImpl>(listViewKuchen.getItems(), fachnummerComp);

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
            updateHersteller();
        } catch (AlreadyExistsException e) {
            errorText.setText("Hersteller existiert bereits");
        }
        herstellerField.clear();
    }

    public void onPressRemoveHersteller() {
        try {
            this.automat.removeHersteller(herstellerField.getText());
            updateHersteller();
        } catch (NoSuchElementException e) {
            errorText.setText("Hersteller nicht bekannt");
        }
        herstellerField.clear();
        updateKuchen();
    }

    public void onPressSetInspection() {
        try {
            this.automat.setInspectionDate(Integer.parseInt(fachnummerField.getText()));
        } catch (InvalidInputException e) {
            errorText.setText("ungültige Eingabe");
        } catch (NoSuchElementException e){
            errorText.setText("an dieser Stelle befindet sich kein Kuchen");
        }
        updateKuchen();
    }

    public void onPressAddKuchen() {
        if (choiceKremkuchen.equals(choiceKuchen.getValue())) {
            KremkuchenImpl kremkuchen = new KremkuchenImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())),
                    new BigDecimal(Integer.parseInt(fieldPreis.getText())), new Kremsorte(fieldKremsorte.getText()));
            try {
                this.automat.addKuchen(kremkuchen);
                clearKuchenFields(); //clear only if the kuchen as been added succesfully
            } catch (FullAutomatException e) {
                errorText.setText("Der Automat ist voll");
            } catch (NoSuchElementException e){
                errorText.setText("Hersteller existiert nicht");
            }
        } else if (choiceObstkuchen.equals(choiceKuchen.getValue())) {
            ObstkuchenImpl obstkuchen = new ObstkuchenImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())),
                    new BigDecimal(Integer.parseInt(fieldPreis.getText())), new Obstsorte(fieldObstsorte.getText()));
            try {
                this.automat.addKuchen(obstkuchen);
                clearKuchenFields(); //clear only if the kuchen as been added succesfully
            } catch (FullAutomatException e) {
                errorText.setText("Der Automat ist voll");
            } catch (NoSuchElementException e){
                errorText.setText("Hersteller existiert nicht");
            }
        } else if (choiceObsttorte.equals(choiceKuchen.getValue())) {
            ObsttorteImpl obsttorte = new ObsttorteImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())),
                    new BigDecimal(Integer.parseInt(fieldPreis.getText())), new Kremsorte(fieldKremsorte.getText()) , new Obstsorte(fieldObstsorte.getText()));
            try {
                this.automat.addKuchen(obsttorte);
                clearKuchenFields(); //clear only if the kuchen as been added succesfully
            } catch (FullAutomatException e) {
                errorText.setText("Der Automat ist voll");
            } catch (NoSuchElementException e){
                errorText.setText("Hersteller existiert nicht");
            }
        } else {
            errorText.setText("fehler bei auswahl der Kuchensorte");
        }
        updateHersteller();
        updateKuchen();
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
        updateHersteller();
        updateKuchen();
    }

    public void onPressSortByHaltbarkeit(){
        try {
            sortedKuchenList = new SortedList<KuchenVerkaufsObjektImpl>(FXCollections.observableList(this.automat.checkKuchen()), new Comparator<KuchenVerkaufsObjektImpl>() {
                @Override
                public int compare(KuchenVerkaufsObjektImpl o1, KuchenVerkaufsObjektImpl o2) {
                    return o1.getHaltbarkeit().compareTo(o2.getHaltbarkeit());
                }
            });
        } catch (EmptyListException e) {
            errorText.setText("automat ist leer");
        }
        listViewKuchen.setItems(sortedKuchenList);
    }

    public void onPressSortByFachnummer(){
        try {
            sortedKuchenList = new SortedList<KuchenVerkaufsObjektImpl>(FXCollections.observableList(this.automat.checkKuchen()), new Comparator<KuchenVerkaufsObjektImpl>() {
                @Override
                public int compare(KuchenVerkaufsObjektImpl o1, KuchenVerkaufsObjektImpl o2) {
                    return o2.getFachnummer()-o1.getFachnummer();
                }
            });
        } catch (EmptyListException e) {
            errorText.setText("automat ist leer");
        }
        listViewKuchen.setItems(sortedKuchenList);
    }

    public void onPressSortByHersteller(){
        try {
            sortedKuchenList = new SortedList<KuchenVerkaufsObjektImpl>(FXCollections.observableList(this.automat.checkKuchen()), new Comparator<KuchenVerkaufsObjektImpl>() {
                @Override
                public int compare(KuchenVerkaufsObjektImpl o1, KuchenVerkaufsObjektImpl o2) {
                    return o1.getHersteller().getName().compareTo(o2.getHersteller().getName());
                }
            });
        } catch (EmptyListException e) {
            errorText.setText("automat ist leer");
        }
        listViewKuchen.setItems(sortedKuchenList);
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

    private void updateKuchen(){
        try {
            observableKuchenList = FXCollections.observableList(this.automat.checkKuchen());
            this.listViewKuchen.setItems(observableKuchenList);
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }

    private void updateHersteller(){
        try {
            this.listViewHersteller.setItems(FXCollections.observableList(this.hashmapToList(this.automat.checkHersteller())));
            //this.observableHerstellerList = FXCollections.observableList(hashmapToList(automat.checkHersteller()));
        } catch (EmptyListException e) {
            errorText.setText("Keine Hersteller");
        }
    }

    private void clearKuchenFields(){
        this.fieldHersteller.clear();
        this.fieldAllergene.clear();
        this.fieldHaltbarkeit.clear();
        this.fieldNaehrwert.clear();
        this.fieldPreis.clear();
        this.fieldKremsorte.clear();
        this.fieldObstsorte.clear();
    }
}

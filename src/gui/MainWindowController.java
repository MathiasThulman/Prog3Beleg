package gui;

import automat.*;
import events.HerstellerNummer;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import persistence.JoSSerializer;

import java.awt.dnd.DragGestureEvent;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class MainWindowController {

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
    private ListView<KuchenKomponente> listViewKuchen;
    @FXML
    private Label errorText;
    public StringProperty errorTextString = new SimpleStringProperty();
    @FXML
    private ChoiceBox choiceKuchen;
    private Automat automat;
    private Comparator<KuchenKomponente> fachnummerComp;
    private Comparator<KuchenKomponente> herstellerComp;
    private Comparator<KuchenKomponente> haltbarKeitComp;
    private ObservableList<KuchenKomponente> observableKuchenList;
    private SortedList<KuchenKomponente> sortedKuchenList;
    @FXML
    private Label allergenLabel;
    JoSSerializer serializer;



    public void initialize() {
        this.errorText.textProperty().bindBidirectional(errorTextString);
        serializer = new JoSSerializer();

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

        this.fachnummerComp = new Comparator<KuchenKomponente>() {
            @Override
            public int compare(KuchenKomponente o1, KuchenKomponente o2) {
                return o1.getFachnummer() - o2.getFachnummer();
            }
        };

        this.haltbarKeitComp = new Comparator<KuchenKomponente>() {
            @Override
            public int compare(KuchenKomponente o1, KuchenKomponente o2) {
                return o1.getHaltbarkeit().compareTo(o2.getHaltbarkeit());
            }
        };

        SortedList<KuchenKomponente> sortedList = new SortedList<KuchenKomponente>(listViewKuchen.getItems(), fachnummerComp);

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

            //if i want to use decimals i cannot use this on the fieldPreis
//        fieldPreis.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue,
//                                String newValue) {
//                if (!newValue.matches("\\d*")) {
//                    fieldPreis.setText(newValue.replaceAll("[^\\d]", ""));
//                }
//            }
//        });

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
        if(this.herstellerField.getText().isEmpty()){
            return;
        }
        try {
            this.automat.addHersteller(new HerstellerImpl(herstellerField.getText()));
            updateHersteller();
        } catch (AlreadyExistsException e) {
            errorTextString.setValue("Hersteller existiert bereits");
        }
        herstellerField.clear();
    }

    public void onPressRemoveHersteller() {
        if(this.herstellerField.getText().isEmpty()){
            return;
        }
        try {
            this.automat.removeHersteller(herstellerField.getText());
        } catch (NoSuchElementException e) {
            errorTextString.setValue("Hersteller nicht bekannt");
        }
        herstellerField.clear();
        updateKuchen();
        updateHersteller();
    }

    //TODO this causes an Error
    public void onPressSetInspection() {
        if(fachnummerField.getText().isEmpty()){
            return;
        }
        try {
            this.automat.setInspectionDate(Integer.parseInt(fachnummerField.getText()));

        } catch (InvalidInputException e) {
            errorTextString.setValue("ungültige Eingabe");
        } catch (NoSuchElementException e) {
            errorTextString.setValue("an dieser Stelle befindet sich kein Kuchen");
        }
        updateKuchen();
    }

    public void onPressAddKuchen() {
        if(fieldHersteller.getText().isEmpty() || fieldPreis.getText().isEmpty() || fieldNaehrwert.getText().isEmpty() || fieldHaltbarkeit.getText().isEmpty() || choiceKuchen.getValue() == null){
            return; //dont allow processing of empty input
        }
        String preisString = fieldPreis.getText().replaceAll(",",".");
        try {
            Double.parseDouble(preisString);
        } catch (NumberFormatException e) {
            return;     //return when anything other than number and , or . have been entered
        }

        if (choiceKremkuchen.equals(choiceKuchen.getValue())) {
            if(fieldKremsorte.getText().isEmpty()){
                return;
            }
            KremkuchenImpl boden = new KremkuchenImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())),
                    new BigDecimal(preisString));
            KuchenBelag kremkuchen = new KuchenBelag(this.fieldKremsorte.getText(), new BigDecimal(0), 0,
                    Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())), new HashSet<>(), boden);
            try {
                this.automat.addKuchen(kremkuchen);
                clearKuchenFields(); //clear only if the kuchen as been added succesfully
            } catch (FullAutomatException e) {
                errorTextString.setValue("Der Automat ist voll");
            } catch (NoSuchElementException e) {
                errorTextString.setValue("Hersteller existiert nicht");
            }
        } else if (choiceObstkuchen.equals(choiceKuchen.getValue())) {
            if(fieldObstsorte.getText().isEmpty()){
                return;
            }
            ObstkuchenImpl boden = new ObstkuchenImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())),
                    new BigDecimal(preisString));
            KuchenBelag obstkuchen = new KuchenBelag(this.fieldObstsorte.getText(), new BigDecimal(0), 0,
                    Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())), new HashSet<>(), boden);
            try {
                this.automat.addKuchen(obstkuchen);
                clearKuchenFields(); //clear only if the kuchen as been added succesfully
            } catch (FullAutomatException e) {
                errorTextString.setValue("Der Automat ist voll");
            } catch (NoSuchElementException e) {
                errorTextString.setValue("Hersteller existiert nicht");
            }
        } else if (choiceObsttorte.equals(choiceKuchen.getValue())) {
            if(fieldObstsorte.getText().isEmpty() || fieldKremsorte.getText().isEmpty()){
                return;
            }
            ObsttorteImpl boden = new ObsttorteImpl(new HerstellerImpl(this.fieldHersteller.getText()), stringToSet(fieldAllergene.getText()),
                    Integer.parseInt(fieldNaehrwert.getText()), Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())),
                    new BigDecimal(preisString));
            KuchenBelag obsttorte = new KuchenBelag(this.fieldKremsorte.getText() + " " + this.fieldObstsorte.getText(), new BigDecimal(0), 0,
                    Duration.ofDays(Long.parseLong(fieldHaltbarkeit.getText())), new HashSet<>(), boden);
            try {
                this.automat.addKuchen(obsttorte);
                clearKuchenFields(); //clear only if the kuchen as been added succesfully
            } catch (FullAutomatException e) {
                errorTextString.setValue("Der Automat ist voll");
            } catch (NoSuchElementException e) {
                errorTextString.setValue("Hersteller existiert nicht");
            }
        } else {
            errorTextString.setValue("fehler bei auswahl der Kuchensorte");
        }
        updateHersteller();
        updateKuchen();
    }


    public void onPressRemoveKuchen() {
        if(this.fachnummerField.getText().isEmpty()){
            return;
        }
        try {
            this.automat.removeKuchen(Integer.parseInt(fachnummerField.getText()));
        } catch (InvalidInputException e) {
            errorTextString.setValue("Ungültige Eingabe");
        } catch (NoSuchElementException e) {
            errorTextString.setValue("an dieser Fachnummer befindet sich kein Kuchen");
        }
        fachnummerField.clear();
        updateHersteller();
        updateKuchen();
    }

    public void onPressSortByHaltbarkeit() {
        try {
            sortedKuchenList = new SortedList<KuchenKomponente>(FXCollections.observableList(this.automat.checkKuchen()), new Comparator<KuchenKomponente>() {
                @Override
                public int compare(KuchenKomponente o1, KuchenKomponente o2) {
                    return getRemainingDuration(o1).compareTo(getRemainingDuration(o2));
                }
            });
        } catch (EmptyListException e) {
            errorTextString.setValue("automat ist leer");
        }
        listViewKuchen.setItems(sortedKuchenList);
    }

    private Duration getRemainingDuration(KuchenKomponente kuchen){
        return kuchen.getHaltbarkeit().minusDays((new Date().getTime() - kuchen.getEinfuegeDatum().getTime()) / (86400000));
    }

    public void onPressSortByFachnummer() {
        try {
            sortedKuchenList = new SortedList<KuchenKomponente>(FXCollections.observableList(this.automat.checkKuchen()), new Comparator<KuchenKomponente>() {
                @Override
                public int compare(KuchenKomponente o1, KuchenKomponente o2) {
                    return o1.getFachnummer() - o2.getFachnummer();
                }
            });
        } catch (EmptyListException e) {
            errorTextString.setValue("automat ist leer");
        }
        listViewKuchen.setItems(sortedKuchenList);
    }



    public void onPressSortByHersteller() {
        try {
            sortedKuchenList = new SortedList<KuchenKomponente>(FXCollections.observableList(this.automat.checkKuchen()), new Comparator<KuchenKomponente>() {
                @Override
                public int compare(KuchenKomponente o1, KuchenKomponente o2) {
                    return o1.getHersteller().getName().compareTo(o2.getHersteller().getName());
                }
            });
        } catch (EmptyListException e) {
            errorTextString.setValue("automat ist leer");
        }
        listViewKuchen.setItems(sortedKuchenList);
    }

    public void onPressShowExistingAllergen(){
        try {
            this.allergenLabel.setText(this.automat.checkAllergen().toString());
        } catch (EmptyListException e) {
            this.errorTextString.setValue("keine Kuchen im Automaten enhalten");
        }
    }

    public void onPressShowAbsentAllergen(){
        try {
            this.allergenLabel.setText(this.automat.checkAbsentAllergen().toString());
        } catch (EmptyListException e) {
            this.errorTextString.setValue("keine Kuchen im Automaten enhalten");
        }
    }

    public void onPressSaveAutomat(){
        serializer.serialize("GuiSaveFile", this.automat);
    }

    public void onPressLoadAutomat(){
        this.automat = serializer.deserialize("GuiSaveFile");
        updateKuchen();
        updateHersteller();
    }


    private HashSet<Allergen> stringToSet(String in) {
        HashSet<Allergen> set = new HashSet<>();
        if(in.length() == 0){
            return set;
        }
        try {
            in = in.trim();//TODO trim properly
            String[] splitString = in.split(",");
            for (int i = 0; i < splitString.length; i++) {
                set.add(Allergen.valueOf(splitString[i]));
            }
        } catch (IllegalArgumentException e) {
            errorTextString.setValue("fehler beim Einlesen der Allergene");
        }
        return set;
    }

    private List<HerstellerNummer> hashmapToList(HashMap<String, Integer> hashMap) {
        LinkedList<HerstellerNummer> hnList = new LinkedList<>();       //source: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for (String key : hashMap.keySet()) {
            hnList.add(new HerstellerNummer(key, hashMap.get(key)));
        }

        return hnList;
    }

    private void updateKuchen() {
        try {
            observableKuchenList = FXCollections.observableList(this.automat.checkKuchen());
            this.listViewKuchen.setItems(observableKuchenList);
        } catch (EmptyListException e) {
            this.errorTextString.setValue("Der Automat ist leer");
            this.listViewKuchen.getItems().clear();//clear the list this way since checkKuchen throws emptyListException when empty and cannot return empty list
        }
    }

    private void updateHersteller() {
        try {
            this.listViewHersteller.setItems(FXCollections.observableList(this.hashmapToList(this.automat.checkHersteller())));
            //this.observableHerstellerList = FXCollections.observableList(hashmapToList(automat.checkHersteller()));
        } catch (EmptyListException e) {
            errorTextString.setValue("Keine Hersteller");
            listViewHersteller.getItems().clear();
        }
    }


    private void clearKuchenFields() {
        this.fieldHersteller.clear();
        this.fieldAllergene.clear();
        this.fieldHaltbarkeit.clear();
        this.fieldNaehrwert.clear();
        this.fieldPreis.clear();
        this.fieldKremsorte.clear();
        this.fieldObstsorte.clear();
    }
}

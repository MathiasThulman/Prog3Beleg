package automat;

import events.*;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KremkuchenImpl;
import kuchen.ObstkuchenImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class AutomatInputIntListener implements EventListener<InputIntEvent> {
    private Automat automat;
    private ErrorEventHandler<ErrorEvent> errorhandler;
    private CollectionOutputHandler<CollectionOutputEvent> collectionHandler;


    @Override
    public void addEvent(InputIntEvent event) {
        switch (event.getType()) {
            case addKuchen:
                switch (event.getInputInt()) {
                    case 1:
                        try {
                            automat.addKuchen(new ObstkuchenImpl(new HerstellerImpl("faustulus"), new LinkedList<>(Collections.singletonList(Allergen.Gluten)), 500, Duration.ofDays(7), new BigDecimal(500), "Erdbeere"));
                        } catch (FullAutomatException e) {
                            this.errorhandler.handle(new ErrorEvent(this, "Dieser Automat ist voll"));
                        } catch (NoSuchElementException e){
                            this.errorhandler.handle(new ErrorEvent(this, "Hersteller dieses Kuchens konnte nicht gefunden Werden, bitte Hersteller hinzufügen"));
                        }
                        break;
                    case 2:
                        try {
                            automat.addKuchen(new KremkuchenImpl(new HerstellerImpl("bob"), new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss)), 2000, Duration.ofDays(5), new BigDecimal(400), "Butter"));
                        } catch (FullAutomatException e) {
                            this.errorhandler.handle(new ErrorEvent(this, "Dieser Automat ist voll"));
                        } catch (NoSuchElementException e){
                            this.errorhandler.handle(new ErrorEvent(this, "Hersteller dieses Kuchens konnte nicht gefunden Werden, bitte Hersteller hinzufügen"));
                        }
                        break;
                }
                break;
            case removeKuchen:
                try {
                    automat.removeKuchen(event.getInputInt());
                } catch (InvalidInputException e) {
                    errorhandler.handle(new ErrorEvent(this, "Ungütlige eingabe, diese fachnummer existiert nicht"));
                } catch (NoSuchElementException e){
                    this.errorhandler.handle(new ErrorEvent(this, "an dieser Stelle befindet sich kein Kuchen"));
                }
                break;
            case getOneKuchen:
                try {
                    collectionHandler.handle(new CollectionOutputEvent(this, Collections.singleton(automat.getKuchen(event.getInputInt())))); //nicht so schön
                } catch (InvalidInputException e) {
                    this.errorhandler.handle(new ErrorEvent(this, "Ungültige Eingabe"));
                } catch (NoSuchElementException e){
                    this.errorhandler.handle(new ErrorEvent(this, "an dieser Stelle befindet sich kein Kuchen"));
                }
                break;
            case setDate:
                try {
                    automat.setInspectionDate(event.getInputDate(), event.getInputInt());
                } catch (InvalidInputException e) {
                    this.errorhandler.handle(new ErrorEvent(this, "Ungültige Eingabe"));;
                }catch (NoSuchElementException e){
                    this.errorhandler.handle(new ErrorEvent(this, "an dieser Stelle befindet sich kein Kuchen"));
                }
        }
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

    public void setErrorhandler(ErrorEventHandler<ErrorEvent> errorhandler) {
        this.errorhandler = errorhandler;
    }

    public void setCollectionHandler(CollectionOutputHandler<CollectionOutputEvent> collectionHandler) {
        this.collectionHandler = collectionHandler;
    }
}

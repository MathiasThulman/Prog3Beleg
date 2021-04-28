package automat;

import events.InputEvent;
import events.InputEventListener;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KremkuchenImpl;
import kuchen.ObstkuchenImpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class AutomatInputListener implements InputEventListener {
    private AutomatImpl automat;
    private final String ADDKUCHEN = "addKuchen";
    private final String ADDHERSTELLER = "addHersteller";
    private final String REMOVEHERSTELLER = "removeHersteller";
    private final String REMOVEKUCHEN = "removeKuchen";
    private final String GETKUCHEN = "getKuchen";
    private final String CHECKHERSTELLER = "checkHersteller";
    private final String CHECKKUCHEN = "checkKuchen";
    private final String CHECKKUCHESPECIFIC = "checkKuchenSpecific";
    private final String CHECKALLERGEN = "checkAllergen";
    private final String SETINSPECTIONDATE = "setInspectionDate";

    @Override
    public void addEvent(InputEvent event) throws FullAutomatException, AlreadyExistsException, InvalidInputException, EmptyListException {
        int commandIndex = event.getMessage().indexOf('#');
        String commandString = event.getMessage().substring(0, commandIndex);

        String parameterString = event.getMessage().substring(commandIndex + 1);

        switch (commandString) {
            case ADDHERSTELLER:
                automat.addHersteller(new HerstellerImpl(parameterString));
                break;
            case REMOVEHERSTELLER:
                automat.removeHersteller(parameterString);
                break;
            case ADDKUCHEN:
                switch (parameterString) {
                    case "1":
                        automat.addKuchen(new ObstkuchenImpl(new HerstellerImpl("faustulus"), new LinkedList<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(7), new BigDecimal(500), "Erdbeere"));
                        break;
                    case "2":
                        automat.addKuchen(new KremkuchenImpl(new HerstellerImpl("bob"), new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss)), 2000, Duration.ofDays(5), new BigDecimal(400), "Butter"));
                }
            case GETKUCHEN:
                automat.getKuchen(Integer.parseInt(parameterString));
                //TODO OutputEvent?
                break;
            case REMOVEKUCHEN:
                automat.removeKuchen(Integer.parseInt(parameterString));
                break;
            case CHECKHERSTELLER:
                automat.checkHersteller();
                break;
                //TODO where is the event created who prints?
            case CHECKKUCHEN:
                automat.checkKuchen();
                break;
                //TODO where is the event created who prints?
            case CHECKKUCHESPECIFIC:
                //TODO write this
                break;
            case CHECKALLERGEN:
                automat.checkAllergen();
                //TODO where is the event created who prints?
                break;
            case SETINSPECTIONDATE:
                //automat.setInspectionDate(convertDate(parameterString));
        }
    }

    public void setAutomat(AutomatImpl automat) {
        this.automat = automat;
    }

    private Date convertDate(String parameterString) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date date = df.parse(parameterString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

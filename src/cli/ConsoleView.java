package cli;

import events.CollectionOutputEvent;
import events.ErrorEvent;

public class ConsoleView {


    public void printError(ErrorEvent error){
        System.out.println(error.getError());
    }

    public void printCollectionEvent(CollectionOutputEvent event){

        for(Object object : event.getEventCollection()){
            if(object != null) {
                System.out.println(object.toString());
            }
        }
    }
}

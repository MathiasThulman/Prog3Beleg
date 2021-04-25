package cli;


import java.util.Scanner;

public class Console {
    private InputEventHandler handler;

    public Console(){
    }

    public void setHandler(InputEventHandler handler){
        this.handler = handler;
    }

    public void start(){
        try(Scanner s=new Scanner(System.in)){
            do {
                System.out.println("Enter text:");
                String text=s.next();
                InputEvent e=new InputEvent(this,text);
                if(null!=this.handler) handler.handle(e);
            }while (true);
        }
    }
}

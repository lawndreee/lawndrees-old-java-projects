import javafx.scene.image.Image;

public class MC extends Character{
    public MC(){
        super("Gekko", 100, 50, new Image("images/gekko.png"), new Image("images/mcspot.png"));
        setInfobar(new Image("images/gekkohbar.png"));
    }

}

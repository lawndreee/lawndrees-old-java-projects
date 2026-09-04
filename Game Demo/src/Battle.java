import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.ArrayList;

/*
- default 3 moves per turn
 */
public class Battle extends Application{
    private Stage stage;
    private Scene scene;
    private Scene gameover;
    private int currturn;
    private int turnstotal;
    private int currmove;
    private MC gekko = new MC();
    private Enemy boo = new Enemy("bad guy", 100, 50);
    private String[] usermoves;
    private HBox userchoice = new HBox();
    private ArrayList<String> enemymoves;
    private ImageView pmove;
    private BorderPane bp = new BorderPane();
    private ImageView mchealth = new ImageView(new Image("images/healthbar.png"));
    private ImageView boohealth = new ImageView(new Image("images/healthbar.png"));
    private Text results = new Text();
    private ImageView boopos = new ImageView();
    private Text stuninfo = new Text();
    private ImageView minimap = new ImageView();
    private MediaPlayer bgmusic;

    private Media lost = new Media(getClass().getResource("sound/lostgame.mp3").toURI().toString());
    private Media won = new Media(getClass().getResource("sound/wongame.mp3").toURI().toString());
    Media gamebg = new Media(getClass().getResource("sound/greater than one.mp3").toURI().toString());

    private Image move = new Image("images/emptymove.png");

    public Battle() throws URISyntaxException {
    }

    @Override
    /*
    - set bg image to current area
    - while loop: currturns < turnstotal && enemy + mc health isnt 0
        - make mc choose 3 moves
            - each move chosen (need to figure this out with like user interface stuff) update moves cards
            - IF TIME AVAILABLE: make players able to reselect moves
        - execute moves, if enemy is allowed to deal dmg to user it will
            - on every move, if position change then change bg
        - enemy will do 3 moves, same rules from before apply
            - enemy moves will NOT be displayed
        - currturn++
    */
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResource("fonts/SourceCodePro.ttf").toExternalForm(), 30);
        Font.loadFont(getClass().getResource("fonts/PixelifySans.ttf").toExternalForm(), 30);
        this.stage = primaryStage;
        AnchorPane battle = new AnchorPane();
        scene = new Scene(bp);
        bp.setCenter(battle);
        scene.getStylesheets().add("styles.css");

        AnchorPane gg = new AnchorPane();
        gameover = new Scene(gg);
        gameover.getStylesheets().add("gameover.css");
        Button retry = new Button();
        retry.setText("Retry");
        gg.getChildren().add(retry);
        retry.setLayoutX(400);
        retry.setLayoutY(500);
        gg.getChildren().add(results);
        results.setX(360);
        results.setY(465);


        minimap.setImage(new Image("images/map.png"));
        minimap.setFitWidth(150);
        minimap.setPreserveRatio(true);
        minimap.setX(800);
        battle.getChildren().addAll(minimap, gekko.getMapicon(), boo.getMapicon());
        gekko.setmapicony(90);
        boo.setmapicony(10);
        gekko.setmapiconx(860);
        boo.setmapiconx(860);
        boo.getMapicon().setVisible(false);


        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setTitle("please please please display something pelase pleaf AAAA");
        currturn = 1;
        currmove = 0;
        turnstotal = 10;
        usermoves = new String[3];

        //initialize character infobars on screen
        ImageView mcbar = new ImageView(gekko.getInfobar());
        ImageView boobar = new ImageView(boo.getInfobar());
        mcbar.setFitWidth(200);
        mcbar.setPreserveRatio(true);
        boobar.setPreserveRatio(true);
        boobar.setFitWidth(200);
        mchealth.setFitWidth(140);
        boohealth.setFitWidth(140);
        mchealth.setFitHeight(30);
        boohealth.setFitHeight(30);
        VBox infobars = new VBox();
        VBox hbars = new VBox();
        hbars.setLayoutY(20);
        hbars.setLayoutX(50);
        hbars.setSpacing(45);
        hbars.getChildren().addAll(mchealth, boohealth);
        infobars.getChildren().addAll(mcbar, boobar);
        bp.getChildren().addAll(infobars, hbars);

        Ability stun = new Ability("Stun", new Image("images/stunned.png"));
        Ability survey = new Ability("Survey", null);
        Ability guard = new Ability("Guard", new Image("images/guarded.png"));
        gekko.addAbility(stun);
        gekko.addAbility(survey);
        gekko.addAbility(guard);


        bgmusic = new MediaPlayer(gamebg);
        bgmusic.play();

        //initialize move slots on screen

        pmove = new ImageView(move);
        pmove.setFitWidth(100);
        pmove.setPreserveRatio(true);

        //player choice buttons
        Button attackmove = new Button();
        Button gomove = new Button();
        Button abilitymove = new Button();
        attackmove.setText("attack");
        gomove.setText("move");
        abilitymove.setText("abilities");
        battle.getChildren().add(pmove);
        pmove.setX(410.0);
        pmove.setY(10.0);

        boopos.setImage(boo.getimg());
        battle.getChildren().add(boopos);
        boopos.setVisible(false);
        boopos.setFitWidth(200);
        boopos.setPreserveRatio(true);

        Button help = new Button("Help");
        Label helptext = new Label("You must kill the enemy! \nYou can move to your left and right via the move buttons \nto hide from enemy attacks and move into open space. \nYou can only attack the enemy if you are in open \nspace and can see the enemy on screen! \n\nMake sure to use your abilities to aid you.\n Stun will give the enemy a chance of damaging to themselves for 3 turns. \n Survey will display their current location on the top right minimap.\n Guard will block the next instance of damage.\n\n Good luck! \n(click help again to get rid of this text)");
        Popup helpinfo = new Popup();
        helpinfo.getContent().add(helptext);
        userchoice.getChildren().add(help);

        userchoice.getChildren().add(attackmove);
        userchoice.getChildren().add(gomove);
        userchoice.getChildren().add(abilitymove);

        HBox movelist = new HBox();
        VBox abilitylist = new VBox();

        ImageView goleft = new ImageView(new Image("images/goleft.png"));
        ImageView goright = new ImageView(new Image("images/goright.png"));
        ImageView stunimg = new ImageView(new Image("images/stun.png"));
        ImageView surveyimg = new ImageView(new Image("images/survey.png"));
        ImageView guardimg = new ImageView(new Image("images/guard.png"));


        stunimg.setFitWidth(200);
        stunimg.setPreserveRatio(true);
        surveyimg.setFitWidth(200);
        surveyimg.setPreserveRatio(true);
        guardimg.setFitWidth(200);
        guardimg.setPreserveRatio(true);

        goleft.setFitWidth(100);
        goleft.setPreserveRatio(true);
        goright.setFitWidth(100);
        goright.setPreserveRatio(true);
        movelist.getChildren().addAll(goleft, goright);

        stuninfo.setText("Turns left: " + stun.getTurns() + "\nUses Left: " + stun.getUses());
        Text surveyinfo = new Text("Uses Left: " + survey.getUses());
        Text guardinfo = new Text("Uses Left: " + guard.getUses());
        abilitylist.getChildren().addAll(stunimg,stuninfo , surveyimg, surveyinfo,guardimg, guardinfo);
        bp.getChildren().add(movelist);
        bp.getChildren().add(abilitylist);
        movelist.setLayoutY(100);
        movelist.setLayoutX(780);
        abilitylist.setLayoutY(260);
        abilitylist.setLayoutX(780);
        movelist.setVisible(false);
        abilitylist.setVisible(false);
        bp.setBottom(userchoice);

           pmove.setImage(move);

           help.setOnAction(e->{
               if (!helpinfo.isShowing())
                   helpinfo.show(stage);
               else
                  helpinfo.hide();
           });
           attackmove.setOnAction(e -> {
               if (gekko.canattack(boo)) {
                   movelist.setVisible(false);
                   userchoice.setVisible(false);
                   abilitylist.setVisible(false);
                   usermove("attack");
               }else {
                   System.out.println("can't attack here !");
               }
           });
           gomove.setOnAction(e -> {
               abilitylist.setVisible(false);
               System.out.println("moving...");
               movelist.setVisible(true);
           });
           abilitymove.setOnAction(e -> {
               movelist.setVisible(false);
               System.out.println("abilty used...");
               abilitylist.setVisible(true);
           });
           stunimg.setOnMouseClicked(i -> {
               if (abilitylist.isVisible() && stun.getTurns() == 0) {
                   movelist.setVisible(false);
                   userchoice.setVisible(false);
                   abilitylist.setVisible(false);
                   boo.addStatus(stun);
                   stun.useAbility(boo);
                   System.out.println(stun.getUses() + " " + stun.getTurns());
                   usermove("stun");
               }
               stuninfo.setText("Turns left: " + stun.getTurns() + "\nUses Left: " + stun.getUses());
           });
           surveyimg.setOnMouseClicked(i -> {
               if (abilitylist.isVisible()) {
                   abilitylist.setVisible(false);
                   movelist.setVisible(false);
                   userchoice.setVisible(false);
                   survey.useAbility(boo);
                   System.out.println(survey.getUses());
                   surveyinfo.setText("Uses Left: " + survey.getUses());
                   usermove("survey");
               }
           });
           guardimg.setOnMouseClicked(i -> {
               if (abilitylist.isVisible()) {
                   movelist.setVisible(false);
                   userchoice.setVisible(false);
                   abilitylist.setVisible(false);
                   boo.addStatus(guard);
                   guard.useAbility(boo);
                   System.out.println(guard.getUses());
                   guardinfo.setText("Uses Left: " + guard.getUses());
                   usermove("guard");
               }
           });
           goleft.setOnMouseClicked(i -> {
               if (movelist.isVisible() && gekko.canmove("left")) {
                   movelist.setVisible(false);
                   userchoice.setVisible(false);
                   abilitylist.setVisible(false);
                   usermove("left");
               } else System.out.println("can't move there !");
               System.out.println(gekko.getPosition());
           });
           goright.setOnMouseClicked(i -> {
               if (movelist.isVisible() && gekko.canmove("right")) {
                   movelist.setVisible(false);
                   userchoice.setVisible(false);
                   abilitylist.setVisible(false);
                  usermove("right");
               }else System.out.println("can't move there !");
               System.out.println(gekko.getPosition());
           });
           retry.setOnAction(e->{
               greset();
           });


        stage.centerOnScreen();
        stage.show();
    }

    public void usermove(String move){
        if (boo.getMapicon().isVisible()){
            boo.getMapicon().setVisible(false);
        }
        pmove.setImage(new Image("images/move" + move + ".png"));
        if (move.equals("left") || move.equals("right")) {
            gekko.move(move);
            mapchange(gekko);
            String bg = "images/bgpos" + gekko.getPosition() + ".png";
            System.out.println(bg);
            bp.setBackground(new Background(new BackgroundImage(new Image(bg), null, null, null, new BackgroundSize(1000, 700, false, false, false, false))));
           enemypos();
        }if (move.equals("attack")) {
            hbar(boohealth, gekko.attack(boo));
        }
        if (move.equals("survey")){
            mapchange(boo);
            boo.getMapicon().setVisible(true);
        }
        for (int i = 0; i < boo.getStatus().size(); i++){
            if (boo.getStatus().get(i).getName().equals("Stun")){
                boo.getStatus().get(i).turns(boo);
                stuninfo.setText("Turns left: " + boo.getStatus().get(i).getTurns() + "\nUses Left: " + boo.getStatus().get(i).getUses());
            }
        }
           enemymove(gekko);
    }
    public void enemymove(Character enemy){
        String enemymove = boo.bestmove(enemy);
        enemypos();
        if (enemymove.equals("attack")){
            int h = boo.attack(gekko);
            hbar(mchealth, h);
        }
        if (enemymove.equals("healing")){
            hbar(boohealth, boo.getHealth());
        }
        for (int i = 0; i < gekko.getStatus().size(); i++) {
            if (gekko.getStatus().get(i).getName().equals("evade")){
                gekko.getStatus().get(i).turns(gekko);
            }
        }
            System.out.println(gekko.getHealth());
            System.out.println(boo.getHealth());
            currmove = 0;
            userchoice.setVisible(true);
            pmove.setImage(move);

        if(boo.getHealth() <= 0 || gekko.getHealth() <= 0){
            System.out.println("womp womp !!");
            stage.setScene(gameover);
            if (boo.getHealth() == 0){
                results.setText("You Won!!");
                bgchange(won);
            }else {
                results.setText("Damn you suck lol");
                bgchange(lost);
            }
        }
    }
    //set health bar's new size to the health remaining
    public void hbar(ImageView bar, int health){
        bar.setFitWidth((int)health*1.4);
    }
    public void greset(){
        gekko.setHealth(100);
        gekko.setPosition(2);
        boo.setPosition(2);
        mapchange(gekko);
        mapchange(boo);
        boo.getMapicon().setVisible(false);
        boopos.setVisible(false);
        hbar(mchealth, 100);
        boo.setHealth(100);
        hbar(boohealth, 100);
        currmove = 0;
        for (int i = 0; i < gekko.getStatus().size(); i++) {
            gekko.getStatus().remove(gekko.getStatus().get(i));
        }
        for (int i = 0; i < boo.getStatus().size(); i++) {
            boo.getStatus().remove(boo.getStatus().get(i));
        }
        for (int i = 0; i < gekko.getAbilities().size(); i++) {
            gekko.getAbilities().get(i).setUses(4);
        }
        for (int i = 0; i < boo.getAbilities().size(); i++) {
            boo.getAbilities().get(i).setUses(4);
        }
        stage.setScene(scene);
        bgchange(gamebg);
    }

    public void enemypos(){
        if (gekko.getPosition() == 1 && boo.getPosition() == 3){
            boopos.setVisible(true);
            boopos.setX(520);
            boopos.setY(300);
        }
        else if (gekko.getPosition() == 3 && boo.getPosition() == 1){
            boopos.setVisible(true);
            boopos.setX(90);
            boopos.setY(300);
        }
        else if ((gekko.getPosition() == 1 || gekko.getPosition() == 3) && boo.getPosition() == 2){
            boopos.setVisible(true);
            if (gekko.getPosition() == 1){
            boopos.setX(200);}
            else{
                boopos.setX(500);
            }
            boopos.setY(300);
        }else boopos.setVisible(false);
    }
    public void mapchange(Character icon){
        if (icon.getPosition() == 1) {
            icon.setmapiconx(820);
        }else if (icon.getPosition() == 2){
         icon.setmapiconx(860);
        }
        else icon.setmapiconx(900);
    }
    public void bgchange(Media bg){
        bgmusic.stop();
        bgmusic = new MediaPlayer(bg);
        bgmusic.play();
    }
    }

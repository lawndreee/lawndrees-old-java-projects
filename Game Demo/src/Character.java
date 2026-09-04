import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Character extends ImageView{
    private int health;
    private int attack;
    private String name;
    private Image image;
    private Image infobar;
    private int accuracy;
    private int position;
    private ImageView mapicon;
    private ArrayList<Ability> abilities = new ArrayList<Ability>();
    private ArrayList<Ability> status = new ArrayList<Ability>();
    public Character(String name, int h, int atk, Image img, Image icon){
        this.name = name;
        health = h;
        attack = atk;
        accuracy = 85;
        position = 2;
        image = img;
        infobar = new Image("images/characinfo.png");
        abilities = new ArrayList<>();
        status = new ArrayList<>();
        mapicon = new ImageView();
        mapicon.setImage(icon);
        mapicon.setFitWidth(40);
        mapicon.setPreserveRatio(true);
    }
    public void setmapiconx(int x){
        mapicon.setX(x);

    }
    public void setmapicony(int y){
        mapicon.setY(y);

    }
    public Image getimg(){
        return image;
    }
    public Image getInfobar() {
        return infobar;
    }
    public void setInfobar(Image info){
        infobar = info;
    }
    public ImageView getMapicon(){
        return mapicon;
    }

    public void addAbility(Ability ability){
        abilities.add(ability);
    }
    public String getName(){
        return name;
    }
    public void takeDmg(int damage){
        health -= damage;
    }
    public void addStatus(Ability status){
        this.status.add(status);
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
    public void setAttack(int atk) {attack = atk; }
    public void setPosition(int pos){position = pos; }

    public int getPosition() {
        return position;
    }
    public ArrayList<Ability> getAbilities() {
        return abilities;
    }
    public ArrayList<Ability> getStatus() {
        return status;
    }

    public boolean canmove(String direction){
        if ((direction.equals("right") && position != 3) || (direction.equals("left")) && position != 1){
            return true;
        }
        return false;
    }
    public int move(String direction){
        if (direction.equals("right")){
                position++;
        }if (direction.equals("left")){
                position--;
        }
        return position;
    }

    public int attack(Character enemy){
        int chance = (int)(Math.random()*100);
        boolean stunned = false;
        for (int i = 0; i < status.size(); i++){
            if (status.get(i).getName().equals("Stun")){
                stunned = true;
            }if(status.get(i).equals("Guard")){
                System.out.println("attack blocked!");
                statusdone("Guard");
                return enemy.getHealth();
            }
        }
        if (chance <= accuracy){
            if (!stunned) {
                System.out.println(attack * 0.4);
                if (enemy.getHealth() <= attack * 0.4) {
                    enemy.setHealth(0);
                } else enemy.setHealth((int) (enemy.getHealth() - attack * 0.4));
            }else {
                if (chance <= 50){
                    health -= attack *0.4;
                    return health;
                }
            }
        }
        return enemy.getHealth();
    }
    public boolean canattack(Character enemy){
        if (enemy.getClass().getName().equals("MC")) {
            if ((enemy.getPosition() != 2 && position == 2) || enemy.getPosition() == 1 && position == 3 || enemy.getPosition() == 3 && position == 1) {
                return true;
            }
        }
        if (enemy.getClass().getName().equals("Enemy")){
            if ((enemy.getPosition() == 2 && (position != 2)) || enemy.getPosition() == 3 && position == 1 || enemy.getPosition() == 1 && position == 3){
                return true;
            }
        }
        return false;
    }
    public void statusdone(String status){
        for (int i = 0; i < this.status.size(); i++){
            if (this.status.get(i).equals(status)){
                this.status.remove(status);
            }
        }
    }
    public void update(){
        if (position == 1){

        }
    }
}

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ability extends ImageView {
    private String name;
    private int uses;
    private int effect;
    private int turns;
    private ImageView status;
    public Ability(String name, Image image) {
        this.name = name;
        uses = 4;
    }
    public void setImage(){
        status.setFitWidth(20);
        status.setPreserveRatio(true);
    }
    public String getName() {
        return name;
    }
    public void setUses(int val){
            uses = val;
    }
    public int geteffect() {
        return effect;
    }
    public void setTurns(int val){
        turns = val;
    }
    public int getUses() {
        return uses;
    }
    public void setEffect(int eff){
        effect = eff;
    }
    public int getTurns() {
        return turns;
    }
    public void turns(Character me){
        if (turns == 0){
            resetStats(me);
        }else{
            turns--;
        }
    }
    public void useAbility(Character enemy){
        uses--;
        if (name.equals("Survey")){
            System.out.println(enemy.getPosition());
        }if (name.equals("Stun")){
            turns = 4;
        }
        if (name.equals("evade")) {
            enemy.setAccuracy(50);
            turns = 3;
        }if (name.equals("remedy")){
            if ((int)(enemy.getHealth() * 1.5) > 100){
                enemy.setHealth(100);
            }else enemy.setHealth((int)(enemy.getHealth() *1.5));
        }
    }
    public void resetStats(Character enemy){
       enemy.setAccuracy(85);
       enemy.statusdone(name);
    }
}

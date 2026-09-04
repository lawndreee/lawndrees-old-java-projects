import javafx.scene.image.Image;

public class Enemy extends Character{
    private Ability remedy = new Ability("remedy", null);
   private Ability evade = new Ability("evade", new Image("images/evade.png"));
    public Enemy(String name, int health, int attack) {
        super(name, health, attack, new Image("images/stinky.png"), new Image("images/enemyspot.png"));
        this.addAbility(remedy);
        this.addAbility(evade);
    }
    public String bestmove(Character gekko){
        System.out.println("moving");
        int move = (int) (Math.random() * 2 + 1);
        boolean stunned = false;
        for (int i = 0; i < this.getStatus().size(); i++){
            if (this.getStatus().get(i).getName().equals("Stun")){
                stunned = true;
            }
        }
        boolean guarded = false;
        for (int i = 0; i < gekko.getAbilities().size(); i++) {
            if (gekko.getAbilities().get(i).getName().equals("Guard")){
                guarded = true;
            }
        }
        if (gekko.getPosition() == 1 || gekko.getPosition() == 3){
           if (this.getPosition() == 2){
               if (this.getHealth() >= 80){
                   if (stunned) {
                       if (remedy.getUses() > 0) remedy.useAbility(this);
                       else {
                           System.out.println("moved away");
                           if (move == 1) {
                               this.move("left");
                           } else this.move("right");
                       }
                   }else {
                       return "attack";
                   }
               }else{
                   if (this.getHealth() >= 60){
                       System.out.println("attacking");
                       return "attack";
                   }else{
                       if (remedy.getUses() > 0 && this.getHealth() >= 40){
                           remedy.useAbility(this);
                       }else {
                           System.out.println("moved away");
                           if (move == 1) {
                               this.move("left");
                           } else this.move("right");
                       }
                   }
               }
           } else{
               if ((this.getHealth() >= 60 && this.getStatus().size() <= 0) || evade.getTurns() > 0){
                   System.out.println("moved away");
                   if (this.getPosition() == 1){
                       this.move("right");
                   }else this.move("left");
               }else if (evade.getUses() > 0){
                   System.out.println("evade used");
                   evade.useAbility(gekko);
               }else {
                   if (remedy.getUses() > 0){
                       System.out.println("healing!");
                       remedy.useAbility(this);
                       return "healing";
                   }
               }
           }
        }else {
            if (this.getPosition() == 2){
                System.out.println("moved away");
                if (move == 1) {
                    this.move("left");
                } else this.move("right");
            }else {
                System.out.println("healing");
                if (this.getHealth() < 60 && remedy.getUses() > 0){
                    remedy.useAbility(this);
                    return "healing";
                }
            }
        }
        return "move done";
    }
}

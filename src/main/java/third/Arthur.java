package third;

public class Arthur {

    private boolean isFlying = false;
    private Building seenBuilding;

    public void setSeenBuilding(Building seenBuilding) {
        this.seenBuilding = seenBuilding;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void notice(boolean flying) {
        isFlying = flying;
        doFlyingAction(checkFlyingState());
    }

    public String checkFlyingState(){
        if (isFlying) {
            return "";
        } else {
            return " не";
        }

    }

    private void doFlyingAction(String isFlying){
        System.out.print("Артур обнаружил, что он" + isFlying + " скользит по воздуху ");
    }

    public boolean tryChaseWindow(){
        if (seenBuilding != null && seenBuilding.getBuilding(seenBuilding.getFloors(),
                seenBuilding.getWindows())){
            System.out.print("к одному из величественных окон во втором этаже здания");
            seenBuilding.isPlatform();
            return true;
        } else {
            System.out.print("но здания перед глазами Артура не оказалось...");
        }
        return false;
    }

}

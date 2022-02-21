package third;

public class Location {

    private Crowd crowd;
    private Arthur arthur;

    public Crowd getCrowd() {
        return crowd;
    }

    public Arthur getArthur() {
        return arthur;
    }

    public Building getBuilding() {
        return building;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    private Building building;
    private Speaker speaker;

    private boolean hasBuilding = false;
    private boolean hasCrowd = false;
    private boolean hasSpeaker = false;

    public Location(boolean hasBuilding, boolean hasCrowd, boolean hasSpeaker) {
        this.hasBuilding = hasBuilding;
        this.hasCrowd = hasCrowd;
        this.hasSpeaker = hasSpeaker;
        initLocation();
    }

    public void initLocation() {
        if (hasBuilding) {
            building = new Building();
        }
        if (hasSpeaker) {
            speaker = new Speaker();
        }

        if (hasCrowd) {
            crowd = new Crowd();
        }

        arthur = new Arthur();
    }

    public void makeStory(){
        building.setFloors(3);
        building.setWindows(10);
        building.initPlatform();

        arthur.notice(true);
        arthur.setSeenBuilding(building);
        arthur.tryChaseWindow();

        speaker.setPlatform(building.getPlatform());
        crowd.setPlatform(building.getPlatform());

        speaker.trySayingMessage();
        crowd.doActionOnPlatform();

    }

}

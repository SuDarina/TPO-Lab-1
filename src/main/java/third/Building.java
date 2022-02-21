package third;

public class Building {
    private int floors;
    private int windows;

    public Building() {}

    public Building(int floors, int windows) {
        this.floors = floors;
        this.windows = windows;
    }

    private Platform platform;

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getWindows() {
        return windows;
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }

    public Platform getPlatform() {
        return platform;
    }

    public boolean initPlatform() {
        if(this.getBuilding(floors, windows))  {
            platform = new Platform();
            return true;
        }
        return false;
    }

    public void isPlatform(){
        if (platform != null){
            System.out.print(", перед которым стоял помост");
        }
    }


    boolean getBuilding(int floors, int windows){
        setWindows(windows);
        setFloors(floors);
        return floors >= 2 && windows >= 1;
    }
}

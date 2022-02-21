package third;

public class Crowd {

    public boolean isScreaming = false;
    private Platform platform;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
        platform.setContainsCrowd(true);
    }


    private void tryToScream() {
        isScreaming = true;
        scream();
    }

    private boolean isListening() {
        return isScreaming = false;
    }

    private void scream() {
        System.out.print("После того, как толпа вновь разразилась ликующими криками, ");
    }

    public boolean doActionOnPlatform(){
        if (platform != null && platform.isContainsCrowd()) {
            if (platform.isContainsSpeaker())
                isListening();
            else tryToScream();
            return true;
        }
        return false;
    }

}

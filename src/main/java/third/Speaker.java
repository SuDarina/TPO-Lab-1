package third;

public class Speaker {

    private Platform platform;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
        platform.setContainsSpeaker(true);
    }

    public boolean trySayingMessage() {
        if(platform != null) {
            if(platform.isContainsCrowd() && platform.isContainsSpeaker()){
                doSayMessage();
                return true;
            }
        }
        return false;
    }

    private void doSayMessage(){
        System.out.print(", с которого оратор обращался к народу.");
    }

}

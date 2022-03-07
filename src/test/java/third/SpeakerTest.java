package third;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class SpeakerTest {

    //   Данные для теста isArthurChasingTheWindow()/initPlatform
    //    {int floors, int windows, boolean expected}
    public static Collection<Object[]> dataBuilding() {
        return Arrays.asList(new Object[][]{
                {1, 1, false}, {2, 0, false},
                {3, 1, true}, {10, 10, true}
        });
    }

    //    Данные для теста isArthurFlying {String state, boolean notice}
    public static Collection<Object[]> dataFlying() {
        return Arrays.asList(new Object[][]{
                {" не", false}, {"", true}
        });
    }

    Location location;

    @BeforeEach
    public void setUp() {
        location = new Location(true, true, true);
        location.initLocation();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void isSpeakerTalk(boolean containsCrowd){
        Platform platform = new Platform();
        platform.setContainsCrowd(containsCrowd);
        if(containsCrowd) location.getCrowd().setPlatform(platform);
        platform.setContainsSpeaker(true);
        location.getSpeaker().setPlatform(platform);

        location.getCrowd().doActionOnPlatform();
        assertEquals(containsCrowd, location.getSpeaker().trySayingMessage());
    }
}
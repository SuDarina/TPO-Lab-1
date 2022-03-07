package third;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class DomainModelTest{

    Location location;

//    Данные для теста isArthurChasingTheWindow()/initPlatform
//    {int floors, int windows, boolean expected}
    public static Collection<Object[]> dataBuilding() {
        return Arrays.asList(new Object[][] {
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



    @BeforeEach
    public void setUp() {
        location = new Location(true, true, true);
        location.initLocation();
    }

//    Проверяет увидит ли Артур перед глазами нужное здание в
//    зависимости от того, сколько в здании этажей и окон на этажах
    @ParameterizedTest
    @MethodSource("dataBuilding")
    public void isArthurChasingTheWindow(int floors, int windows, boolean expected){
        location.getBuilding().setFloors(floors);
        location.getBuilding().setWindows(windows);
        location.getArthur().setSeenBuilding(location.getBuilding());
        assertEquals(expected, location.getArthur().tryChaseWindow());
    }

    @ParameterizedTest
    @MethodSource("dataFlying")
    public void isArthurFlying(String state, boolean notice) {
        location.getArthur().notice(notice);
        Assertions.assertEquals(state, location.getArthur().checkFlyingState());
    }

//    Проверка инициализации платформы
    @ParameterizedTest
    @MethodSource("dataBuilding")
    public void isPlatformInit(int floors, int windows, boolean expected){
        location.getBuilding().setFloors(floors);
        location.getBuilding().setWindows(windows);
        assertEquals(expected, location.getBuilding().initPlatform());
    }

// Может ли народ что-то делать на платформе, если платформа не установлена?
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void isDoActionPossible(boolean setPlatform){
        if(setPlatform){
            Platform platform = new Platform();
            location.getCrowd().setPlatform(platform);
            assertTrue(location.getCrowd().doActionOnPlatform());
        } else {
            assertFalse(location.getCrowd().doActionOnPlatform());
        }
    }

//    Тест, проверяющий действия народа в зависимости от наличия/отсутствия спикера на платформе
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void isCrowdScreaming(boolean containsSpeaker){
        Platform platform = new Platform();
        platform.setContainsCrowd(true);
        platform.setContainsSpeaker(containsSpeaker);
        if(containsSpeaker) location.getSpeaker().setPlatform(platform);
        location.getCrowd().setPlatform(platform);

        location.getCrowd().doActionOnPlatform();
        assertEquals(!containsSpeaker, location.getCrowd().isScreaming);
    }

//    Тест, проверяющий действия спикера в зависимости от наличия/отсутствия народа на платформе
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

    @Test
    public void isArthurFlyingAfterAction() {
        location.getArthur().notice(true);
        Assertions.assertTrue(location.getArthur().isFlying());
    }

    @ParameterizedTest
    @ValueSource(strings = {" не"})
    public void isCheckFlyingFalse(String input) {
        assertEquals(input, location.getArthur().checkFlyingState());
    }

    @ParameterizedTest
    @EmptySource
    public void isCheckFlyingTrue(String input) {
        location.getArthur().notice(true);
        assertEquals(input, location.getArthur().checkFlyingState());
    }

}

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

public class ArthurTest {

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
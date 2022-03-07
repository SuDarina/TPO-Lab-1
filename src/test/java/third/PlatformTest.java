package third;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PlatformTest {

    //   Данные для теста isArthurChasingTheWindow()/initPlatform
    //    {int floors, int windows, boolean expected}
    public static Collection<Object[]> dataBuilding() {
        return Arrays.asList(new Object[][] {
                {1, 1, false}, {2, 0, false},
                {3, 1, true}, {10, 10, true}
        });
    }

    Location location;
    @BeforeEach
    public void setUp() {
        location = new Location(true, true, true);
        location.initLocation();
    }

    //    Проверка инициализации платформы
    @ParameterizedTest
    @MethodSource("dataBuilding")
    public void isPlatformInit(int floors, int windows, boolean expected){
        location.getBuilding().setFloors(floors);
        location.getBuilding().setWindows(windows);
        assertEquals(expected, location.getBuilding().initPlatform());
    }

}
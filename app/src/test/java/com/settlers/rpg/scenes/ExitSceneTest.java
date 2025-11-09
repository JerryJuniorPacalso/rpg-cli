package com.settlers.rpg.scenes;

import com.settlers.rpg.utils.ConsolePrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mockStatic;

public class ExitSceneTest {

    @Test
    @DisplayName("render() should call ConsolePrinter.title with 'Exiting Game'")
    void testRenderCallsConsolePrinter() {
        try (MockedStatic<ConsolePrinter> mocked = mockStatic(ConsolePrinter.class)) {
            ExitScene scene = new ExitScene();
            scene.render();
            mocked.verify(() -> ConsolePrinter.title("Exiting Game"));
        }
    }

    @Test
    @DisplayName("handleInput should return same scene (ExitScene)")
    void testHandleInputReturnsSelf() {
        ExitScene scene = new ExitScene();
        Scene result = scene.handleInput("anything");
        assertSame(scene, result);
    }

    @Test
    @DisplayName("update should return same instance")
    void testUpdateReturnsSelf() {
        ExitScene scene = new ExitScene();
        Scene result = scene.update();
        assertSame(scene, result);
    }

    @Test
    @DisplayName("getName should return 'ExitScene'")
    void testGetName() {
        ExitScene scene = new ExitScene();
        assertEquals("ExitScene", scene.getName());
    }
}
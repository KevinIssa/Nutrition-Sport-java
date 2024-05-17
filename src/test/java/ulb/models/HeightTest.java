package ulb.models;

import org.junit.jupiter.api.Test;
import ulb.exceptions.BadHeightException;
import ulb.exceptions.ValueObjectException;

import static org.junit.jupiter.api.Assertions.*;

class HeightTest {

    @Test
    void checkValidityThrowsExceptionForHeightLessThanMin() {
        assertThrows(BadHeightException.class, () -> new Height(1 - 0.1f));
    }

    @Test
    void checkValidityThrowsExceptionForHeightGreaterThanMax() {
        assertThrows(BadHeightException.class, () -> new Height(300 + 0.1f));
    }

    @Test
    void checkValidityDoesNotThrowExceptionForValidHeight() {
        assertDoesNotThrow(() -> new Height((1 + 300) / 2));
    }

    @Test
    void toStringReturnsCorrectFormatForPositiveHeight() throws ValueObjectException {
        Height height = new Height(150.5f);
        System.out.println(height.toString());
        assertEquals("150,50 cm", height.toString());
    }

}
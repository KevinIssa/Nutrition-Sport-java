package ulb.models;

import org.junit.jupiter.api.Test;
import ulb.exceptions.BadWeightException;
import ulb.exceptions.ValueObjectException;

import static org.junit.jupiter.api.Assertions.*;

class WeightTest {
    @Test
    void checkValidityThrowsExceptionForWeightEqualToMin() {
        assertThrows(BadWeightException.class, () -> new Weight(1));
    }

    @Test
    void checkValidityDoesNotThrowExceptionForWeightGreaterThanMin() {
        assertDoesNotThrow(() -> new Weight(1 + 1));
    }

    @Test
    void checkValidityDoesNotThrowExceptionForWeightEqualToMax() {
        assertDoesNotThrow(() -> new Weight(500));
    }

    @Test
    void checkValidityThrowsExceptionForWeightGreaterThanMax() {
        assertThrows(BadWeightException.class, () -> new Weight(500 + 1));
    }

    @Test
    void toStringReturnsCorrectStringForPositiveWeight() throws ValueObjectException {
        Weight weight = new Weight(70f);
        assertEquals("70.0 kg", weight.toString());
    }

}
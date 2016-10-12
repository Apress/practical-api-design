package org.apidesign.samples;

import org.junit.Test;
import static org.junit.Assert.*;

public class CodeThatCanBeCalledOnlyByTestTest {

    public CodeThatCanBeCalledOnlyByTestTest() {
    }

    @Test
    public void setNumber() {
        CodeThatCanBeCalledOnlyByTest.setNumber(111);
        assertEquals(
            "Changing number is OK from tests", 
            111, 
            CodeThatCanBeCalledOnlyByTest.getNumber()
        );
    }

}
package com.bridgelabz;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateCensusAnalyserTest {
    @Test
    public void checkToEnsure_NumberOfRecordsMatches() throws CSVCensusException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser("StateCensus.csv");
        assertEquals(37, stateCensusAnalyser.readStateData());
    }

    @Test
    public void givenWrongFileName_ShouldThrowNoSuchFileException() {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser("StateCensus12.csv");
        try {
            int value = stateCensusAnalyser.readStateData();
            assertEquals(37, value);

        } catch (CSVCensusException e) {
            System.out.println(e.getMessage());
          assertEquals("Please enter proper file name", e.getMessage());
        }
    }

    @Test
    public void givenWrongFilePath_ShouldThrowRunTimeException() {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser("gradlew.bat");
            int checkNumberOfRecords = stateCensusAnalyser.readStateData();
            assertEquals(37, checkNumberOfRecords);

        } catch (CSVCensusException e) {
            e.printStackTrace();
           assertEquals("binding of file to failed", e.getMessage());
        }
    }

    @Test
    public void givenMethod_ifFoundIncorrectDelimiterPosition_ShouldReturnException() throws IOException, CSVCensusException {
        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser("StateCensus.csv");
            int value = stateCensusAnalyser.readStateData();
            assertEquals(37, value);
        } catch (CSVCensusException e) {
            System.out.println(e.getMessage());
            assertEquals("Exception due to incorrect delimiter position", e.getMessage());
        }
    }

    @Test
    public void givenMethod_ifFoundNoHeader_ShouldReturnException()
    {

        try {
            StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser("StateCensus.csv");
            int value = stateCensusAnalyser.readStateData();
            assertEquals(37, value);
        }
        catch (CSVCensusException e)
        {
            System.out.println(e.getMessage());
            assertEquals("Exception due to Header", e.getMessage());
        }

    }
}

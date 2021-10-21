package com.bridgelabz;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;


public class StateCensusAnalyser {
    private String STATE_CENSUS_FILE_PATH = "StateCensus.csv";

    public StateCensusAnalyser(String STATE_CENSUS_FILE_PATH) {
        this.STATE_CENSUS_FILE_PATH = STATE_CENSUS_FILE_PATH;
    }

    public int readStateData() throws CSVCensusException {
        int count = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(STATE_CENSUS_FILE_PATH))) {
            CsvToBean<CSVStateCensus> csvToBean = new CsvToBeanBuilder(reader).withIgnoreLeadingWhiteSpace(true).withType(CSVStateCensus.class).build();
            Iterator<CSVStateCensus> stateIterator = csvToBean.iterator();
            while (stateIterator.hasNext()) {
                CSVStateCensus csvStates = stateIterator.next();
                count++;
                if (csvStates.getSrNo() == 0 || csvStates.getStateName() == null || csvStates.getTIN() == null || csvStates.getStateCode() == null) {
                    throw new CSVCensusException("Exception due to Header", CSVCensusException.ExceptionType.NO_SUCH_HEADER);
                }
            }
        } catch (NoSuchFileException e) {
            if (STATE_CENSUS_FILE_PATH.contains(".csv"))
                throw new CSVCensusException("Please enter proper file name", CSVCensusException.ExceptionType.NO_SUCH_FILE);
            throw new CSVCensusException("Please enter proper file type", CSVCensusException.ExceptionType.NO_SUCH_FILE);
        } catch (RuntimeException e) {
            throw new CSVCensusException("Exception due to incorrect delimiter position", CSVCensusException.ExceptionType.NO_SUCH_FIELD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}

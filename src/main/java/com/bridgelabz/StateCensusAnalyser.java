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
    private String STATE_CENSUS_FILE_PATH;

    public StateCensusAnalyser(String STATE_CENSUS_FILE_PATH) {
        this.STATE_CENSUS_FILE_PATH = STATE_CENSUS_FILE_PATH;
    }


    public <E> int readStateData(Class<E> eClass) throws CSVCensusException {
        int count = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(STATE_CENSUS_FILE_PATH))) {
            CsvToBean<E> csvToBean = new CsvToBeanBuilder(reader)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withType(eClass)
                    .build();
            Iterator stateIterator = csvToBean.iterator();
            while (stateIterator.hasNext()) {
                E csv = (E) stateIterator.next();
                count++;
                if (csv instanceof CSVStateCensus) {
                    if ( ((CSVStateCensus) csv).getSrNo() == 0 || ((CSVStateCensus) csv).getStateName() == null || ((CSVStateCensus) csv).getTIN() == null || ((CSVStateCensus) csv).getStateCode() == null) {
                        throw new CSVCensusException("Exception due to Header or mismatch data", CSVCensusException.ExceptionType.NO_SUCH_HEADER);
                    }
                }
            }
        } catch (NoSuchFileException e) {
            if (STATE_CENSUS_FILE_PATH.contains(".csv")) {
                throw new CSVCensusException("Please enter proper file name", CSVCensusException.ExceptionType.NO_SUCH_FILE);
            }
        } catch (RuntimeException e) {
            throw new CSVCensusException("Exception due to incorrect delimiter position", CSVCensusException.ExceptionType.NO_SUCH_FIELD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}

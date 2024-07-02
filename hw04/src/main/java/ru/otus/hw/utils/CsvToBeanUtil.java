package ru.otus.hw.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;

public class CsvToBeanUtil {

    public static <T> CsvToBean<T> getCsvToBeanConverter(Reader reader, Class<T> beanType) {
        CsvToBeanBuilder<T> csvToBean = new CsvToBeanBuilder<>(reader);
        csvToBean.withSkipLines(1);
        csvToBean.withType(beanType);
        csvToBean.withSeparator(';');
        return csvToBean.build();
    }
}

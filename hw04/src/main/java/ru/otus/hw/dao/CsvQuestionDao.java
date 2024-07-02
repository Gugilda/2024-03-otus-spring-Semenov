package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.utils.CsvToBeanUtil;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/
        List<Question> result;
        try (InputStream is = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName()))) {
            Reader reader = new InputStreamReader(is);
            CsvToBean<QuestionDto> csvToBean = CsvToBeanUtil.getCsvToBeanConverter(reader, QuestionDto.class);
            List<QuestionDto> questionDtos = csvToBean.parse();
            result = questionDtos.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());
        } catch (IOException e) {
            throw new QuestionReadException("Couldn't load questions file", e);
        }
        return result;
    }
}

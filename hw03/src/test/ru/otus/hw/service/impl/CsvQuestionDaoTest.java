package ru.otus.hw.service.impl;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CsvQuestionDaoTest {

    public static final String TEST_PROPERTIES = "resources/testQuestions.csv";

    @InjectMocks
    CsvQuestionDao csvQuestionDao;

    @Mock
    TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    public void findAllTest() {
        when(fileNameProvider.getTestFileName()).thenReturn(TEST_PROPERTIES);
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
        List<Question> questions = csvQuestionDao.findAll();
        assertEquals("Question?", questions.get(0).text());
        assertEquals("trueAnswer", questions.get(0).answers().get(0).text());
        assertTrue(questions.get(0).answers().get(0).isCorrect());
        assertEquals("falseAnswer", questions.get(0).answers().get(1).text());
        assertFalse(questions.get(0).answers().get(1).isCorrect());

    }

}
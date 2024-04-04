package ru.otus.hw.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TestServiceImplTest {

    @InjectMocks
    TestServiceImpl testService;

    @Mock
    IOService ioService;

    @Mock
    QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void executeTest() {
        List<Question> questions = new ArrayList<>();
        String textQuestion1 = "Some text";
        Question question = new Question(textQuestion1, null);
        questions.add(question);

        when(questionDao.findAll()).thenReturn(questions);
        testService.executeTest();

        verify(ioService).printLine(textQuestion1);
    }
}
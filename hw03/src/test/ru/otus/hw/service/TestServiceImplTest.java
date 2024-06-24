package ru.otus.hw.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.otus.hw.service.TestServiceImpl.ERROR_MESSAGE_CODE;

class TestServiceImplTest {

    @InjectMocks
    TestServiceImpl subj;

    @Mock
    LocalizedIOService ioService;

    @Mock
    QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void executeTestForWithCorrectAnswer() {
        Student student = new Student("name", "surname");
        List<Question> questions = prepareQuestions();
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeLocalized(1, questions.get(0).answers().size(),
                ERROR_MESSAGE_CODE)).thenReturn(1);
        TestResult testResult = subj.executeTestFor(student);

        assertEquals(student, testResult.getStudent());
        assertEquals(questions, testResult.getAnsweredQuestions());
        assertEquals(1, testResult.getRightAnswersCount());
    }

    @Test
    void executeTestForWithInCorrectAnswer() {
        Student student = new Student("name", "surname");
        List<Question> questions = prepareQuestions();
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeLocalized(1, questions.get(0).answers().size(),
                ERROR_MESSAGE_CODE)).thenReturn(2);
        TestResult testResult = subj.executeTestFor(student);

        assertEquals(student, testResult.getStudent());
        assertEquals(questions, testResult.getAnsweredQuestions());
        assertEquals(0, testResult.getRightAnswersCount());
    }

    @Test
    void executeTestForWithException() {
        Student student = new Student("name", "surname");
        List<Question> questions = prepareQuestions();
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeLocalized(1, questions.get(0).answers().size(),
                ERROR_MESSAGE_CODE)).thenReturn(3);

        assertThrows(IndexOutOfBoundsException.class, () -> subj.executeTestFor(student));
    }

    private List<Question> prepareQuestions() {
        Answer correctAnswer = new Answer("correctAnswer", true);
        Answer incorrectAnswer = new Answer("incorrectAnswer", false);
        List<Answer> answers = Arrays.asList(correctAnswer, incorrectAnswer);
        Question question = new Question("question", answers);
        return List.of(question);
    }
}
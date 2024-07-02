package ru.otus.hw.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static ru.otus.hw.service.TestServiceImpl.ERROR_MESSAGE_CODE;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class TestServiceImplTest {

    @Autowired
    TestServiceImpl subj;

    @MockBean
    LocalizedIOService ioService;

    @MockBean
    QuestionDao questionDao;

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
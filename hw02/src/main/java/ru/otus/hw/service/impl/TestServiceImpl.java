package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        int questionCounter = 1;

        for (var question : questions) {
            ioService.printLine(questionCounter++ + ". " + question.text());
            int answerCounter = 1;
            for (var answer : question.answers()) {
                ioService.printLine("\t" + answerCounter++ + ". " + answer.text());
            }
            String studentAnswer = ioService.readString();
            try {
                int answerIndex = Integer.parseInt(studentAnswer);
                var isAnswerValid = question.answers().get(answerIndex).isCorrect();
                testResult.applyAnswer(question, isAnswerValid);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                testResult.applyAnswer(question, false);
            }
        }
        return testResult;
    }
}

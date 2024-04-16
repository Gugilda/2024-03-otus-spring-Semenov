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
        int counter = 1;

        for (var question : questions) {
            ioService.printLine(counter++ + ". " + question.text());
            String studentAnswer = ioService.readString();
            var isAnswerValid = question.answers().stream().filter(Answer::isCorrect)
                    .anyMatch(a -> a.text().equalsIgnoreCase(studentAnswer));
            testResult.applyAnswer(question, isAnswerValid);
        }

        return testResult;
    }
}

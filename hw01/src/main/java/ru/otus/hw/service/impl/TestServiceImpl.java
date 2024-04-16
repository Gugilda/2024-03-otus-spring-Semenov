package ru.otus.hw.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        List<Question> questionList = questionDao.findAll();
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов

        for (Question question : questionList) {
            ioService.printLine(question.text());
            if (question.answers() != null) {
                AtomicInteger count = new AtomicInteger(1);
                question.answers().forEach(a -> ioService.printLine("\t" + count.getAndIncrement() + ". " + a.text()));
            }
            ioService.printLine("");
        }
    }
}

package net.bot.crypto.application.common.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulingService {

    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    public SchedulingService(@Qualifier("taskScheduler") TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    /**
     * 작업 스케줄링을 Fixed Rate<i>(이전 작업이 완료되지 않아도 주기적으로 실행)</i>로 시작한다.
     *
     * @param task 스케줄링할 작업
     * @param duration 작업 주기
     */
    public void startScheduledTask(Runnable task, Duration duration) {
        if (scheduledFuture == null || scheduledFuture.isDone()) {
            scheduledFuture = taskScheduler.scheduleAtFixedRate(task, duration);
        }
    }
    public void stopScheduledTask() {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(true);
        }
    }
}

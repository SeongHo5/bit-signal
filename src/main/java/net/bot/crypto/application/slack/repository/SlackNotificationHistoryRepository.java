package net.bot.crypto.application.slack.repository;

import net.bot.crypto.application.domain.entity.SlackNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackNotificationHistoryRepository extends JpaRepository<SlackNotificationHistory, Long> {
}

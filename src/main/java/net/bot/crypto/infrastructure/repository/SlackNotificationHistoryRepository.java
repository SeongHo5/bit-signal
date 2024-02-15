package net.bot.crypto.infrastructure.repository;

import net.bot.crypto.domain.entity.SlackNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackNotificationHistoryRepository extends JpaRepository<SlackNotificationHistory, Long> {
}

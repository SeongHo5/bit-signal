package net.bot.crypto.application.slack.repository;


import net.bot.crypto.domain.entity.SlackCommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackCommandHistoryRepository extends JpaRepository<SlackCommandHistory, Long> {
}

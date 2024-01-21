package net.bot.crypto.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bot.crypto.domain.dto.SlashCommandRequest;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "slack_command_history")
public class SlackCommandHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Comment("요청 채널 ID")
    private String channelId;

    @Comment("요청 채널 이름")
    private String channelName;

    @Comment("요청 유저 ID")
    private String userId;

    @Comment("요청 유저 이름")
    private String userName;

    @Comment("요청 커맨드")
    private String command;

    @Comment("커맨드에 포함된 텍스트(인자값)")
    private String text;


    public static SlackCommandHistory of(SlashCommandRequest request) {
        return SlackCommandHistory.builder()
                                  .channelId(request.channelId())
                                  .channelName(request.channelName())
                                  .userId(request.userId())
                                  .userName(request.userName())
                                  .command(request.command())
                                  .text(request.text())
                                  .build();
    }

}

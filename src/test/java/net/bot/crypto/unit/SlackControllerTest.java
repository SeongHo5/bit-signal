package net.bot.crypto.unit;

import net.bot.crypto.domain.dto.request.RequestSlashCommand;
import net.bot.crypto.application.slack.controller.SlackController;
import net.bot.crypto.application.slack.service.SlackCommandDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SlackControllerTest {

    @Mock
    private SlackCommandDispatcher commandDispatcher;
    @InjectMocks
    private SlackController slackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideTextForSlashCommand")
    @DisplayName("Slash Command 처리 테스트")
    void shouldHandleSlashCommand(String command, String text, String expectedResponse) {
        // Given
        when(commandDispatcher.handleSlashCommandInternal(any(RequestSlashCommand.class)))
                .thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = slackController.handleSlashCommand("channelId", "channelName", "userId", "userName", command, text);

        // Then
        assertThat(response).isEqualTo(ResponseEntity.ok(expectedResponse));
    }

    private static Stream<Arguments> provideTextForSlashCommand() {
        String expectedResponse = "Command handled successfully";
        return Stream.of(
                Arguments.of("market-list", "", expectedResponse),
                Arguments.of("info", "KRW-BTC", expectedResponse),
                Arguments.of("info", null, expectedResponse),
                Arguments.of("alarm", "1234", expectedResponse),
                Arguments.of("alarm", "56%20", expectedResponse),
                Arguments.of("stop", null, expectedResponse)
        );
    }
}

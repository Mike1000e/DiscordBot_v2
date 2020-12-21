package org.jointheleague.features.examples.third_features;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class CatFactsApiTest {

    private final String testChannelName = "test";
    private final CatFactsApi catFactsApi = new CatFactsApi(testChannelName);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private MessageCreateEvent messageCreateEvent;

    @Mock
    private TextChannel textChannel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void itShouldNotPrintToSystemOut() {
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = catFactsApi.COMMAND;

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }

    @Test
    void itShouldHandleMessagesWithCommand() {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(catFactsApi.COMMAND, "test");
        when(messageCreateEvent.getMessageContent()).thenReturn(catFactsApi.COMMAND);
        when(messageCreateEvent.getChannel()).thenReturn((textChannel));

        //When
        catFactsApi.handle(messageCreateEvent);

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }

//    @Test
//    void itShouldHandleMessagesWithoutSecondaryCommand() {
//        //Given
//        HelpEmbed helpEmbed = new HelpEmbed(underTest.COMMAND, "test");
//        when(messageCreateEvent.getMessageContent()).thenReturn(underTest.COMMAND);
//        when(messageCreateEvent.getChannel()).thenReturn((textChannel));
//
//        //When
//        underTest.handle(messageCreateEvent);
//
//        //Then
//        verify(textChannel, times(1)).sendMessage(anyString());
//    }

    @Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(messageCreateEvent.getMessageContent()).thenReturn(command);

        //When
        catFactsApi.handle(messageCreateEvent);

        //Then
        verify(textChannel, never()).sendMessage();
    }

    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = catFactsApi.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = catFactsApi.getHelpEmbed().getTitle();
        String command = catFactsApi.COMMAND;

        //Then
        assertEquals(command, helpEmbedTitle);
    }

}
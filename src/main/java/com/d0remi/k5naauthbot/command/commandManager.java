package com.d0remi.k5naauthbot.command;

import com.d0remi.k5naauthbot.wikiSearch.gitbookSearch;
import com.d0remi.k5naauthbot.wikiSearch.searchResult;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class commandManager extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction (@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("say")){
            String message = event.getOption("message").getAsString();

            event.getChannel().sendMessage(message).queue();
            event.reply("Your message was went!").setEphemeral(true).queue();
        }
        else if (command.equals("wiki")){

            String query = event.getOption("query") != null ? event.getOption("query").getAsString() : "";
            System.out.println("query = " + query);
            if (query.isEmpty()) {
                event.reply("Error: Missing or invalid query parameter").queue();
                return;
            }
            String response = gitbookSearch.search(query);
            /*String title = searchResult.searchResult(title);
            String url = searchResult.getUrlStr();
            String description = searchResult.getDescription();*/

            EmbedBuilder message = new EmbedBuilder();

            message.setTitle("위키 검색 결과");
            message.setDescription(response);
            message.setAuthor("코나 길라잡이", "https://k5nawiki.gitbook.io/undefined");
            message.setColor(Color.GREEN);
            //event.replyEmbeds(message.build()).queue();




/*            for (searchResult result : response){
                String title = result.getTitle();
                String url = result.getUrlStr();
                String description = result.getDescription();

               EmbedBuilder message = new EmbedBuilder();

                message.setTitle(title);
                message.addField("링크", url, true);
                message.addField("설명", description, true)
                message.setColor(Color.GREEN);
                event.replyEmbeds(message.build()).queue();
            }*/

            String searchOp = event.getOption("setting").getAsString();
            System.out.println(searchOp);
            if (searchOp.equals("예"))
                event.replyEmbeds(message.build()).setEphemeral(true).queue();
            else
                event.replyEmbeds(message.build()).queue();
        }
    }



    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        //command: /say <message>
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot say", true);
        OptionData option2 = new OptionData(OptionType.STRING, "test", "Testing message", false);
        commandData.add(
                Commands.slash("say", "위키 관련 공지를 내보냅니다.")
                        .addOptions(option1, option2)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
        );
        OptionData searchOption = new OptionData(OptionType.STRING, "query", "검색할 키워드를 입력하세요", true);
        //OptionData showOption = new OptionData(OptionType.STRING, "setting", "답변을 본인에게만 보이게 하려면 yes를 입력하세요.", true);
        commandData.add(
                Commands.slash("wiki", "코나 위키에서 관련 내용을 검색합니다.")
                        .addOptions(searchOption)
                        .addOptions(
                                new OptionData(OptionType.STRING, "setting", "답변을 본인에게만 보이게 하려면 '예'를 입력하세요.", false)
                                        .addChoice("예", "예")
                                        .addChoice("X", "No")
                        )
                        //.addOptions(showOption)
        );
    }
}

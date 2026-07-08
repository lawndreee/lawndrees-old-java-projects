package org.valbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.io.IOException;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class valbot extends ListenerAdapter {
    private int index;
    private ArrayList<String> favevent = new ArrayList<>();
    private ArrayList<String> favteam = new ArrayList<>();
    private static vlrinfo eventdata = new vlrinfo();
    private static vlrinfo teamdata = new vlrinfo();
    public static void main(String[] args) throws IOException {
        eventdata.setup("events");
        teamdata.setup("teams");
        JDA bot = JDABuilder.createDefault("MTI1MTEzODI4ODcxOTM2NDE2Ng.GXRSdX.JC_z_yWxQDpJXL-BnnMyKSXlSCUCrxiS8FRMgU") // slash commands don't need any intent
                .addEventListeners(new valbot())
                .build();

        CommandListUpdateAction stats = bot.updateCommands();
        stats.addCommands(
                Commands.slash("currevents", "list of ongoing events")
        );
        stats.addCommands(
                Commands.slash("compevents", "list of recently completed events")
        );
        stats.addCommands(
                Commands.slash("upevents", "list of upcoming events")
        );stats.addCommands(
                Commands.slash("team", "search for a team")
                        .addOption(OptionType.STRING, "region", "region team is from", true)
                        .addOption(OptionType.STRING, "name", "Team to look up", false)
        );
        stats.addCommands(
                Commands.slash("test", "meows back or something")
        ).queue();
        stats.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
            if(event.getName().equals("currevents")){
                System.out.println("current events");
                curreventslist(event);
            }else if(event.getName().equals("upevents")){
                System.out.println("upcoming events");
                upeventslist(event);
            }else if(event.getName().equals("compevents")){
                System.out.println("completed events");
                compeventslist(event);
            }else if (event.getName().equals("test")){
            System.out.println("meow");
            meow(event);
        }
    }
    public void meow(SlashCommandInteractionEvent event){
        event.reply("meow").queue();
    }
public void upeventslist (SlashCommandInteractionEvent event){
    EmbedBuilder eventinfo = new EmbedBuilder();
    event.deferReply().queue();
    index = 0;
    eventinfo.setTitle("Upcoming events list");
    for (int i = 0; i < eventdata.eventnames("upcoming").size(); i++) {
        eventinfo.setDescription(eventinfo.getDescriptionBuilder() + "\n" + eventdata.eventnames("upcoming").get(i));
    }
    eventinfo.setFooter("click buttons to view more information about events shown");
    event.getHook().sendMessageEmbeds(eventinfo.build()).addActionRow(
            Button.secondary("uleft", Emoji.fromUnicode("U+25C0")),
            Button.secondary("uright", Emoji.fromUnicode("U+25B6"))
    ).queue();
}
    public void curreventslist (SlashCommandInteractionEvent event){
        EmbedBuilder eventinfo = new EmbedBuilder();
        event.deferReply().queue();
        index = 0;
        eventinfo.setTitle("Ongoing events list");
        for (int i = 0; i < eventdata.eventnames("ongoing").size(); i++) {
            eventinfo.setDescription(eventinfo.getDescriptionBuilder() + "\n" + eventdata.eventnames("ongoing").get(i));
        }
        eventinfo.setFooter("click buttons to view more information about events shown");
        event.getHook().sendMessageEmbeds(eventinfo.build()).addActionRow(
                Button.secondary("oleft", Emoji.fromUnicode("U+25C0")),
                //Button.secondary("efavourite", Emoji.fromUnicode("U+2B50")),
                Button.secondary("oright", Emoji.fromUnicode("U+25B6"))
        ).queue();
    }
    public void compeventslist (SlashCommandInteractionEvent event){
        EmbedBuilder eventinfo = new EmbedBuilder();
        event.deferReply().queue();
        index = 0;
        eventinfo.setTitle("Completed events list");
        for (int i = 0; i < eventdata.eventnames("completed").size(); i++) {
            eventinfo.setDescription(eventinfo.getDescriptionBuilder() + "\n" + eventdata.eventnames("completed").get(i));
        }
        eventinfo.setFooter("click buttons to view more information about events shown");
        event.getHook().sendMessageEmbeds(eventinfo.build()).addActionRow(
                Button.secondary("cleft", Emoji.fromUnicode("U+25C0")),
                //Button.secondary("efavourite", Emoji.fromUnicode("U+2B50")),
                Button.secondary("cright", Emoji.fromUnicode("U+25B6"))
        ).queue();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("uleft")){
            updatepage(event, index--, "upcoming");
        }
        else if (event.getComponentId().equals("uright")){
            updatepage(event, index++, "upcoming");
        }
       else if (event.getComponentId().equals("oleft")){
            updatepage(event, index--, "ongoing");
        }
        else if (event.getComponentId().equals("oright")){
            updatepage(event, index++, "ongoing");
        }
       else if (event.getComponentId().equals("cleft")){
            updatepage(event, index--, "completed");
        }
        else if (event.getComponentId().equals("cright")){
            updatepage(event, index++, "completed");
        }
    }
    public void updatepage(ButtonInteractionEvent event, int index, String type){
        EmbedBuilder expanded = new EmbedBuilder();
        if (index >= 0 && index < eventdata.eventnames(type).size()) {
            expanded.setTitle(eventdata.eventnames(type).get(index));
            expanded.setThumbnail("https:" + eventdata.eventicon(type).get(index));
            expanded.setDescription(eventdata.eventprize(type).get(index) + "\n" + eventdata.eventdates(type).get(index));
            event.editMessageEmbeds(expanded.build()).queue();
        }else{
            expanded.setTitle("no more entries");
            event.editMessageEmbeds(expanded.build()).queue();
        }
    }
}

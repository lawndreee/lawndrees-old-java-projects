package arkbot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

import javax.print.DocFlavor;
import java.awt.Color;


public class Arkbot extends ListenerAdapter {
    public static void main(String[] args) {
        //bot builder
        JDA bot = JDABuilder.createDefault("MTE2MTg5MDk5MzE1MTQxODQ0OQ.G6FE_K._qRlN9u9h4ijXmFkOLqgfZr_1la7MErtafAWNI") // slash commands don't need any intents
                .addEventListeners(new Arkbot())
                .build();
        //all slash commands for the bot
                CommandListUpdateAction comms = bot.updateCommands();

        comms.addCommands(
                Commands.slash("quote", "Sends the last words of a dead character in Arknights")
                        .addOption(OptionType.STRING, "name", "Character to quote", true)

        );
        comms.addCommands(
                Commands.slash("profile", "Sends information about dead character in Arknights")
                        .addOption(OptionType.STRING, "name", "Character to send information about", true)
        );

        comms.addCommands(
                Commands.slash("meow", "bot will meow back")
        );

        comms.addCommands(
                Commands.slash("vexfunds", "Total amount of money Mr. Jones has used for Vex approx so far")
        );

        comms.addCommands(
                Commands.slash("currwish", "Wishing simulator for current and upcoming Arknights banners (EN ONLY)")
                        .addOption(OptionType.STRING, "banner", "The name of the banner you want to simulate wish on", true)
        ).queue();
        //loading commands into the bot
        comms.queue();
    }

    //handles all incoming slash commands and directs them to the designated functions
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("quote")) {
            quote(event, event.getOption("name").getAsString());
        } else if (event.getName().equals("meow")) {
            meow(event);
        } else if (event.getName().equals("profile")) {
            profile(event, event.getOption("name").getAsString());
        } else if (event.getName().equals("vexfunds")) {
            vexfunds(event);
        } else if (event.getName().equals("currwish")) {
            currbanners(event, Objects.requireNonNull(event.getOption("banner")).getAsString());
        } else {
            event.reply("Not a command bro");
        }
    }

    //function that is called when /quote name is used
    public void quote(SlashCommandInteractionEvent event, String name) {
        /* Template for quotes
        if (name.equals("NAME")){
            event.reply("**stage here**\n“ ”").queue();
        }
         */
        //gets user's chosen character from name option, sends designated quote
        switch (name) {
            case "Ace":
                event.reply("**Main story 1-12 After: Pyrrhic Victory**\n“If you can live even a single minute longer, it will be worth it. I'll be right back. Take care of yourself.”").queue();
                break;
            case "Skullshatterer":
                event.reply("**Main story 3-4 After: Cracked**\n“It hurts...Even though you have this kind of power… You still... in cold blood... watch your fellow Infected…I can't... forgive… I can't forgive those like you…”").queue();
                break;
            case "Misha":
                event.reply("**Main story 3-8 Before: Dusked**\n“A-...Amiya...? Amiya... I guess this was our fate all along.”").queue();
                break;
            case "Scout":
                event.reply("**Darknights Memoir DM-7 Before: Cracked**\n“We Sarkaz... should not allow others to continue to use us. You? So... you're alive...Heh... Sounds like... she's the captain now… You... always...You... should not stay here…” ").queue();
                break;
            case "Kreide":
                event.reply("**Lingering Echoes LE-8 After: Schicksals**\n“If you return to The Afterglow someday, you'll remember that you once had a good friend, with whom you've laughed and argued here… And at the last, put on a duet to remember. Isn’t this what you wanted?”").queue();
                break;
            default:
                event.reply("This character is not included so far/does not exist!").queue();
        }
    }

    //function that is called when /meow is used
    public void meow(SlashCommandInteractionEvent event) {
        event.reply("fuck you").queue();
    }

    //function called when /vexfund is used
    public void vexfunds(SlashCommandInteractionEvent event) {
        double funds = (double) Math.round( (250.47 + 895.81 + 332.43 + 81.79 +  15.79 + 469.59)*100)/100;
        event.reply("Mr. Jones has spent $" + funds + " so far!").queue();
    }

    //function that is called when /profile name is used
    public void profile(SlashCommandInteractionEvent event, String name) {
        /* Template for profiles
         else if (name.equals("NAME")){
            event.deferReply().queue();
            em.setTitle("NAME", "https://arknights.fandom.com/wiki/NAME?so=search");
            em.setDescription("");
            em.setThumbnail("");
            em.setColor(Color.COLOR);
            em.addField("Appeared in", "", true);
            em.addField("Died in", "", true);
            em.addField("Killed by", "", true);
            event.getHook().sendMessageEmbeds(em.build()).queue();
        }
         */
        //sends an embed based on chosen character from name option
        EmbedBuilder em = new EmbedBuilder();
        if (name.equals("Ace")) {
            event.deferReply().queue();
            em.setTitle("Ace", "https://arknights.fandom.com/wiki/Ace?so=search");
            em.setDescription("A former member from Babel and one of Rhodes Island's elite combatants alongside Blaze, Rosmontis, and Scout, with whom he shared a strong camaraderie. He is also Blaze's senior when she first joined R.I. and helped her in combat training together with Mechanist.– From the Arknights Wiki Fandom page\nDied alongside his squad consisting of 13 members while trying to protect Amiya and the Doctor.");
            em.setThumbnail("https://static.wikia.nocookie.net/mrfz/images/7/74/Ace_icon.png/revision/latest/scale-to-width-down/60?cb=20220518023117");
            em.setColor(Color.cyan);
            em.addField("Appeared in", "Prologue, Episode 1", true);
            em.addField("Died in", "Episode 1", true);
            em.addField("Killed by", "Talulah", true);
            event.getHook().sendMessageEmbeds(em.build()).queue();
        } else if (name.equals("Scout")) {
            event.deferReply().queue();
            em.setTitle("Scout", "https://arknights.fandom.com/wiki/Scout?so=search");
            em.setDescription("He is one of those who fought for Theresa during the Kazdel civil war as part of Babel. He was also known as an expert sharpshooter, hence his codename. During the Chernobog incident, he led a squad of twelve operators to defeat the leader of a Sarkaz mercenary. He managed to escape with the annihilation of his entire squad before his ultimate death. – From the Arknights Wiki Fandom Page");
            em.setThumbnail("https://static.wikia.nocookie.net/mrfz/images/5/5e/Scout_icon.png/revision/latest/scale-to-width-down/60?cb=20220326152758");
            em.setColor(Color.blue);
            em.addField("Appeared in", "Prologue, Darknights Memoir", true);
            em.addField("Died in", "Darknights Memoir(Death was mentioned in Main story)", true);
            em.addField("Killed by", "Hoederer", true);
            event.getHook().sendMessageEmbeds(em.build()).queue();
        } else if (name.equals("Misha")) {
            event.deferReply().queue();
            em.setTitle("Misha", "https://arknights.fandom.com/wiki/Misha?so=search");
            em.setDescription("");
            em.setThumbnail("https://static.wikia.nocookie.net/mrfz/images/6/6d/Misha_icon.png/revision/latest/scale-to-width-down/60?cb=20220123015321");
            em.setColor(Color.lightGray);
            em.addField("Appeared in", "Episode 2, Episode 3", true);
            em.addField("Died in", "Episode 3", true);
            em.addField("Killed by", "Amiya", true);
            event.getHook().sendMessageEmbeds(em.build()).queue();
        } else if (name.equals("Skullshatterer")) {
            event.deferReply().queue();
            em.setTitle("Skullshatterer", "https://arknights.fandom.com/wiki/Skullshatterer?so=search");
            em.setDescription("The son of Sergei, an Ursus scientist and an accomplice of Kal'tsit and Ilia (Crownslayer's father), and the younger brother of Misha. At some point during his childhood, Alex and Misha were held hostage by the Ursus government to force Sergei to reveal Ilia's involvement in the research of the Sarcophagus in Chernobog. After the incident, Sergei disappeared and their mother was the only one who could take care of both Alex and Misha. – From the Arknights Wiki Fandom page");
            em.setThumbnail("https://static.wikia.nocookie.net/mrfz/images/b/b8/Skullshatterer_icon.png/revision/latest/scale-to-width-down/60?cb=20220123015719");
            em.setColor(Color.orange);
            em.addField("Appeared in", "Episode 2, Episode 3", true);
            em.addField("Died in", "Episode 3", true);
            em.addField("Killed by", "Amiya", true);
            event.getHook().sendMessageEmbeds(em.build()).queue();
        } else if (name.equals("Kreide")) {
            event.deferReply().queue();
            em.setTitle("Kreide", "https://arknights.fandom.com/wiki/Kreide?so=search");
            em.setDescription("One of the Witch King's distant descendants who, like Ebenholz, was kidnapped by the Witch King's Remnants as a child to be used as a subject in their cruel experiments. The Remnants forcefully implanted a fragment of the \"Voice of Terra\", a musical Arts that possesses the Witch King's consciousness, into his body in hope of \"resurrecting\" their majesty. – From the Arknights Wiki Fandom page\n");
            em.setThumbnail("https://static.wikia.nocookie.net/mrfz/images/a/a6/Kreide_icon.png/revision/latest/scale-to-width-down/60?cb=20220612104401");
            em.setColor(Color.white);
            em.addField("Appeared in", "Lingering Echoes", true);
            em.addField("Died in", "Lingering Echoes", true);
            em.addField("Killed by", "Ebenholz", true);
            event.getHook().sendMessageEmbeds(em.build()).queue();
        }
    }

    String choi;

    //loads an embed of all current banners (im lazy ill probably forget to change this interface lol
    public void currbanners(SlashCommandInteractionEvent event, String banner) {
        EmbedBuilder ban = new EmbedBuilder();
        event.deferReply().queue();
        if (banner.equals("The Front That Was")) {
            choi = "front";
            ban.setTitle("The Front That Was");
            ban.setImage("https://gamepress.gg/arknights/sites/arknights/files/2023-03/Episode12Preview_0.jpeg");
            event.getHook().sendMessageEmbeds(ban.build()).addActionRow(
                            Button.secondary("c1", "Headhunt x1"),
                            Button.secondary("c10", "Headhunt x10"))
                    .queue();
        } else if (banner.equals("standard")) {
            choi = "stan";
            event.deferReply().queue();
            ban.setTitle("Standard Pool 99");
            ban.setImage("https://arknights.wiki.gg/images/c/cb/EN_Standard_Pool_99_banner.png");
            event.getHook().sendMessageEmbeds(ban.build()).addActionRow(
                            Button.secondary("c1", "Headhunt x1"),
                            Button.secondary("c10", "Headhunt x10"))
                    .queue();
        }
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        event.deferReply().queue();
        if (event.getComponentId().equals("c1")) {
            event.getHook().sendMessage("You got: " + wish()).queue();
        } else if (event.getComponentId().equals("c10")) {
            event.getHook().sendMessage("You got: " + wish() + ", " + wish() + ", " + wish() + ", " + wish() + ", " + wish() + ", " + wish() + ", " + wish() + ", " + wish() + ", " + wish() + ", " + wish()).queue();
        }
    }

    /*public void upbanners(SlashCommandInteractionEvent event, String time) {
        EmbedBuilder ban = new EmbedBuilder();
        if (time.equals("As In My Adumbration")) {
        choi = "ines";
            event.deferReply().queue();
            ban.setTitle("As In My Adumbration");
            ban.setImage("https://gamepress.gg/arknights/sites/arknights/files/2023-04/Ep12AllQuietUnderTheThunderBanner.jpeg");
            event.getHook().sendMessageEmbeds(ban.build()).addActionRow(
                            Button.secondary("single", Emoji.fromUnicode("1️⃣")),
                            Button.secondary("ten", Emoji.fromUnicode("🔟")))
                    .queue();
        } else if (time.equals("Here I Stand")) {
        choi = "anni";
            event.deferReply().queue();
            ban.setTitle("Here I Stand");
            ban.setImage("https://gamepress.gg/arknights/sites/arknights/files/2023-04/LoneTrailBanner_0.jpeg");
            event.getHook().sendMessageEmbeds(ban.build()).addActionRow(
                            Button.secondary("single", Emoji.fromUnicode("1️⃣")),
                            Button.secondary("ten", Emoji.fromUnicode("🔟")))
                    .queue();
        } else if (time.equals("Joint Op 11")){
        choi = "j11";
            event.deferReply().queue();
            ban.setTitle("Joint Operation #11");
            ban.setImage("https://static.wikia.nocookie.net/mrfz/images/0/06/CN_Joint_Operation_11_banner.png/revision/latest?cb=20230818090642");
            event.getHook().sendMessageEmbeds(ban.build()).addActionRow(
                            Button.secondary("single", Emoji.fromUnicode("1️⃣")),
                            Button.secondary("ten", Emoji.fromUnicode("🔟")))
                    .queue();
        }
    }*/

    /*
    2% for 6*
    8% for 5*
    50% for 4*
    40% for 3*
     */

    Integer[] rates = {6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    int pity = 0;

    public String wish() {
        pity++;
        String charac;
        charac = op(rarity());
        System.out.println(pity);
        return charac;
    }

    //generates a rarity including weighted probability, when pity goes over 50 weighted probability gets changed
    public int rarity() {
        //while the pity is not 51, the rates will not need to be changed so just randomly generates a rarity
        if (pity <= 50) {
            return rates[(int) (Math.random() * 99)];
        }
        //2% change counter
        int fini = 2;
        //while the 2% change has not occurred yet
        while (fini != 0) {
            //generates random number locations from rarity array to possibly change
            int p1 = (int) (Math.random() * 97) + 2;
            //checks to see if it's not 6* rarity ig? and then changes them to be so
            if (rates[p1] != 6) {
                fini--;
                rates[p1] = 6;
            }
        }
        //after changing the rates, will randomly select a rarity
        return rates[(int) (Math.random() * 99)];
    }

    String[] fr6 = {"Horn", "Saileach", "Stainless"};
    String[] fr5up = {"Mulberry", "Rockrock", "Paprika"};
    String[] fr5 = {"Ptilopsis", "Zima", "Texas", "Franka", "Lappland", "Specter", "Blue Poison", "Platinum", "Meteorite", "Skyfire", "Mayer", "Silence", "Warfarin", "Nearl", "Projekt Red", "Liskarm", "Croissant", "Provence", "Firewatch", "Cliffheart", "Pramanix", "Istina", "Sora", "Manticore", "FEater", "Nightmare", "Swire", "Glaucus", "Astesia", "Executor", "Waai Fu", "Reed", "Broca", "GreyThroat", "Hung", "Leizi", "Sesa", "Shamare", "Elysium", "Asbestos", "Tsukinogi", "Leonhardt", "Ayerscarpe", "Beeswax", "Chiave", "Andreana", "Flint", "April", "Aosta", "Whisperain", "Kafka", "Iris", "Mr. Nothing", "Toddifons", "Akafuyu", "Kirara", "La Pluma", "Ashlock", "Corrserum", "Aurora", "Blacknight", "Quercus", "Kazemaru", "Windflit", "Hibiscus the Purifier", "Greyy the Lightningbearer", "Cantabile", "Proviso", "Lunacub", "Harmonie", "Firewhistle", "Wind Chimes"};
    String[] fr4up = {"Roberta", "Chestnut", "Totter"};
    String[] fr4 = {"Haze", "Gitano", "Jessica", "Meteor", "Shirayuki", "Scavenger", "Vigna", "Dobermann", "Matoimaru", "Frostleaf", "Mousse", "Gravel", "Rope", "Myrrh", "Perfumer", "Matterhorn", "Cuora", "Gummy", "Deepcolor", "Earthspirit", "Shaw", "Beehunter", "Greyy", "Sussurro", "Myrtle", "Vermeil", "May", "Ambriel", "Utage", "Cutter", "Podenco", "Click", "Jaye", "Aciddrop", "Arene", "Bubble", "Jackie", "Pinecone", "Beanstalk", "Indigo"};
    String[] nst6 = {"Weedy", "Rosa", "Suzuran", "Thorns", "Eunectes", "Surtr", "Blemishine", "Mudrock", "Mountain", "Archetto", "Passenger", "Kal'tsit", "Carnelian", "Pallas", "Saileach", "Fartooth", "Flametail", "Gnosis", "Lee", "Goldenglow", "Fiammetta", "Horn", "Irene", "Ebenholz", "Dorothy", "Pozëmka", "Mlynar", "Stainless", "Penance", "Reed the Flame Shadow", "Lin", "Qiubai"};
    String[] nst6up = {"Mizuki", "Saga"};
    String[] nst5up = {"Whisperain", "Kafka", "Greyy the Lightningbearer"};
    String[] nst5 = {"Mulberry", "Rockrock", "Paprika", "Elysium", "Asbestos", "Tsukinogi", "Leonhardt", "Ayerscarpe", "Beeswax", "Chiave", "Andreana", "Flint", "April", "Aosta", "Iris", "Mr. Nothing", "Toddifons", "Akafuyu", "Kirara", "La Pluma", "Ashlock", "Corrserum", "Aurora", "Blacknight", "Quercus", "Kazemaru", "Windflit", "Hibiscus the Purifier", "Cantabile", "Proviso", "Lunacub", "Harmonie", "Firewhistle", "Wind Chimes"};
    String[] st4 = {"Haze", "Gitano", "Jessica", "Meteor", "Shirayuki", "Scavenger", "Vigna", "Dobermann", "Matoimaru", "Frostleaf", "Mousse", "Gravel", "Rope", "Myrrh", "Perfumer", "Matterhorn", "Cuora", "Gummy", "Deepcolor", "Earthspirit", "Shaw", "Beehunter", "Greyy", "Sussurro", "Myrtle", "Vermeil", "May", "Ambriel", "Utage", "Cutter", "Podenco", "Click", "Jaye", "Aciddrop", "Arene", "Bubble", "Jackie", "Pinecone", "Beanstalk", "Indigo", "Roberta", "Chestnut", "Totter"};

    String[] op3 = {"Fang", "Vanilla", "Plume", "Melantha", "Cardigan", "Beagle", "Kroos", "Lava", "Hibiscus", "Ansel", "Steward", "Orchid", "Catapult", "Midnight", "Spot", "Popukar"};

    //based on the rarity gotten, and currently selected banner will generate an operator from banner list
    public String op(int rarity) {
        //if the rarity was 6*, the rates array might have messed up values so need to go back to readjust them + pity
        if (rarity == 6) {
            cleanup();
        }
        //generates operator based on rarity including their rateups from the front that was banner
        if (choi.equals("front")) {
            //all 6* will be the 3 appearing, no rateups
            if (rarity == 6) {
                return fr6[(int) (Math.random() * fr6.length)] + " 6★";
                //rate up 5*s have a 60% chance of appearing aka 2/3, from 1-3 if the value is divisible by 2(1/3 chance) op will not be rate up
            } else if (rarity == 5) {
                if ((int) (Math.random() * 3) + 1 % 2 != 1) {
                    return fr5[(int) (Math.random() * fr5.length)] + " 5★";
                } else return fr5up[(int) (Math.random() * fr5up.length)] + " 5★ rate up";
                //rate up 4s have a 45% of appearing (9/20), generates number from 0-20 and if it's from 9-20 then no rate up
            } else if (rarity == 4) {
                if ((int) (Math.random() * 20) > 8) {
                    return fr4[(int) (Math.random() * fr4.length)] + " 4★";
                } else {
                    return fr4up[(int) (Math.random() * fr4up.length)] + " 4★ rate up";
                }
            }
        } else if (choi.equals("stan")) {
            if (rarity == 6) {
                if ((int) (Math.random() * (3 - 1) + 1) != 2) {
                    return nst6up[(int) (Math.random() * nst6up.length)] + " 6★ rate up";
                } else return nst6[(int) (Math.random() * nst6.length)] + " 6★";
            } else if (rarity == 5) {
                if ((int) (Math.random() * (3 - 1) + 1) != 2) {
                    return nst5up[(int) (Math.random() * nst5up.length)] + " 5★ rate up";
                } else return nst5[(int) (Math.random() * nst5.length)] + " 5★";
            } else if (rarity == 4) {
                return st4[(int) (Math.random() * st4.length)] + " 4★";
            }
        }
        //3*s dont have rate ups
        return op3[(int) (Math.random() * op3.length)] + " 3★";
    }

    public void cleanup() {
        if (pity > 50) {
            for (int i = 2; i <= 9; i++) {
                if (rates[i] == 6) {
                    rates[i] = 5;
                }
            }
            for (int i = 10; i <= 59; i++) {
                if (rates[i] == 6) {
                    rates[i] = 4;
                }
            }
            for (int i = 60; i <= 99; i++) {
                if (rates[i] == 6) {
                    rates[i] = 3;
                }
            }
            System.out.println("Cleaned up!");
        }
        pity = 0;
    }
}



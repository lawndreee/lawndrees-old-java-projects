package org.valbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Year;
import java.util.List;

public class vlrinfo{
    private static Document data;
    private static Elements upcoming;
    private static Elements ongoing;
    private static Elements completed;

    public static void main(String[] args) throws IOException {
        //when called add team name to end
        data = Jsoup.connect("https://www.vlr.gg/vct-" + Year.now().getValue()).get();
        upcoming = data.select(".wf-card.mod-flex.event-item:has(span.event-item-desc-item-status.mod-upcoming)[href]");
        System.out.println(upcoming.select(".event-item-desc-item.mod-prize").eachText());
    }

    public void setup(String type) throws IOException {
        if (type.equals("events")) {
            data = Jsoup.connect("https://www.vlr.gg/vct-" + Year.now().getValue()).get();
            upcoming = data.select(".wf-card.mod-flex.event-item:has(span.event-item-desc-item-status.mod-upcoming)[href]");
            ongoing = data.select(".wf-card.mod-flex.event-item:has(span.event-item-desc-item-status.mod-ongoing)");
            completed = data.select(".wf-card.mod-flex.event-item:has(span.event-item-desc-item-status.mod-completed)");
        }else{
            data = Jsoup.connect("https://liquipedia.net/valorant/").get();
        }
    }
    public String eventurls(String event, int index){
        if (event.equals("upcoming")){
            return upcoming.eachAttr("href").get(index);
        }else if(event.equals("ongoing")){
            return ongoing.eachAttr("href").get(index);
        }else if(event.equals("completed")){
            return completed.eachAttr("href").get(index);
        }else return null;
    }
    public List<String> eventnames(String event){
        if (event.equals("upcoming")){
            return upcoming.select("div.event-item-title").eachText();
        }else if(event.equals("ongoing")){
            return ongoing.select("div.event-item-title").eachText();
        }else if(event.equals("completed")){
            return completed.select("div.event-item-title").eachText();
        }else return null;
    }
    public List<String> eventicon(String event) {
        if (event.equals("upcoming")){
            return upcoming.select("img[src]").eachAttr("src");
        }else if(event.equals("ongoing")){
            return ongoing.select("img[src]").eachAttr("src");
        }else if(event.equals("completed")){
            return completed.select("img[src]").eachAttr("src");
        }else return null;
    }
    public List<String> eventprize(String event) {
        if (event.equals("upcoming")){
            return upcoming.select(".event-item-desc-item.mod-prize").eachText();
        }else if(event.equals("ongoing")){
            return ongoing.select(".event-item-desc-item.mod-prize").eachText();
        }else if(event.equals("completed")){
            return completed.select(".event-item-desc-item.mod-prize").eachText();
        }else return null;
    }
    public List<String> eventdates(String event) {
        if (event.equals("upcoming")){
            return upcoming.select(".event-item-desc-item.mod-dates").eachText();
        }else if(event.equals("ongoing")){
            return ongoing.select(".event-item-desc-item.mod-dates").eachText();
        }else if(event.equals("completed")){
            return completed.select(".event-item-desc-item.mod-dates").eachText();
        }else return null;
    }
    public List<String> partTeams(String event, int index){
            return eventurls(event, index).select(".wf-module-item event-team-name");
    }
}

package com.olympics;

import java.util.ArrayList;

public class Event extends FileManager{
    private String EventID,sport,discipline,category,venue,date,startTime,duration,busTravelTime;
    private String[] dateArr;
    private int photo_icon,photo_title;

    public Event(){}
    public Event(int index){
        super();
        this.EventID = super.getEvents().get(index)[0];
        this.sport = super.getEvents().get(index)[1];
        this.discipline = super.getEvents().get(index)[2];
        this.category = super.getEvents().get(index)[3];
        this.venue = super.getEvents().get(index)[4];
        this.date = super.getEvents().get(index)[5];
        this.startTime = super.getEvents().get(index)[6];
        this.duration = super.getEvents().get(index)[7];
        this.busTravelTime = super.getEvents().get(index)[8];
        this.dateArr = date.split("\\.");
    }

    public int getEventID() {
        return Integer.parseInt(EventID);
    }
    public int getEventIndex(){return getEventID()-1;}

    public String getSport() {
        return sport;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getCategory() {
        return category;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getDurationHours() {
        int durationHours;
        if (duration.contains(".")){
            durationHours = Integer.parseInt(duration.substring(0,duration.indexOf(".")));
        }
        else {durationHours = Integer.parseInt(duration);}

        return durationHours;
    }

    public String getBusTravelTime() {return busTravelTime; }

    public int getDurationMin(){
        int durationMin;
        if(duration.contains(".")){
            durationMin = Integer.parseInt(duration.substring(duration.indexOf(".")+1));
        }
        else{durationMin = 0;}
        return durationMin;
    }

    public int getPhoto_icon() {
        if (sport.equals("Ceremony")){this.photo_icon = R.drawable.ceremony_icon;}
        else if (sport.equals("Athletics")){this.photo_icon = R.drawable.athletics_icon;}
        else if (sport.equals("Swimming")){this.photo_icon = R.drawable.swimming_icon;}
        else if (sport.equals("Diving")){this.photo_icon = R.drawable.diving_icon;}
        else if (sport.equals("Weightlifting")){this.photo_icon = R.drawable.weightlifting_icon;}
        return photo_icon;
    }

    public int getPhoto_title() {
        if (sport.equals("Ceremony")){this.photo_title = R.drawable.ceremony_title;}
        else if (sport.equals("Athletics")){this.photo_title = R.drawable.athletics_title;}
        else if (sport.equals("Swimming")){this.photo_title = R.drawable.swimming_title;}
        else if (sport.equals("Diving")){this.photo_title = R.drawable.diving_title;}
        else if (sport.equals("Weightlifting")){this.photo_title = R.drawable.weightlifting_title;}
        return photo_title;
    }

    public String getDay(){
        return Integer.parseInt(dateArr[0])+"";
    }

    public String getMonth(){
        return Integer.parseInt(dateArr[1])+"";
    }

    public String getYear(){
        return Integer.parseInt(dateArr[2])+"";
    }

    public ArrayList<Bus> getBusToEvent(){
        ArrayList<Bus> busToEventIndex = new ArrayList<>();
        for (String[] a: super.getBuses()) {
            //depart time xx.xx
            String depart = a[3];
            //depart hour xx hour
            int depart_hour = Integer.parseInt(depart.substring(0,depart.indexOf("."))) ;
            //depart min xx min
            int depart_min = Integer.parseInt(depart.substring(depart.indexOf(".")+1)) ;
            //plus travel time xx min
            depart_min+= Integer.parseInt(getBusTravelTime());
            //check min to hour
            if(depart_min>=60){
                depart_hour++;
                depart_min-=60;
            }
            //set to arrive hour xx
            int arrive_hour = depart_hour;
            //set to arrive min xx
            int arrive_min = depart_min;
            //event start hour xx
            int start_hour = Integer.parseInt(getStartTime().substring(0,getStartTime().indexOf(".")));
            //event start min xx
            int start_min = Integer.parseInt(getStartTime().substring(getStartTime().indexOf(".")+1));

            //arrive before start in 2 hours
            int before_hour = start_hour-2;
            Boolean beforeCheck = false;
            //check arrive time
            if(arrive_hour>=before_hour){
                beforeCheck = true;
            }

            //arrive after start 1 hour
            int after_hour = start_hour+1;
            int after_min = start_min;
            Boolean afterCheck = false;

            //check arrive time
            if (arrive_hour<=after_hour)//
                {
                if (arrive_hour==after_hour && arrive_min > after_min){
                    afterCheck = false;
                }
                else{afterCheck = true;}
            }

            //return bus index list
            if((a[2].equals(this.venue)) && beforeCheck && afterCheck){
                busToEventIndex.add(new Bus(Integer.parseInt(a[0])-1));
            }
        }
        return busToEventIndex;
    }

    @Override
    public String toString(){
        return "Event ID : "+getEventID()+"\n"+"Sport : "+getSport()+"\n"+"Discipline : "+getDiscipline()+"\n"+"Category : "+getCategory()+"\n"+"Venue : "+getVenue()+"\n"+"Date : "+getDate()+"\n"+"Start time : "+getStartTime()+"\n"+"Duration : "+getDurationHours()+" hours "+getDurationMin()+" minutes"+"\n"+"Bus travel time : "+getBusTravelTime();
    }
}

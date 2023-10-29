package com.olympics;
public class Bus extends FileManager {
    private String busID,busType,destination,depart,duration,rows,cols,cost;
    private int seatTotal,seatLeft;
    private int[] seatStatus;

    public Bus(int index){
        super();
        this.busID = super.getBuses().get(index)[0];
        this.busType = super.getBuses().get(index)[1];
        this.destination= super.getBuses().get(index)[2];
        this.depart= super.getBuses().get(index)[3];
        this.duration= super.getBuses().get(index)[4];
        this.rows= super.getBuses().get(index)[5];
        this.cols= super.getBuses().get(index)[6];
        this.cost= super.getBuses().get(index)[7];
        this.seatTotal = getRows()*getCols();
        this.seatLeft = seatTotal;
        this.seatStatus = new int[getRows()*getCols()];
        //0 = notBook, 1 = booking, 2 = booked
        for (int i = 0; i <seatStatus.length ; i++) {
            seatStatus[i] = 0;
        }
    }

    public int getBusID() {
        return Integer.parseInt(busID);
    }

    public String getBusType() {
        return busType;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepart() {
        return depart;
    }

    public String getArrive(){
        //depart hour xx hour
        int depart_hour = Integer.parseInt(depart.substring(0,depart.indexOf("."))) ;
        //depart min xx min
        int depart_min = Integer.parseInt(depart.substring(depart.indexOf(".")+1)) ;
        //plus travel time xx min
        depart_min+= getDuration();
        //check min to hour
        if(depart_min>=60){
            depart_hour++;
            depart_min-=60;
        }
        //set to arrive hour xx
        int arrive_hour = depart_hour;
        //set to arrive min xx
        int arrive_min = depart_min;

        String arriveTime;
        if (arrive_min == 0){
            arriveTime = arrive_hour+"."+arrive_min+"0";
        }
        else if (arrive_hour<10){
            arriveTime = "0"+arrive_hour+"."+arrive_min;
        }
        else {arriveTime = arrive_hour+"."+arrive_min;}
        return arriveTime;
    }

    public int getDuration() {
        return Integer.parseInt(duration);
    }

    public int getRows() {
        return Integer.parseInt(rows);
    }

    public int getCols() {
        return Integer.parseInt(cols);
    }

    public int getCost() {
        this.cost = this.cost.substring(this.cost.indexOf("B")+1);
        return Integer.parseInt(cost);
    }

    public int getSeatTotal(){
        return seatTotal;
    }

    public int getSeatLeft(){return seatLeft;}

    public int getBusIndex(){
        return this.getBusID()-1;
    }

    public void bookSeat(){ this.seatLeft --; }

    public void cancelBook(){
        this.seatLeft++;}

    public int[] getSeatStatus(){
        return seatStatus;
    }

    public void setSeatStatus(int seatNO,int status){
        this.seatStatus[seatNO] = status;
    }

    public String getTimeTravel(){ return getDepart()+" - "+getArrive();}

    public String getSeatPosition(int seatIndex){
        String seatPosition = "";
        if (this.busType.equals("Type A")){
        if (seatIndex<=3){
            seatPosition += "A"+(seatIndex+1);
        }
        else if (seatIndex<=7){
            seatPosition += "B"+(seatIndex-3);
        }
        else if (seatIndex<=11){
            seatPosition += "C"+(seatIndex-7);
        }
        else if (seatIndex<=15){
            seatPosition += "D"+(seatIndex-11);
        }
        else if (seatIndex<=19){
            seatPosition += "E"+(seatIndex-15);
        }
        }
        else {
            if (seatIndex<=1){
                seatPosition += "A"+(seatIndex+1);
            }
            else if (seatIndex<=3){
                seatPosition += "B"+(seatIndex-1);
            }
            else if (seatIndex<=5){
                seatPosition += "C"+(seatIndex-3);
            }
            else if (seatIndex<=7){
                seatPosition += "D"+(seatIndex-5);
            }
            else if (seatIndex<=9){
                seatPosition += "E"+(seatIndex-7);
            }
            }
        return seatPosition;
    }

    public int getSeatIndex(String position){
        int seatIndex = 0;
        if (this.busType.equals("Type A")){
            if (position.contains("A")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("A")+1))-1 ;
            }
            else if (position.contains("B")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("B")+1))+3;
            }
            else if (position.contains("C")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("C")+1))+7;
            }
            else if (position.contains("D")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("D")+1))+11;
            }
            else if (position.contains("E")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("E")+1))+15;
            }
        }
        else {
            if (position.contains("A")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("A")+1))-1 ;
            }
            else if (position.contains("B")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("B")+1))+1;
            }
            else if (position.contains("C")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("C")+1))+3;
            }
            else if (position.contains("D")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("D")+1))+5;
            }
            else if (position.contains("E")){
                seatIndex = Integer.parseInt(position.substring(position.indexOf("E")+1))+7;
            }
        }
        return seatIndex;
    }


    @Override
    public String toString(){
        return "Bus ID : "+getBusID()+"\n"+"Bus Type : "+getBusType()+"\n"+"Destination : "+getDestination()+"\n"+"Depart : "+getDepart()+"\n"+"Arrive : "+getArrive()+"\n"+"Duration : "+getDuration()+"\n"+"Rows : "+getRows()+"\n"+"Col : "+getCols()+"\n"+"Cost : "+getCost()+" B"+"\n"+"Seat left : "+getSeatLeft();
    }
}

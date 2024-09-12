import java.util.*;


// convertors for Route and Timings
class Convertor {

    // Converting String to List
    static List<String> stringtolist(String s) {

        String[] array = s.split(",");
        List<String> list = Arrays.asList(array);
        return list;
    }

    // sorting (like snake pattern example start to destination destination to start)
    static ArrayList<String> sorting (List<String> routeList, List<String> timingList) {

        int timelength = timingList.size();
        int routelength = routeList.size();
        ArrayList<String> routedlist = new ArrayList<>();

        for (int i = 0; i < timelength; i++) {
            if (i % 2 == 0) {
                // Start to Destination
                for (int j = 0; j < routelength; j++) {
                    int index = i * routelength + j;
                    if (index < timelength) {
                        routedlist.add(timingList.get(index));
                    }
                }
            } else {
                // Destination to Start
                for (int j = routelength - 1; j >= 0; j--) {
                    int index = i * routelength + j;
                    if (index < timelength) {
                        routedlist.add(timingList.get(index));
                    }
                }
            }
        }
        return routedlist;
    }

    // Grouping corresponding timings to route (if eample :14c, Pollachi : [[10.00,18.00],[17.00]], etc...)
    static ArrayList<ArrayList<List<String>>>  grouping (List<String> routeList, ArrayList<String> routedlist) {

        int count = 0;
    ArrayList<ArrayList<List<String>>> stoplist = new ArrayList<>();
    int routelength = routeList.size();
    for (String r : routeList) {
        ArrayList<List<String>> routeLists = new ArrayList<>();
        routeLists.add(new ArrayList<>()); // First list
        routeLists.add(new ArrayList<>()); // Second list
        stoplist.add(routeLists);
    }
    boolean direction = true ;
    for (int i = 0; i < routedlist.size(); i++) {

        if (count==routelength) {
          direction = !direction ;
          count = 0;
          }

      if (direction) {
        stoplist.get(i % routelength).get(0).add(routedlist.get(i));

      }
      else {
        stoplist.get(i % routelength).get(1).add(routedlist.get(i));
        }
count++;
    }
        return stoplist;
    }
}

// basic details from the user(BusNo, Route, Timings) and (full details)

class Busdetails {
    String bNo;
    String route;
    String timings;
    List<String> routeList;
    List<String> timingList;
    ArrayList<String> routedlist;
    ArrayList<ArrayList<List<String>>>  stoplist;

    Busdetails(String b, String r, String t) {
        bNo = b;
        route = r;
        timings = t;
        routeList = Convertor.stringtolist(route);
        timingList = Convertor.stringtolist(timings);
        routedlist = Convertor.sorting(routeList,timingList);
        stoplist = Convertor.grouping(routeList, routedlist);
    }

        Scanner sc = new Scanner (System.in);

    // display bus Stopping and timing
    void display () {

      for( String s : routeList) {
          System.out.print(s);
          System.out.print("\t");
        }

        System.out.println();
        System.out.println();

      int rl = routeList.size();

      for( int i=0; i<routedlist.size(); i++) {
        if( i%rl==0 && i != 0){
            System.out.println();
            System.out.println();
            }              System.out.print(routedlist.get(i));
            System.out.print("\t\t\t\t\t");
        }

      }

      // update busNo
      void updatebusnumber(String newNo) {
          bNo = newNo;
        }

      // update stoping
      Boolean updatestopping() {

              boolean status;
              System.out.println("which stop : ");
              String index = sc.nextLine();
              if (routeList.contains(index)) {
              System.out.println("what would be : ");
              String newstop=sc.nextLine();
           routeList.set(routeList.indexOf(index),newstop);
              status = true ;
              }
              else {
                System.out.println("stop not found");
                status = false ;
                }
            return status;

        }

      // update timing
      void updatetiming () {

          System.out.println("which row :  ");
          int row = sc.nextInt();
          System.out.println("which column : ");
          int column = sc.nextInt();
          sc.nextLine();
          System.out.println("New timie : ");
          String change= sc.nextLine();
          int index = (row*routeList.size())- (routeList.size()-column);
         routedlist.set(index-1,change);
          stoplist.get(column-1).get((row-1)%2).set((row-1)/2,change);

        }
}

// set of bus details(busNo,StoppingNo, Stop, Timings Example : 14c,0, Pollachi ,[[10.00,18.00],[17.00]], etc...)to the database Bustimings to easy access.

class Dataset {
    String busNo;
    int stoppingNo;
    String stop;
    ArrayList<List<String>> timings;

    Dataset(String busNo, int stoppingNo, String stop, ArrayList<List<String>> timings) {
        this.busNo = busNo;
        this.stoppingNo = stoppingNo;
        this.stop = stop;
        this.timings = timings;
    }
}



class BusTimingsProject {

 static Scanner sc= new Scanner(System.in);

// data_insertion method for adding new bus details
static void data_insertion(TreeMap<String, Busdetails> basic_details) {

  sc.nextLine();
  System.out.println("Example : ");
  System.out.println("Bus No 14c");
  System.out.println("Stopping Pollachi,Thermutti,Marapettai,Teachers colony");
  System.out.println("Timings 10.00,11.00,12.00,13.00,14.00,15.00,16.00,17.00,18.00,19.00,20.00,21.00");
  System.out.println();
  while ( true ) {

            System.out.println("Give Bus Number or back to exit");
            String bNo = sc.nextLine();
            System.out.println();

            if (bNo.equals("back")) {
                break;
            }
            if (basic_details.containsKey(bNo)) {
              System.out.println("Bus number already exists");
              System.out.println();
              }
            else {
System.out.println("Stopping :(separated by commas (,) & Stoping must have \"Pollachi\" )");
            String route = sc.nextLine();
            System.out.println();
            System.out.println("Timings:(separated by commas (,))");
            String timing = sc.nextLine();
            System.out.println();

            Busdetails bus_details = new Busdetails(bNo, route, timing);

            basic_details.put(bNo,bus_details);
            basic_details.get(bus_details.bNo). display();
            System.out.println("Do you want to add another bus details? (yes/no)");
            String wish = sc.nextLine();
            System.out.println();

            if (wish.equals("no")) {
                break;
            }
        }
        }
  }

// update method for bus details
static void update (TreeMap<String, Busdetails> basic_details) {

  boolean firsttime = true ;
  boolean exit = false ;
  while( true ) {

      if (firsttime) {

        System.out.println("which you want to update ?");
          }
        System.out.println();
        firsttime = true ;
        System.out.println("1. Bus Number, 2. Stopping, 3. timings, 4. Exit ");

        int input = scannextInt();
        String busNo;

        switch (input) {

            case 1:

              sc.nextLine();
              System.out.println("Give Bus Number or back to exit");
              busNo =sc.nextLine();
              if (busNo.equals("back")) {
                firsttime = false ;
                break;
                }
if (!basic_details.containsKey(busNo)) {
    System.out.println("Bus number not found");
              System.out.println();
              firsttime = false ;
  }
  else {
System.out.println("what would be : ");
              String newNo =sc.nextLine();
              basic_details.get(busNo).updatebusnumber(newNo);
              Busdetails busdetails= basic_details.remove(busNo);
      basic_details.put(newNo,busdetails);
System.out.println("successful changed");
  }
              break;

            case 2:

              sc.nextLine();
              System.out.println("Give Bus Number or back to exit");
              busNo =sc.nextLine();
              if (busNo.equals("back")) {
                firsttime = false ;
                break;
                }
              if (!basic_details.containsKey(busNo)) {
                System.out.println("Bus Number not found");
              System.out.println();
              firsttime = false ;
                }
              else {
              if (basic_details.get(busNo).updatestopping()) {
                basic_details.get(busNo).display();
System.out.println(basic_details.get(busNo).routeList);
                }
                else {
                  System.out.println("Available :");
                  System.out.println(basic_details.get(busNo).routeList);
                  firsttime = false ;
                  }

              }

              break;

            case 3:

              sc.nextLine();
              System.out.println("Give Bus Number or back to exit");
              busNo =sc.nextLine();
              if (busNo.equals("back")) {
                firsttime = false ;
                break;
                }

                if (!basic_details.containsKey(busNo)) {
                  System.out.println("Bus number not found");
              System.out.println();
              firsttime = false ;
                  }
                else {
                   basic_details.get(busNo).updatetiming ();    basic_details.get(busNo).display();
                   }

              break;

            case 4 :

              exit = true ;
              firsttime = false ;
              break;

            default:

              System.out.println("select between 1 to 4 ");
              firsttime = false ;
              break;

          }
          if( firsttime ) {
          System.out.println("Do you want to edit again (yes/no)");
          firsttime = false ;
          String wish = sc.nextLine();
          if (wish.equals("no")) {
                System.out.println("update completed");
                break;
            }
          }
          if (exit) {
            break;
            }
        }
  }

// database method for inserting data we need (busNo,StoppingNo, Stop, Timings. Example : 14c,0, Pollachi ,[[10.00,18.00],[17.00]], etc...)

static void database ( TreeMap<String, Busdetails> basic_details, TreeMap<String, TreeMap<String,
Dataset>> Bustimings ) {

  for( Busdetails bus_details : basic_details.values()) {
            for (String s : bus_details.routeList) {
                  if (!Bustimings.containsKey(s)) {
                      Bustimings.put(s, new TreeMap<>());
                  }
                  int Is = bus_details.routeList.indexOf(s);
                        Bustimings.get(s).put(bus_details.bNo, new Dataset(bus_details.bNo, Is, s, bus_details.stoplist.get(Is)));

                }

            }
  }

// Search timings by bus method
static void searching_by_bus (TreeMap<String, Busdetails> basic_details) {

  sc.nextLine();
  while( true ) {

        System.out.println("Which bus : or back to exit ");
        String bus_number = sc.nextLine();
        System.out.println();
        if (bus_number.equals("back")) {
          break;
        }
        if (basic_details.containsKey(bus_number)) {
          basic_details.get(bus_number).display();
System.out.println();
}
        else {
          System.out.println("Bus nummber not found");
System.out.println();
            }
          }

  }

// Search timings by place method
static void searching_by_place (TreeMap<String, TreeMap<String,
Dataset>> Bustimings, String From, String To) {

        System.out.println();
        if (Bustimings.containsKey(From) && Bustimings.containsKey(To) && !(From.equals(To))) {

  int timetodisplay;
  boolean result = true ;

        for (Map.Entry<String, Dataset> entry : Bustimings.get(From).entrySet()) {
          String busNo = entry.getKey();
          if (Bustimings.get(To).containsKey(busNo)) {
          System.out.println();
          System.out.println(From+""+" to "+To);
          System.out.println();
          System.out.println("Bus Number: " + Bustimings.get(From).get(busNo).busNo);

          System.out.println();

         timetodisplay = Bustimings.get(From).get(busNo).stoppingNo < Bustimings.get(To).get(busNo). stoppingNo ?0:1;

          System.out.println("Pickup time : " + Bustimings.get(From).get(busNo).timings.get(timetodisplay));

          System.out.println();

          System.out.println("Dropping time : " + Bustimings.get(To).get(busNo).timings.get(timetodisplay));
          System.out.println();
          result = false ;

          }

     }

   if (result) {

          System.out.println();
          System.out.println("No bus for "+From+" to "+To+" but you can choose ");
System.out.println();
  searching_by_place(Bustimings,From,"Pollachi");
  searching_by_place(Bustimings,"Pollachi",To);
          }
     }
     else {

          System.out.println("No bus for given route "+From+" To "+To+" (Note : Check spelling and spacing.)");
          System.out.println();
        }

  }


// Integer exception handling method
static int scannextInt() {

        int choice;
        try {
            choice = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Integer values only");
            sc.nextLine();
            choice = scannextInt();
        }

        return choice;
    }


    public static void main(String[] args) {

        TreeMap<String, Busdetails> basic_details = new TreeMap<>(); // it has full details about bus (objects of Bus details).

        TreeMap<String, TreeMap<String,
Dataset>> Bustimings = new TreeMap<>();
// this is act like data base (objects of Dataset).

Busdetails bus1 = new Busdetails("14c", "Pollachi,Thermutti,Marapettai,Teachers colony", "10.00,11.00,12.00,13.00,14.00,15.00,16.00,17.00,18.00,19.00,20.00,21.00");

basic_details.put(bus1.bNo,bus1);

Busdetails bus2 = new Busdetails("12d", "Pollachi,Thermutti,Marapettai", "10.00,11.00,12.00,13.00,14.00,15.00,16.00,17.00,18.00,19.00,20.00,21.00");

basic_details.put(bus2.bNo,bus2);

Busdetails bus3 = new Busdetails("15c", "Pollachi,Nptc,Unjavelanpatti", "10.00,11.00,12.00,13.00,14.00,15.00,16.00,17.00,18.00,19.00,20.00,21.00");

basic_details.put(bus3.bNo,bus3);

Scanner sc = new Scanner(System.in);

    int choice;
    boolean firsttime = true ;

        while ( true ) {

        if (firsttime) {
          System.out.println();
          System.out.println("what do you want to do ");
        }
        System.out.println();
        System.out.println("1. add/update bus details, 2. Serach timings ");

        choice = scannextInt();

if (choice==1) {

  firsttime = true ;
  while ( firsttime ) {
  System.out.println();
  System.out.println("add or update or back to exit");
  String choice1 =sc.nextLine();
  System.out.println();

  switch (choice1) {

    case "add" :
        data_insertion(basic_details);
        break;

    case "update":
        update (basic_details);
        break;

    case "back" :
        firsttime  = false ;
        break;

    default:
        System.out.println("Give only add or update or back (Note : case sensitive and no spacing and no integer)");
      }
    }
  }
database(basic_details,Bustimings);

if (choice==2) {

        firsttime= true ;
        while( true ) {

        System.out.println("1. By bus,  2. By place, 3. Exit ");
         int choice2 = scannextInt();
  switch (choice2) {

    case 1 :

        System.out.println("Available buses : "+basic_details.keySet());
        System.out.println();
        searching_by_bus(basic_details);

        break;

      case 2:

        System.out.println("Available Routs : "+Bustimings.keySet());
        System.out.println();

        while( true ) {

        System.out.println("Where are you : (From) or back to exit");
        String From = sc.nextLine();
        if (From.equals("back")) {
          break;
        }
        System.out.println("Where you want to go : (To)");
        String To = sc.nextLine();
    searching_by_place(Bustimings,From,To);

        }

        break;

      case 3 :
         firsttime = false ;
         break;

      default:
        System.out.println("select 1 to 3");
        break;

      }
      if( !firsttime ) {
          break;
        }
    }
  }

if (choice !=1 && choice !=2) {
    System.out.println("select 1 or 2 ");
    firsttime = false ;
    }
  }
 }
}

import java.util.Scanner;
public class KeypadWords {
    public static void keypadToWord(String keypadInput) {
        char[][] two = {
    {'a', 'b', 'c'},
    {'d', 'e', 'f'},
    {'g', 'h', 'i'},
    {'j', 'k', 'l'},
    {'m', 'n', 'o'},
    {'p', 'q', 'r','s'},
    {'t', 'u','v'},
    {'w', 'x','y', 'z'}
};
        String[] keys = keypadInput.split(" ");

for (String key : keys)
{
switch (key.charAt(0)){
  case '0' :
       System.out.print(" ");
       break;
  case '1' :
       System.out.print("key 1 does not contain any letters ");
       break;
  case '2' :
       int d1=(key.length()-1);
       System.out.print(two[0][d1>2?d1%3:d1]);
       break;
  case '3' :
       int d2=(key.length()-1);
       System.out.print(two[1][d2>2?d2%3:d2]);
       break;
  case '4' :
       int d3=(key.length()-1);
       System.out.print(two[2][d3>2?d3%3:d3]);
       break;
  case '5' :
       int d4=(key.length()-1);
       System.out.print(two[3][d4>2?d4%3:d4]);
       break;
  case '6' :
       int d5=(key.length()-1);
       System.out.print(two[4][d5>2?d5%3:d5]);
       break;
  case '7' :
       int d6=(key.length()-1);
       System.out.print(two[5][d6>2?d6%4:d6]);
       break;
   case '8':
       int d7=(key.length()-1);
       System.out.print(two[6][d7>2?d7%3:d7]);
       break;
   case '9':
       int d8=(key.length()-1);
       System.out.print(two[7][d8>2?d8%4:d8]);
       break;
  }
}
    }

    public static void main(String[] args) {
      System.out.println("keyboard key convertion ");
      System.out.println("0-(space), 1-(Null), 2-(a,b,c), 3-(d,e,f), 4-(g,h,i), 5-(j,k,l), 6-(m,n,o), 7-(p,q,r,s), 8-(t,u,v), 9-(w,x,y,z).");
      while (true)
      {
        System.out.println();
        System.out.println("Press keys to print : ");
        Scanner scanner = new Scanner(System.in);
      keypadToWord(scanner.nextLine());
      }
    }
}

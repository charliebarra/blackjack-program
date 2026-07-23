/*
Name: Charlie B
Date Last Edited: 4/21
Purpose: Java Spring Project
*/

import java.util.*;
import java.io.*;

public class BlackJack {
   public static void main(String [] args) 
   {
      //Initializations
      
      String[] dval = new String[21];
      String[] pval = new String[21];
      int[] dealer = new int[21];
      int[] user = new int[21];
      boolean stand = false, playing = true;
      double bank, money, bet;
      int input, count = 2, dcount = 2;
      Scanner gorlockTheEaterOfInts = new Scanner(System.in);
      Scanner gorlockTheEaterOfStrings = new Scanner(System.in);
      
      System.out.print("Enter your name ");
      String username = gorlockTheEaterOfStrings.nextLine();
      System.out.print("Enter your account balance ");
      money = gorlockTheEaterOfInts.nextDouble();
      InfoClass userInfo = new InfoClass(username, money);
      System.out.println("Welcome to the blackajack table! \nThe balance in your account is $" + userInfo.getMoney());
      
      //Loop Until Money is Zero
      while (userInfo.getMoney() > 0 && playing == true){
         do{
            System.out.print("How much would you like to bet on this hand? (Must be less than or equal to account balance) $");
            bet = gorlockTheEaterOfInts.nextDouble();
            }while(bet > userInfo.getMoney());
         userInfo.setMoney(userInfo.getMoney() - bet);
         
         //Generate and Value Dealer Initial Cards
         dealer[0] = dealCard();
         dealer[1] = dealCard();
         
         dval[0] = cardValues(dealer[0]);
         dval[1] = cardValues(dealer[1]);
         
         //Generate and Value User Initial Cards
         user[0] = dealCard();
         user[1] = dealCard();

         pval[0] = cardValues(user[0]);
         pval[1] = cardValues(user[1]);
         
         //Display User Initial and Dealer Cards
         System.out.println("Your cards are " + pval[0] + " and " + pval[1]);
         System.out.println("Dealer is showing " + dval[0]);
         
         //Hitting and Standing
         while (calcHandTotal(pval) < 21 && stand == false)
         {
            System.out.print("What would you like to do\n 1. Hit \n 2. Stand \n ");
            input = gorlockTheEaterOfInts.nextInt();
               
            if (input == 1){
               user[count] = dealCard();
               pval[count] = cardValues(user[count]);
               System.out.println("You drew a " + pval[count]);
               count ++; //Counts How many cards the user has drawn
            }
            else if (input == 2){
               stand = true;
            }
            
               
            System.out.println("Your cards are totaled at " + calcHandTotal(pval));
         }
         stand = false;
         
         System.out.println("Dealer flips over a " + dval[1]);
         //Dealer Drawing
         while (calcHandTotal(dval) < 17)
         {
            dealer[dcount] = dealCard();
            dval[dcount] = cardValues(dealer[dcount]);
            System.out.println("Dealer drew a " + dval[dcount]);
            dcount ++; //Counts How many cards the user has drawn
            try{
            Thread.sleep(2000);
            }  catch(InterruptedException e){
            Thread.currentThread().interrupt();
            }
         }
         System.out.println("Dealer cards are totaled at " + calcHandTotal(dval));
         
         if (calcHandTotal(pval) > 21){
            System.out.println("You lost to the dealer!\nYour new balance is " + userInfo.getMoney());
         }
         
         else if (calcHandTotal(dval) > 21){
            userInfo.setMoney(userInfo.getMoney() + bet*2);
            System.out.println("You beat the dealer!\nYour new balance is " + userInfo.getMoney());
         }
         
         else if (calcHandTotal(pval) > calcHandTotal(dval)){
            userInfo.setMoney(userInfo.getMoney() + bet*2);
            System.out.println("You beat the dealer!\nYour new balance is " + userInfo.getMoney());
         }
         else if (calcHandTotal(pval) < calcHandTotal(dval)){
            System.out.println("You lost to the dealer!\nYour new balance is " + userInfo.getMoney());
         }
         else if (calcHandTotal(pval) == calcHandTotal(dval)){
            userInfo.setMoney(userInfo.getMoney() + bet);
            System.out.println("You tied to the dealer!\nYour new balance is " + userInfo.getMoney());
         }
         
         //Loop Game
         if (userInfo.getMoney() > 0){
            System.out.print("Would you rather \n 1. Continue \n 2. Leave \n ");
            input = gorlockTheEaterOfInts.nextInt();
            if (input == 1){
               playing = true;
            }
            else if (input == 2){
               playing = false;
            }
            
            for (int i=0; i < 21; i++){
               dval[i] = "";
               pval[i] = "";
               user[i] = 0;
               dealer[i] = 0;
            }
         }
         else{
            playing = false;
         }
      }
   System.out.println("Thanks for playing " + userInfo.getName() + ". You made it out with " + userInfo.getMoney());
   }
   //Generates Cards
   public static int dealCard()
   {
      Random rand = new Random();
      return rand.nextInt(1,14)+1;
   }
   //Turns Numbers into face cards
   public static String cardValues(int num)
   {
      if (num == 11)
      return "Ace";
      
      else if (num == 12)
      return "Jack";
      
      else if (num == 13)
      return "Queen";
      
      else if (num == 14)
      return "King";
      
      else 
      return String.valueOf(num);
      
   }
   public static int calcHandTotal(String[] val)
   {  
      //Initializations
      int aces=0, handTotal=0, num=0;
      
      //Calculates Card total
      for (int i=0; i < val.length; i++)
      {  
         if (val[i] == null)
            continue;
         if (val[i].equals("Ace"))
            aces++;
         else if (val[i].equals("Jack")||
                  val[i].equals("Queen")||
                  val[i].equals("King"))
            handTotal += 10;
         else if (!val[i].isEmpty())
            handTotal += Integer.parseInt(val[i]);
      }
      
      //Changes Ace value to 1 if hand total would go over 21
      for (int i=0; i < aces; i++)
      {
         if ((handTotal + 11) >21)
            handTotal += 1;
         else
            handTotal += 11;
      }
      
      return handTotal;
      
   }
}
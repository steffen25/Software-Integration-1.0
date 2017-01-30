package clientServer;


import java.io.*;
import java.net.*;

class server
{
   public static void main(String argv[]) throws Exception
   {
         String clientSentence;
         String capitalizedSentence;
         ServerSocket welcomeSocket = new ServerSocket(6000);
         int balance = 2500; 

         while(true)
         {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            
                        
            clientSentence = inFromClient.readLine();
          
            int amount = 0;
            try{
            	amount = Integer.parseInt(clientSentence);
            	System.out.println(amount + " this is the amount");
            }catch(NumberFormatException nfe){
            	outToClient.writeBytes("Not a valid number");
            	
            }
            int newAmount = balance - amount;
            System.out.println(newAmount);
            try{
            	outToClient.writeBytes("Your new balance is" + newAmount + " " +'\n');
            }
            catch(IOException e)
            {
            	System.out.println("exception" + e);
            }
            
            System.out.println("To client: " + "Your new balance is" + newAmount +"/n");
   

         }
      }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steamprogram;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import steamcondenser.SteamCondenserException;
import steamcondenser.steam.community.SteamId;

/**
 *
 * @author Michael
 */
public class SteamProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SteamCondenserException {
        // TODO code application logic here
        
        System.out.println("Enter the name of the users you would like to check\n"
                + "when your done type stop\n");
        
        Scanner kb = new Scanner(System.in);
        String name;
        ArrayList<SteamId> users = new ArrayList<>();
        ArrayList<String> commonGameList = new ArrayList<>();
        ArrayList<Set<String>> games = new ArrayList<>();
        
        
        name = kb.nextLine();
        while (!name.equalsIgnoreCase("stop")) {
            try {
                users.add(SteamId.create(name));
            } catch (SteamCondenserException ex) {
                try {
                    users.add(SteamId.create(Long.valueOf(name)));
                } catch (NumberFormatException e) {
                    System.out.println("Sorry but i cant find the user " + name);
                }
            }
            name = kb.nextLine();
        }

        if (users.size() <= 1) {
            System.out.println("You only entered in one user");
            System.exit(0);
        }
        
        //add everyones games to the game list
        for (SteamId sid : users) {
            System.out.println("Getting " + sid.getNickname() + "'s games...");
            try {
                games.add(sid.getGames().keySet());
            } catch (SteamCondenserException ex) {
                System.out.println("Sorry but i can't find " + sid.getNickname() + "'s games");
            }
        }
        
        //add all games from the first user to the common game list
        //so we can later remove the games.
        for (String s : games.get(0)) {
            commonGameList.add(s);
        }
        
        System.out.println("Finding games in common...\n");
        for (int i = 1; i < games.size(); i++) {
            for (int j = 0; j < commonGameList.size(); j++) {
                if (!(games.get(i).contains(commonGameList.get(j)))) {
                    commonGameList.remove(j);
                    j--;
                }
            }
        }
        
//        for (int i = 0; i < commonGameList.size(); i++) {
//            if ((i % 5) == 0) {
//                System.out.print("\n");
//            }
//            System.out.print(commonGameList.get(i) + ",");
//        }
        
        for (int i = 0; i < commonGameList.size(); i++) {            
            System.out.println(commonGameList.get(i));
        }
    }
}

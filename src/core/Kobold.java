package core;

import client.KoboldClient;

public class Kobold {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("[Kobold IDE] Please provide a file you wish to edit.");
        }

        else {
            String file = args[0];
            KoboldClient client = new KoboldClient();

            KoboldSettings.setCurrentFile(file);

            client.open(KoboldSettings.getCurrentFile());
        }

    }
}
package core;

import client.KoboldClient;

public class Kobold {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("[Kobold IDE] Please provide a file you wish to edit.");
        }

        else {
            String file = args[0];
            KoboldSettings settings = new KoboldSettings();
            KoboldClient client = new KoboldClient();

            settings.setCurrentFile(file);

            client.open(settings.getCurrentFile());
        }

    }
}
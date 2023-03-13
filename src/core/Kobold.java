package core;

import client.KoboldClient;

public class Kobold {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("[Error] : You must provide a directory for Kobold to open.");
            System.exit(1);
        }

        System.setProperty("user.dir", args[0]);
        KoboldClient client = new KoboldClient();
    }
}

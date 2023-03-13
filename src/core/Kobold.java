package core;

import client.KoboldClient;

public class Kobold {
    public static void main(String[] args) {
        System.setProperty("user.dir", args[0]);
        KoboldClient client = new KoboldClient();
    }
}

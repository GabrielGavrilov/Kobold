package core;

import client.frames.Kobold;

public class main {
    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("Please provide a file to edit.");
        }

        else {
            String fileToEdit = args[0];
            Kobold kobold = new Kobold();
            kobold.open(fileToEdit);
        }


    }
}
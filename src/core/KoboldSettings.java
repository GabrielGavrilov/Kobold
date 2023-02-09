package core;

import java.io.File;

public class KoboldSettings {

    private static File fileCurrentlyEditing;

    /**
     *  Sets the current file we're working with.
     *
     *  @param fileToWorkWith Name of the file we're currently working with.
     */
    public static void setCurrentFile(String fileToWorkWith) {
        fileCurrentlyEditing = new File(fileToWorkWith);
    }

    /**
     *  Returns the file we're currently working with.
     *
     *  @return this.fileCurrentlyEditing
     */
    public static File getCurrentFile() {
        return fileCurrentlyEditing;
    }

}

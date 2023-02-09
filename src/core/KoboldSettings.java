package core;

import java.io.File;

public class KoboldSettings {

    private File fileCurrentlyEditing;

    /**
     *  Sets the current file we're working with.
     *
     *  @param fileToWorkWith Name of the file we're currently working with.
     */
    private void setCurrentFile(String fileToWorkWith) {
        this.fileCurrentlyEditing = new File(fileToWorkWith);
    }

    /**
     *  Returns the file we're currently working with.
     *
     *  @return this.fileCurrentlyEditing
     */
    private File getCurrentFile() {
        return this.fileCurrentlyEditing;
    }

}

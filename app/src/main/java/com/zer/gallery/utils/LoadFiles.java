package com.zer.gallery.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LoadFiles {
    private Context _context;
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");
    public LoadFiles(Context context) {
        this._context = context;
    }
    public ArrayList<String> getFilePaths(String filePath) {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(filePath);

        if (directory.isDirectory()) {
            File[] listFiles = directory.listFiles();

            if (listFiles.length > 0) {

                for (int i = 0; i < listFiles.length; i++) {

                    String path = listFiles[i].getAbsolutePath();
                    if(IsSupportedFile(path))
                        filePaths.add(path);
                }
            } else {
                Toast.makeText(
                        _context,
                        " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        }
        return filePaths;
    }
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

}

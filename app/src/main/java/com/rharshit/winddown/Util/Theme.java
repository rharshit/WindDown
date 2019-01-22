package com.rharshit.winddown.Util;

import com.rharshit.winddown.R;

public class Theme {
    private static int theme;

    public static int switchTheme(){
        if(theme == R.style.AppThemeDark){
            theme = R.style.AppThemeLight;
            return theme;
        } else {
            theme = R.style.AppThemeDark;
            return theme;
        }
    }

    public static int getTheme(){
        if(theme == R.style.AppThemeDark){
            return theme;
        } else {
            theme = R.style.AppThemeLight;
            return theme;
        }
    }
}

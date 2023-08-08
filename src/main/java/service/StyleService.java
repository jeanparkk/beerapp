package service;

import model.style.StyleDao;

public class StyleService {

    private StyleDao styleDao;

    public StyleService(StyleDao styleDao){
        this.styleDao = styleDao;
    }
}

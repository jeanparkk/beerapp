package service;

import dto.StyleRespDto;
import model.style.StyleDao;

import java.sql.SQLException;
import java.util.List;

public class StyleService {

    private StyleDao styleDao;

    public StyleService(StyleDao styleDao) {
        this.styleDao = styleDao;
    }

    // 스타일 등록 기능
    public String createStyle(int breweryId, String name) {
        try {
            int result = styleDao.createStyle(breweryId, name);
            if (result > 0) {
                return "성공";
            } else {
                return "실패";
            }
        } catch (SQLException e) {
            System.out.println("Failed to create style: " + e.getMessage());
            return "실패";
        }
    }

    // 전체 팀 목록 조회
    public List<StyleRespDto> getAllStyles() {
        List<StyleRespDto> styles = styleDao.getAllStyles();
        if (!styles.isEmpty()) {
            return styles;
        } else {
            return List.of();
        }
    }
}


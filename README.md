# 맥주관리 프로그램

<br>

## 🧑🏻‍💻 작성자
<table>
   <tr>
      <td align="center">
        <a href="https://github.com/jeanparkk">
          <img src="https://avatars.githubusercontent.com/u/119830820?v=4" width="100px;" alt=""/><br>
          <sub><b>박장희</b></sub><br>
          <sub><b>jeanparkk</b></sub>
        </a>
      </td>
   </tr>
</table>

<br><br>

## 🛠️ 프로젝트에 사용한 기술
<img src="https://img.shields.io/badge/Java 11-FF160B?style=flat-square&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/JDBC-A9225C?style=flat-square&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white">

<br><br>

<br><br>

## 🖥️ 프로젝트 초기세팅
`데이터베이스 스키마/테이블 생성`
``` sql
create database beer;
use beer;

create table brewery(
  id int primary key auto_increment,
  name varchar(20),
  created_at timestamp
);

create table style (
  id int primary key auto_increment,
  brewery_id int,
  name varchar(20),
  created_at timestamp,
  foreign key(brewery_id) references brewery(id)
);

create table beer(
  id int primary key auto_increment,
  style_id int,
  name varchar(20),
  created_at timestamp,
	unique(style),
  foreign key (style_id) references style(id)
);

create table out_beer(
  id int primary key auto_increment,
  beer_id int,
  reason varchar(255),
  created_at timestamp,
  foreign key(beer_id) references beer(id)
);
```

<br>

`의존성 추가`
``` groovy
dependencies {
    implementation 'org.assertj:assertj-core:3.24.2'
    compileOnly group: 'org.projectlombok', name: 'lombok', version: '1.18.28'
    annotationProcessor group: 'org.projectlombok', name: 'lombok', version: '1.18.28'
    implementation 'com.mysql:mysql-connector-j:8.0.32'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

<br>

`DBConnection 생성`
``` java
public class DBConnection {
    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/baseball";
        String username = "root";
        String password = "root1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

<br>

`DBCOnnection 연동 테스트`
``` java
class DBConnectionTest {
    @Test
    @DisplayName("DB Connection 테스트")
    void DBConnectionTest() {
        // Given & When
        Connection connection = DBConnection.getConnection();

        // Then
        assertThat(connection).isNotNull();
    }
}
```

<br><br>

## ✋🏻 깃허브 커밋 메시지 규칙
```
feat: 새로운 기능 추가했을 때
fix: 버그나 오류 수정했을 때
refactor: 코드 리팩토링했을 때
chore: 약간 애매한 기타 변경사항
docs: 리드미 파일이나 md 파일 수정할 때 (문서작업)
```

<br><br>

## 🙌🏻 작업 규칙
- test 브랜치를 생성하여 작업을 진행
- 작업이 끝나면 `develop` 브랜치로 Pull Request
- 모든 작업이 끝난 후 `develop` 브랜치에서 전체 기능 테스트
- 모든 테스트 완료 시 `main` 브랜치로 merge

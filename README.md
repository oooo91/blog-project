### 👨‍💻 개인 프로젝트 소개
**🎇 게시판 기능을 바탕으로 한 일기형 블로그**  
#

### 📜 요구사항 정의서
* [**post-project(notion)**](https://receptive-platinum-aea.notion.site/4d28f5332b1f4a458f88086937abfb9f)
#


### 👨‍🔧 기술 스택

**Back-End**

<img src="https://img.shields.io/badge/Spring Boot-000000?style=flat-square&logo=Spring Boot&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/Gradle-000000?style=flat-square&logo=Gradle&logoColor=#02303A"/></a>
<img src="https://img.shields.io/badge/Spring Security-000000?style=flat-square&logo=Spring Security&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/Spring JPA-000000?style=flat-square&logo=Spring Jpa&logoColor=#6DB33F"/></a>
<img src="https://img.shields.io/badge/MariaDB-000066?style=flat-square&logo=MariaDB&logoColor=#003545"/></a>

**Front-End**

<img src="https://img.shields.io/badge/HTML5-000000?style=flat-square&logo=HTML5&logoColor=#E34F26"/></a>
<img src="https://img.shields.io/badge/CSS3-000000?style=flat-square&logo=CSS3&logoColor=#1572B6"/></a>
<img src="https://img.shields.io/badge/jQuery-000000?style=flat-square&logo=jQuery&logoColor=#0769AD"/></a>
#

### 📑 ERD
<img src="https://ifh.cc/g/AXD7Gx" width="800"/>  
#

### 📱 Demo

#

### ✨ 프로젝트 주요기능

**회원가입/로그인**
- 회원가입
    - 유효한 이메일 주소가 있으면 회원가입이 가능하다.
    - 이메일 인증을 시도한다.
    - 이메일과 비밀번호 유효성을 검사한다.
    - 관리자일 경우 DB에서 ROLE 직접 설정한다.
- 로그인
    - 로그인 시 회원 가입한 적 없는 이메일을 이용하여 로그인을 시도하면 에러가 발생한다.
    - 이메일과 비밀번호 유효성을 검사한다.
    - 구글 로그인이 가능하다.


**아이디/비밀번호 찾기**
- 이메일 인증을 통해 아이디를 찾을 수 있다.
- 아이디 및 이메일 인증을 통해 새로운 비밀번호를 발급한다.


**글 상세보기/작성/수정/삭제**
- 게시글 작성
    - 로그인한 유저는 게시판 글을 작성할 수 있다.
    - 로그인한 유저에게만 게시글 작성 페이지가 보이도록 한다.
    - 썸네일 사진을 업로드할 수 있다.
- 게시글 수정
    - 유저는 자신이 쓴 게시글만 수정할 수 있다.
- 게시글 삭제
    - 유저는 자신이 쓴 게시글만 삭제할 수 있다.
    - 관리자는 모든 유저의 게시글을 삭제할 수 있다.
- 게시글 상세보기
    - 게시판의 글은 로그인 한 유저만 볼 수 있다.
    - 게시판 글 상세보기에서는 제목, 작성일(수정일), 작성자, 본문의 내용이 보인다.
    - 오늘날 날씨를 날씨 이모티콘으로 확인할 수 있다.


**댓글 작성/수정**
- 로그인한 사용자만 댓글을 작성할 수 있다.
- 공백일 경우 유효성을 검사한다.
- 댓글 수정 및 삭제는 댓글 작성자만 가능하다.
- 게시글 삭제될 시 해당 댓글도 같이 삭제되도록 한다.


**달력 페이지**
- 로그인한 유저는 게시판 글을 기간 별로 조회 및 정렬할 수 있다.
- 5개씩 페이징 처리가 된다.


**관리자 페이지**
- 관리자만 열람이 가능하다.
- 사용자의 게시판을 삭제할 수 있다.
- 사용자의 상태 정보를 변경할 수 있다.


**마이 페이지**
- 사용자 정보를 확인하고 수정할 수 있다.
- 소셜 로그인은 비밀번호를 변경할 수 없다.
- 사용자의 기본 정보와 전체 게시글 수, 최근 일주일 게시글 수를 보여준다.
- 탈퇴가 가능하다.
- 프로필 사진을 등록할 수 있다.


**피드 페이지**
- 다른 유저의 다이어리를 조회할 수 있다.
- 다른 유저의 다이어리를 열람할 수 있다.
#

### ☄️ 회고 및 트러블 슈팅

**1. Property or field 'name' cannot be found on object of type 'org.springframework.security.core.userdetails.User'**
- UserDetails를 implements한 CustomUser를 구현하여 이를 해결

**2. Authenticated=true, Details=WebAuthenticationDetail [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null]**
- log.info(authentication.getPrincipal()); 위처럼 로그를 찍었더니 SessionId가 null이 뜨는 문제 발생
- .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS); 세션 정책 설정하여 해결

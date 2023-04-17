### 👨‍💻 개인 프로젝트 소개
**🎇 게시판 기능을 바탕으로 한 일기용 블로그**  
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

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232205715-7634a2c6-6704-4e06-83e6-34c4f8b68d86.gif"/>

---------------------------------------------------


**아이디/비밀번호 찾기**
- 이메일 인증을 통해 아이디를 찾을 수 있다.
- 아이디 및 이메일 인증을 통해 새로운 비밀번호를 발급한다.

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232208029-faf659dd-7bdb-4fff-a9ff-e29be5ac9a44.gif"/>

---------------------------------------------------

**피드 페이지**
- 다른 유저의 다이어리를 조회할 수 있다.
- 다른 유저의 다이어리를 열람할 수 있다.
- 유저가 관리자일 경우 관리자 페이지로 이동할 수 있다.
- 자신의 게시판 페이지로 이동할 수 있다.
- 로그아웃할 수 있다.

**댓글 작성/수정**
- 로그인한 사용자만 댓글을 작성할 수 있다.
- 공백일 경우 유효성을 검사한다.
- 댓글 수정 및 삭제는 댓글 작성자만 가능하다.
- 게시글 삭제될 시 해당 댓글도 같이 삭제되도록 한다.

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232208085-a36fc62e-b3dc-4688-b3d7-d174b6f137be.gif"/>

---------------------------------------------------

**글 상세보기/작성/수정/삭제**
- 게시글 작성
    - 로그인한 유저는 게시판 글을 작성할 수 있다.
    - 로그인한 유저에게만 게시글 작성 페이지가 보이도록 한다.
    - 썸네일 사진을 업로드/삭제할 수 있다.
- 게시글 수정
    - 유저는 자신이 쓴 게시글만 수정할 수 있다.
    - 썸네일 수정/삭제가 가능하다.
- 게시글 삭제
    - 유저는 자신이 쓴 게시글만 삭제할 수 있다.
    - 관리자는 모든 유저의 게시글을 삭제할 수 있다.
- 게시글 상세보기
    - 게시판의 글은 로그인 한 유저만 볼 수 있다.
    - 게시판 글 상세보기에서는 제목, 작성일(수정일), 작성자, 본문의 내용이 보인다.
    - 오늘날 날씨를 이모티콘으로 확인할 수 있다.

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232208380-90263d8a-cdbf-4992-a563-305b8c35b746.gif"/>


 
        유저가 접속한 블로그의 주인인 경우와 아닌 경우의 버튼이 다르다. (본인인 경우 작성 버튼이 뜬다.)
    
|![image](https://user-images.githubusercontent.com/74234719/232209650-08a58eeb-2692-4a81-8026-3718afd677f8.JPG)|![image](https://user-images.githubusercontent.com/74234719/232238518-630058da-23ea-493b-9aef-ef05fd97d997.JPG)
|:---:|:---:|
|**본인인 경우**|**아닌 경우**|
    
    
        악의적으로 api를 사용하여 게시글 작성/수정/삭제를 시도할 시 접근이 불가능하도록 막는다.
   <img width="60%" src="https://user-images.githubusercontent.com/74234719/232239494-02e027e5-926d-4c45-a219-d24429f47eab.JPG"/>
    
---------------------------------------------------
    

**달력 페이지**
- 로그인한 유저는 게시판 글을 기간 별로 조회 및 정렬할 수 있다.
- 5개씩 페이징 처리가 된다.

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232209422-9ac71992-7b99-4e3b-9a2a-12c5298a9bf6.gif"/>

---------------------------------------------------

**관리자 페이지**
- 관리자만 열람이 가능하다.
- 사용자의 게시판을 삭제할 수 있다.
- 사용자의 상태 정보를 변경할 수 있다.

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232209345-f77d214b-ba59-4bb0-9fd0-c2d1cff75b63.gif"/>


        유저가 관리자 권한이 있을 때와 없을 때의 버튼이 다르다. (본인인 경우 관리자 버튼이 생성된다.)
    
|![image](https://user-images.githubusercontent.com/74234719/232239908-fcdd419b-fc2e-477e-b0d1-d3d6287a0de5.JPG)|![image](https://user-images.githubusercontent.com/74234719/232239916-24fc05a0-4c1a-4461-a15c-780ebae022ae.JPG)
|:---:|:---:|
|**관리자인 경우**|**아닌 경우**|
    
    
        악의적으로 api를 사용하여 관리자 페이지에 접근할 시 이를 막는다.
   <img width="60%" src="https://user-images.githubusercontent.com/74234719/232239979-bfa993b1-6647-4652-82d5-51d90667f2c0.JPG"/>

---------------------------------------------------

**마이 페이지**
- 사용자 정보를 확인하고 수정할 수 있다.
- 소셜 로그인은 비밀번호를 변경할 수 없다.
- 사용자의 기본 정보와 전체 게시글 수, 최근 일주일 게시글 수를 보여준다.
- 프로필 사진을 등록할 수 있다.
- 탈퇴가 가능하다.

<img width="80%" src="https://user-images.githubusercontent.com/74234719/232209478-de6e3293-3ade-47f8-ba26-513a892becda.gif"/>


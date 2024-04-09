## 목표
- spring security 를 통한 로그인 구현 
- jpa를 통해 crud 및 페이징 처리
- 기획서 기반으로 작업 일정 진행
- git branch 분리를 통한 형상관리

## 작업 계획
1. 사전 작업
- application.yml 파일 생성
-  postgreSQL DB 연결
-  securityConfig 설정
-  jpa entity 생성

2.  기능 작업&순서
- 관리자 로그인
- 전체 글 조회
- 글 작성
- 글 수정
- 글 삭제
- 글 검색
- 태그 전체 조회
- portfolio 조회,Devkit 구체화
- portfolio 수정
- 예외처리
- 깃허브 로그인
- 댓글 작성
- 댓글 수정
- 댓글 삭제
- 대시보드(redisDB)
3. 화면 작업
- 기본 프레임 작업 및 jquery, bootstrap 라이브러리 추가
- 전체 화면 생성 및 관리자 분기처리
- 예외 화면 생성


## 설정
application.yml
datasource:
driver-class-name: org.postgresql.Driver
url: jdbc:postgresql://localhost:5432/blog(blog 라는 이름의 database 생성)
(sql 명령어 : create database blog;)
username: 아이디입력
password: 비밀번호입력

## 인증과 인가
1. 인증(Authentication)
인증이란 식별 가능한 정보 (이름 , 이메일)를 이용하여 서비스에 등록 유저의 신원 입증하는 과정이다.
즉 나의 서비스에 등록된 사용자에게만 서비스를 제공한다는 뜻으로 간단히 이해하자.

2. 인가(Authorization)
인증만 가지고는 서비스를 운영하기에는 무리가 있다.
인증을 한 사용자에게 모든 서비스를 제공하게 된다면?
내가 작성한 글이 다른 사람에 의해서 수정되거나 삭제될 수 있다.
따라서 인증된 사용자가 접근하려는 자원에 대한 권한이 있는지 확인하는 절차가 필요할 것이다.
또한 인가는 항상 앞에 인증이라는 선행 프로세스가 필요하다.(인증하지 않은 유저의 권한을 알 수 없기 때문에)

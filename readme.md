## 목표
- spring security 를 통한 로그인 구현 
- jpa를 통해 crud 및 페이징 처리
- 기획서 기반으로 작업 일정 진행
- git branch 분리를 통한 형상관리

## 작업 계획
1. 사전 작업
- application)yml 파일 생성
-  postgreSQL DB 연결
-  securityConfig 설정
-  jpa entity 생성
2.  기능 작업
- 관리자 로그인
- 전체 글 조회
- 글 작성
- 글 수정
- 글 삭제
- 글 검색
- 깃허브 로그인
- 댓글 작성
- 댓글 수정
- 댓글 삭제
- 카테고리 생성
- 카테고리명 수정
- 카테고리 삭제
- 태그 전체 조회
- 소개글 조회
- 소개글 수정
- 예외처리
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

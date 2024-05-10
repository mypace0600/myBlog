# Spring Security와 JPA를 학습하기 위한 블로그 프로젝트

배포 url : http://1.236.96.47:9000/


## 1. 팀원 구성
|<img src="https://avatars.githubusercontent.com/u/97038857?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/131014787?v=4" width="150" height="150"/>|
|:-:|:-:|
|hyunsu<br/>[@mypace0600](https://github.com/mypace0600)|ruukr8080<br/>[@ruukr8080](https://github.com/ruukr8080)|

## 2. 프로젝트 목표
- Spring Security의 동작 탐구
- OAuth2 로그인 구현 및 동작 탐구
- JPA를 통한 ORM 개념 정리
- 엔티티간 연관관계 설정 및 개념 정리
- QueryDSL을 활용한 쿼리문 작성
- redis와 batch 맛보기
- thymeleaf를 활용한 화면 개발
- summernote 를 활용한 웹 에디터 적용
- 홈서버 구축 실습

## 3. 스펙
java 17, spring-boot 3.2.1, JPA, postgreSQL, redis, thymeleaf, javascript

## 4. 구조

## 5. 페이지 별 기능
1) 회원
- 회원가입
- 로그인
- 깃허브를 통한 로그인
- 로그아웃
- 최초 진입시 방문자 카운트

2) 포스트
- 글쓰기 (관리자)
- 글 수정 (관리자)
- 글 삭제 (관리자)
- 글 목록 조회
- 글 상세 조회
- 인기 글 보기 (추후 작업 예정)

3) 태그
- 태그 별 게시글 카운팅
- 태그 별 게시글 모아보기

4) 댓글
- 댓글 작성
- 댓글 삭제 (작성자)
- 댓글 수정 (작성자)
- 댓글 목록 조회

5) 이미지
- 글 이미지 등록시 DB 저장
- 처음 이미지 썸네일 지정 (추후 작업 예정)
- 이미지 수정 삭제시 기존 등록된 이미지 삭제 (추후 작업 예정)

## 6. 신경쓴 부분
- Security 설정
- OAuth2 로그인 설정
- Authentication 통합 관리

## 7. 트러블 슈팅
- 무한참조 해결

## 8. 개선 목표
- 추후 작업 예정인 내용을 차례로 작업해보기
- test 코드를 따로 작성하지 않았는데 작성해보고 리펙토링 하기
- 홈서버 DB 백업 스크립트 작성
- 젠킨스를 통한 CICD 구축

## 9. 프로젝트 후기
- 심현수
- 신한

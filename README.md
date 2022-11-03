## 항해99 9기 C반 1조 클론코딩

## :foggy:프로젝트 주제

 벨로그 사이트 클론코딩



📅 프로젝트 기간 : 2022.10.28 ~ 2022.11.03

## 👨‍👩‍👧‍👦 Our Team 

|  공은희  |  김병현   |  원민재   |  오기쁨  |        박지윤        |조민지|
| :------: | :-------: | :-------: | :------: | :------------------: |:-----:|
| @euni1004 | @KimByeungHyun | @meruberu | @joyfive | tjdghkek88@gmail.com |ㅁㄴㅇㅁㄴㅇ|
|   BE    |    BE    |    BE    |   FE    |         FE          |FE|

## 📝 Technologies & Software Used

<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>   <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"/>  

<img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white"/>  <img src="https://img.shields.io/badge/java-FF81F9?style=flat-square"/>  <img src="https://img.shields.io/badge/JSONWebToken-000000?style=flat-square&logo=JsonWebToken&logoColor=white"/>  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>  <img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=flat-square&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/>

<img src="https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=AmazonS3&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=flat-square&logo=AmazonRDS&logoColor=white"/>  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white"/>

## [:star: 프로젝트 팀 노션](https://joyfive.notion.site/C-1-SA-582e3f58ec414bdaa30730374620cf47) :star:
https://joyfive.notion.site/C-1-SA-582e3f58ec414bdaa30730374620cf47

## 🏀 Trouble Shooting

글에 달린 댓글들을 List로 반환 할 때 JPA관계 설정으로 인해 순환참조의 문제를 직면했습니다.

저희는 단순하게 Response 시 댓글List가 담길 객체의 멤버로 Entity를 보유하지 않는 방식을 선택하여 해결했습니다.

게시글을 조건별로 정렬하여 조회하기 위해 좋아요갯수 등을 Post의 Column으로 관리하였습니다.

## 📋 API Table

| 기능                    | method | url                                                  | request header                           | response header                          | request                                                                                                                                            | response                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| --------------------- | ------ | ---------------------------------------------------- | ---------------------------------------- | ---------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 회원가입                  | POST   | /team01/member/signup                                |                                          |                                          | {<br>“userName” : “fasd”,<br>“userId” : “af”,<br>“pw” : “fadf”,<br>“pwCheck” : “fadf”,<br>“intro” : “adf”,<br>}<br>                                | {<br>“status” : 200,<br>“msg” : {“회원가입이 완료되었습니다.”},<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| 중복확인                  | POST   | /team01/member/idCheck                               |                                          |                                          | {<br>“userId” : “asdf”<br>}                                                                                                                        | {<br>“status” : 200,<br>“msg” : {“사용가능한 아이디입니다.”},<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| 로그인                   | POST   | /team01/member/login                                 |                                          | {<br>“Access\_Token” : “fasdfafasf”<br>} | {<br>“userId” : “dfa”,<br>“pw” : “fasd”<br>}                                                                                                       | {<br>“status” : 200,<br>“msg” : {userName + “님 반갑습니다.”},<br>“data” : {<br>“userId” : “ asdf”,<br>"userName":"asdf ",<br>“userImgUrl” : “ad ”,<br>“intro” : “ sdaf”,<br>}<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| 게시물작성                 | POST   | /team01/post                                         | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          | {<br>“file” : \[“ada”, ”adsf”\],<br>”contents” : {<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“postTag”: \[“asdf”, ”das”\],<br>}<br>} | {<br>“status” : 200,<br>“msg” : {“게시글 등록”}<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| 게시물수정                 | PUT    | /team01/post/{postid}                                | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          | {<br>“postContent” : “fasdf”,<br>}                                                                                                                 | {<br>“status” : 200,<br>“msg” : {“게시글 수정”}<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| 게시물삭제                 | DELETE | /team01/post/{postid}                                | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : {“게시글 삭제”}<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| 좋아요                   | POST   | /team01/likes/{postid}                               | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : {“좋아요 완료”}<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| 좋아요 취소                | DELETE | /team01/likes/{postid}                               | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : {“좋아요 취소ㅜㅜ”}<br>“data” : null<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| 게시글 전체조회 좋아요순/시간순     | GET    | /team01/getAllPostByLike<br>/team01/getAllPostByTime |                                          |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : {“”},<br>“data” : {\[<br>“postId” : 1,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : “수정됨”,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12<br>“countCmt” : 0,<br>\],<br>\[<br>“postId” : 2,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : null,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12,<br>“countCmt” : 0,<br>\]}<br>}                                                              |
| 게시글 상세조회              | GET    | /team01/getPost/{postid}                             | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : “”,<br>“data” : {<br>“userName” : “asdf”,<br>“userImg” : “adf”,<br>“tag”: \[“asdf”, ”das”\],<br>“postTitle” : “adf”,<br>“postContent” : “ads”,<br>“postImg” : “adfadf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : “수정됨”,<br>“userLike”: true,<br>“countLike” : 23,<br>“comments” : \[<br>{<br>“userName” : “asdf”,<br>“userImg” : “adf”,<br>“comment” : “ afsf”<br>“commentId” : “”,<br>},<br>{<br>“userName” : “asdf”,<br>“userImg” : “adf”,<br>“comment” : “ afsf”<br>“commentId” : “”,<br>}<br>\]<br>}<br>}                                                                                                                   |
| 댓글작성                  | POST   | /team01/comment/{postId}                             | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          | {<br>“comment” : “댓글내용”<br>}                                                                                                                       | {<br>"status": 200,<br>"data": {<br>"userName": "병현",<br>"userImgPath": "images/normal\_profile.jpg",<br>"comment": "댓글작성",<br>"commentId": 1<br>},<br>"msg": "댓글 작성 완료"<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| 댓글삭제                  | DELETE | /team01/coment/{commentId}                           | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          |                                                                                                                                                    | {    <br>"status": 200,    <br>"data": null,    <br>"msg": "댓글이 삭제 되었습니다"<br>}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| 검색                    | GET    | /team01/search/?content=searchKeyword                |                                          |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : {“”},<br>“data” : {\[<br>“postId” : 1,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : “수정됨”,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12<br>“countCmt” : 0,<br>\],<br>\[<br>“postId” : 2,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : null,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12,<br>“countCmt” : 0,<br>\]}<br>}                                                              |
| 마이페이지 한줄소개 수정         | POST   | /team01/myPage/intro                                 | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          | {<br>"intro": “한줄자기소개”<br>}                                                                                                                        |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| 마이페이지 프로필 수정          | POST   | /team01/myPage/img                                   | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          | {<br>”file” : 파일<br>}                                                                                                                              |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| 마이페이지 조회              | GET    | /team01/getMyPage?id=userId                          | {<br>“Access\_Token” : “fasdfafasf”<br>} |                                          |                                                                                                                                                    | {<br>”status” : 200,<br>”msg” : null,<br>”data”: "myPageReqDto" :<br>{\[<br>“postId” : 1,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : “수정됨”,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12<br>“countCmt” : 0,<br>\],<br>\[<br>“postId” : 2,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : null,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12,<br>“countCmt” : 0,<br>\]},<br>"tag" : \["sdf","dsaf","dsaf","sdf"\]<br>} |
| 태그로 게시물찾기(태그, 사용자아이디) | GET    | /team01/searchTag/user?tag=tag&id=userId             |                                          |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : null,<br>“data” : {\[<br>“postId” : 1,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : “수정됨”,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12<br>“countCmt” : 0,<br>\],<br>\[<br>“postId” : 2,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : null,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12,<br>“countCmt” : 0,<br>\]}<br>}                                                              |
| 태그로 게시물찾기(태그)         | GET    | /team01/searchTag/allr?tag=tag                       |                                          |                                          |                                                                                                                                                    | {<br>“status” : 200,<br>“msg” : null,<br>“data” : {\[<br>“postId” : 1,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : “수정됨”,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12<br>“countCmt” : 0,<br>\],<br>\[<br>“postId” : 2,<br>“postImg” : “ada”,<br>“postTitle” : “adfa”,<br>“postContent” : “fasdf”,<br>“countTime” : “5일전”,<br>“modifiyPost” : null,<br>“countDay” : “오늘”,<br>“userImg” : “safad”,<br>“userName” : “asdf”,<br>“countLike” : 12,<br>“countCmt” : 0,<br>\]}<br>}                                                              |




## 📜 ERD Table

![image](https://user-images.githubusercontent.com/113872554/199542267-f6fef378-126a-42e5-a7da-52bcf59743ff.png)

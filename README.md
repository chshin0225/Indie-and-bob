# SSAFY 3기 2학기 공통 PjT - SubPjt2

서울 1반 5조 **개발세발**



## Overview

### Directory

```BACKEND
.
├── indieAndBob
│   ├── bin
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── pom.xml
│   │   ├── src
│   │   │   ├── main
│   │   │   │   ├── java
│   │   │   │   │   └── com
│   │   │   │   │       └── ssafy
│   │   │   │   │           └── indieAndBob
│   │   │   │   │               ├── IndieAndBobApplication.class
│   │   │   │   │               ├── config
│   │   │   │   │               │   └── SwaggerConfig.class
│   │   │   │   │               ├── game
│   │   │   │   │               │   ├── controller
│   │   │   │   │               │   │   ├── GameCommentController.class
│   │   │   │   │               │   │   └── GameController.class
│   │   │   │   │               │   ├── dao
│   │   │   │   │               │   │   ├── GameCommentDao.class
│   │   │   │   │               │   │   ├── GameCommentDaoImpl.class
│   │   │   │   │               │   │   ├── GameDao.class
│   │   │   │   │               │   │   └── GameDaoImpl.class
│   │   │   │   │               │   ├── dto
│   │   │   │   │               │   │   ├── Game.class
│   │   │   │   │               │   │   ├── GameComment.class
│   │   │   │   │               │   │   ├── GameDetail.class
│   │   │   │   │               │   │   ├── GameLike.class
│   │   │   │   │               │   │   └── GameRegister.class
│   │   │   │   │               │   └── service
│   │   │   │   │               │       ├── GameCommentService.class
│   │   │   │   │               │       ├── GameCommentServiceImpl.class
│   │   │   │   │               │       ├── GameService.class
│   │   │   │   │               │       ├── GameServiceImpl.class
│   │   │   │   │               │       ├── GameUploadService.class
│   │   │   │   │               │       └── GameUploadServiceImpl.class
│   │   │   │   │               ├── jwt
│   │   │   │   │               │   ├── interceptor
│   │   │   │   │               │   │   └── JwtInterceptor.class
│   │   │   │   │               │   └── service
│   │   │   │   │               │       └── JwtService.class
│   │   │   │   │               ├── response
│   │   │   │   │               │   └── dto
│   │   │   │   │               │       └── BasicResponse.class
│   │   │   │   │               ├── reward
│   │   │   │   │               │   ├── controller
│   │   │   │   │               │   │   └── RewardController.class
│   │   │   │   │               │   ├── dao
│   │   │   │   │               │   │   ├── RewardDao.class
│   │   │   │   │               │   │   └── RewardDaoImpl.class
│   │   │   │   │               │   ├── dto
│   │   │   │   │               │   │   └── Reward.class
│   │   │   │   │               │   └── service
│   │   │   │   │               │       ├── RewardService.class
│   │   │   │   │               │       └── RewardServiceImpl.class
│   │   │   │   │               └── user
│   │   │   │   │                   ├── controller
│   │   │   │   │                   │   └── UserController.class
│   │   │   │   │                   ├── dao
│   │   │   │   │                   │   ├── UserDao.class
│   │   │   │   │                   │   └── UserDaoImpl.class
│   │   │   │   │                   ├── dto
│   │   │   │   │                   │   ├── Follow.class
│   │   │   │   │                   │   └── User.class
│   │   │   │   │                   └── service
│   │   │   │   │                       ├── UserService.class
│   │   │   │   │                       └── UserServiceImpl.class
│   │   │   │   └── resources
│   │   │   │       ├── application.properties
│   │   │   │       └── mybatis
│   │   │   │           ├── mapper
│   │   │   │           │   ├── gameCommentMapper.xml
│   │   │   │           │   ├── gameMapper.xml
│   │   │   │           │   ├── rewardMapper.xml
│   │   │   │           │   └── userMapper.xml
│   │   │   │           └── mybatis_config.xml
│   │   │   └── test
│   │   │       └── java
│   │   │           └── com
│   │   │               └── ssafy
│   │   │                   └── indieAndBob
│   │   │                       └── IndieAndBobApplicationTests.class
│   │   └── target
│   │       └── classes
│   │           └── META-INF
│   │               └── MANIFEST.MF
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── ssafy
│   │   │   │           └── indieAndBob
│   │   │   │               ├── IndieAndBobApplication.java
│   │   │   │               ├── alarm
│   │   │   │               │   ├── controller
│   │   │   │               │   │   ├── AlarmController.java
│   │   │   │               │   │   └── Socket.java
│   │   │   │               │   ├── dao
│   │   │   │               │   │   ├── AlarmDao.java
│   │   │   │               │   │   └── AlarmDaoImpl.java
│   │   │   │               │   ├── dto
│   │   │   │               │   │   └── Alarm.java
│   │   │   │               │   └── service
│   │   │   │               │       ├── AlarmService.java
│   │   │   │               │       └── AlarmServiceImpl.java
│   │   │   │               ├── config
│   │   │   │               │   └── SwaggerConfig.java
│   │   │   │               ├── game
│   │   │   │               │   ├── controller
│   │   │   │               │   │   ├── GameCommentController.java
│   │   │   │               │   │   └── GameController.java
│   │   │   │               │   ├── dao
│   │   │   │               │   │   ├── GameCommentDao.java
│   │   │   │               │   │   ├── GameCommentDaoImpl.java
│   │   │   │               │   │   ├── GameDao.java
│   │   │   │               │   │   └── GameDaoImpl.java
│   │   │   │               │   ├── dto
│   │   │   │               │   │   ├── Game.java
│   │   │   │               │   │   ├── GameAll.java
│   │   │   │               │   │   ├── GameComment.java
│   │   │   │               │   │   ├── GameDetail.java
│   │   │   │               │   │   └── GameLike.java
│   │   │   │               │   └── service
│   │   │   │               │       ├── GameCommentService.java
│   │   │   │               │       ├── GameCommentServiceImpl.java
│   │   │   │               │       ├── GameService.java
│   │   │   │               │       ├── GameServiceImpl.java
│   │   │   │               │       ├── GameUploadService.java
│   │   │   │               │       └── GameUploadServiceImpl.java
│   │   │   │               ├── jwt
│   │   │   │               │   ├── interceptor
│   │   │   │               │   │   └── JwtInterceptor.java
│   │   │   │               │   └── service
│   │   │   │               │       └── JwtService.java
│   │   │   │               ├── kakaopay
│   │   │   │               │   ├── controller
│   │   │   │               │   │   └── KakaopayController.java
│   │   │   │               │   ├── dto
│   │   │   │               │   │   ├── AmountVO.java
│   │   │   │               │   │   ├── CardVO.java
│   │   │   │               │   │   ├── KakaoPayApprovalVO.java
│   │   │   │               │   │   └── KakaoPayReadyVO.java
│   │   │   │               │   └── service
│   │   │   │               │       └── KakaoPay.java
│   │   │   │               ├── response
│   │   │   │               │   └── dto
│   │   │   │               │       └── BasicResponse.java
│   │   │   │               ├── reward
│   │   │   │               │   ├── controller
│   │   │   │               │   │   └── RewardController.java
│   │   │   │               │   ├── dao
│   │   │   │               │   │   ├── RewardDao.java
│   │   │   │               │   │   └── RewardDaoImpl.java
│   │   │   │               │   ├── dto
│   │   │   │               │   │   └── Reward.java
│   │   │   │               │   └── service
│   │   │   │               │       ├── RewardService.java
│   │   │   │               │       └── RewardServiceImpl.java
│   │   │   │               └── user
│   │   │   │                   ├── controller
│   │   │   │                   │   └── UserController.java
│   │   │   │                   ├── dao
│   │   │   │                   │   ├── UserDao.java
│   │   │   │                   │   └── UserDaoImpl.java
│   │   │   │                   ├── dto
│   │   │   │                   │   ├── Follow.java
│   │   │   │                   │   └── User.java
│   │   │   │                   └── service
│   │   │   │                       ├── UserService.java
│   │   │   │                       └── UserServiceImpl.java
│   │   │   └── resources
│   │   │       ├── application.properties
│   │   │       ├── mybatis
│   │   │       │   ├── mapper
│   │   │       │   │   ├── alarmMapper.xml
│   │   │       │   │   ├── gameCommentMapper.xml
│   │   │       │   │   ├── gameMapper.xml
│   │   │       │   │   ├── rewardMapper.xml
│   │   │       │   │   └── userMapper.xml
│   │   │       │   └── mybatis_config.xml
│   │   │       └── static
│   │   │           └── index.html
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── ssafy
│   │                   └── indieAndBob
│   │                       └── IndieAndBobApplicationTests.java
│   └── target
│       ├── classes
│       │   ├── META-INF
│       │   │   ├── MANIFEST.MF
│       │   │   └── maven
│       │   │       └── com.ssafy
│       │   │           └── indieAndBob
│       │   │               ├── pom.properties
│       │   │               └── pom.xml
│       │   ├── application.properties
│       │   ├── com
│       │   │   └── ssafy
│       │   │       └── indieAndBob
│       │   │           ├── IndieAndBobApplication.class
│       │   │           ├── config
│       │   │           │   └── SwaggerConfig.class
│       │   │           ├── game
│       │   │           │   ├── controller
│       │   │           │   │   ├── GameCommentController.class
│       │   │           │   │   └── GameController.class
│       │   │           │   ├── dao
│       │   │           │   │   ├── GameCommentDao.class
│       │   │           │   │   ├── GameCommentDaoImpl.class
│       │   │           │   │   ├── GameDao.class
│       │   │           │   │   └── GameDaoImpl.class
│       │   │           │   ├── dto
│       │   │           │   │   ├── Game.class
│       │   │           │   │   ├── GameAll.class
│       │   │           │   │   ├── GameComment.class
│       │   │           │   │   ├── GameDetail.class
│       │   │           │   │   └── GameLike.class
│       │   │           │   └── service
│       │   │           │       ├── GameCommentService.class
│       │   │           │       ├── GameCommentServiceImpl.class
│       │   │           │       ├── GameService.class
│       │   │           │       ├── GameServiceImpl.class
│       │   │           │       ├── GameUploadService.class
│       │   │           │       └── GameUploadServiceImpl.class
│       │   │           ├── jwt
│       │   │           │   ├── interceptor
│       │   │           │   │   └── JwtInterceptor.class
│       │   │           │   └── service
│       │   │           │       └── JwtService.class
│       │   │           ├── kakaopay
│       │   │           │   ├── controller
│       │   │           │   │   └── KakaopayController.class
│       │   │           │   ├── dto
│       │   │           │   │   ├── AmountVO.class
│       │   │           │   │   ├── CardVO.class
│       │   │           │   │   ├── KakaoPayApprovalVO.class
│       │   │           │   │   └── KakaoPayReadyVO.class
│       │   │           │   └── service
│       │   │           │       └── KakaoPay.class
│       │   │           ├── response
│       │   │           │   └── dto
│       │   │           │       └── BasicResponse.class
│       │   │           ├── reward
│       │   │           │   ├── controller
│       │   │           │   │   └── RewardController.class
│       │   │           │   ├── dao
│       │   │           │   │   ├── RewardDao.class
│       │   │           │   │   └── RewardDaoImpl.class
│       │   │           │   ├── dto
│       │   │           │   │   └── Reward.class
│       │   │           │   └── service
│       │   │           │       ├── RewardService.class
│       │   │           │       └── RewardServiceImpl.class
│       │   │           └── user
│       │   │               ├── controller
│       │   │               │   └── UserController.class
│       │   │               ├── dao
│       │   │               │   ├── UserDao.class
│       │   │               │   └── UserDaoImpl.class
│       │   │               ├── dto
│       │   │               │   ├── Follow.class
│       │   │               │   └── User.class
│       │   │               └── service
│       │   │                   ├── UserService.class
│       │   │                   └── UserServiceImpl.class
│       │   └── mybatis
│       │       ├── mapper
│       │       │   ├── gameCommentMapper.xml
│       │       │   ├── gameMapper.xml
│       │       │   ├── rewardMapper.xml
│       │       │   └── userMapper.xml
│       │       └── mybatis_config.xml
│       └── test-classes
│           └── com
│               └── ssafy
│                   └── indieAndBob
│                       └── IndieAndBobApplicationTests.class
└── makeTable.sql

125 directories, 158 files
```



```
├── README.md
├── __init__.py
├── babel.config.js
├── package-lock.json
├── package.json
├── public
│   └── index.html
├── settings.py
├── src
│   ├── App.vue
│   ├── api
│   │   └── base.js
│   ├── assets
│   │   └── default_profile.png
│   ├── components
│   │   ├── GameDetail
│   │   │   ├── CreateQnA.vue
│   │   │   ├── GameCommunity.vue
│   │   │   ├── GameLike.vue
│   │   │   └── QuestionandAnswer.vue
│   │   ├── ProjectCard.vue
│   │   ├── ProjectSettings
│   │   │   └── SettingsQnA.vue
│   │   ├── ToastEditor.vue
│   │   ├── ToastViewer.vue
│   │   └── user
│   │       ├── FollowInfo.vue
│   │       ├── FundedProjects.vue
│   │       ├── LikedProjects.vue
│   │       ├── MyInfo.vue
│   │       └── MyProjects.vue
│   ├── main.js
│   ├── plugins
│   │   └── vuetify.js
│   ├── router
│   │   └── index.js
│   ├── store
│   │   └── index.js
│   └── views
│       ├── IndexFeed.vue
│       ├── admin
│       │   └── NewProjectRequest.vue
│       ├── community
│       │   ├── CommunityArticle.vue
│       │   ├── CommunityForm.vue
│       │   └── CommunityMain.vue
│       ├── error
│       │   ├── AuthorizationError.vue
│       │   ├── ErrorPage.vue
│       │   └── PageNotFound.vue
│       ├── fund
│       │   ├── CreateFund.vue
│       │   ├── CreateFundDone.vue
│       │   ├── GameDetail.vue
│       │   └── GameMain.vue
│       ├── project
│       │   ├── CreateProject.vue
│       │   ├── CreateProjectDone.vue
│       │   ├── EditProject.vue
│       │   ├── ProjectDetail.vue
│       │   ├── ProjectSettings.vue
│       │   └── SearchResult.vue
│       └── user
│           ├── Edit.vue
│           ├── Join.vue
│           ├── Login.vue
│           ├── MyPage.vue
│           └── PasswordChange.vue
├── urls.py
├── vue.config.js
├── wsgi.py
└── yarn.lock

18 directories, 54 files
```



### 기술 스택

Back End

- Spring(S.T.S 4.7)
- mySql
- workbench
- mybatis

Frontend

- npm(12.18.3)
- vue.js(vue 3)
- vuetify
- npm packages

```
+-- @toast-ui/vue-editor@2.3.0
+-- @vue/cli-plugin-babel@4.4.6
+-- @vue/cli-plugin-eslint@4.4.6
+-- @vue/cli-plugin-router@4.4.6
+-- @vue/cli-plugin-vuex@4.4.6
+-- @vue/cli-service@4.4.6
+-- axios@0.19.2
+-- babel-eslint@10.1.0
+-- core-js@3.6.5
+-- email-validator@2.0.4
+-- eslint@6.8.0
+-- eslint-plugin-vue@6.2.2
+-- password-validator@5.0.3
+-- sass@1.26.10
+-- sass-loader@8.0.2
+-- socket.io-client@2.3.0
+-- v-clipboard@2.2.3
+-- vue@2.6.11
+-- vue-cli-plugin-vuetify@2.0.6
+-- vue-cookies@1.7.3
+-- vue-infinite-loading@2.4.5
+-- vue-moment@4.1.0
+-- vue-router@3.3.4
+-- vue-simple-alert@1.1.1
+-- vue-socket.io@3.0.9
+-- vue-template-compiler@2.6.11
+-- vuetify@2.3.4
+-- vuetify-loader@1.6.0
+-- vuex@3.5.1
`-- UNMET DEPENDENCY yarn@1.22.4
```

Extra

- Deploy : AWS
- Gitlab
- Jira





### ERD

![image-20200720132101124](C:\Users\multicampus\Desktop\jungwon\s03p12a105\image-20200720132101124.png)



### Deploy

http://i3a105.p.ssafy.io:8080/






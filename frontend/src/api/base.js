export default {
    'BASE': "http://localhost:8080",

    // user
    'LOGIN': "/account/login",
    'SIGNUP': "/account/signup",
    'PWCHANGE': "/account/pwchange",
    'USERINFO' : "/account/userinfo",

    // follow
    'FOLLOWING': "/following",
    'FOLLOWER': "/follower",
    'UNFOLLOW': "/unfollow?following=",
    'ISFOLLOWING': "/isfollowing?following=",

    // project
    'UNAPPROVED' : "/admin/gamelist/",
    'APPROVE' : "/game/approve",
    'GAMELIST' : "/gamelist/",
    'GAME': "/game/",
    'GAMEEDIT': "/game/",
    'GAMEREGISTER' : "/game/registergame",
    'GAMECOMMUNITY' : "/game/comment",
    'MYPROJECT': "/madegame/",

    // fund
    'ALLREWARD': "/reward/",
    'REWARDS' : "/rewards/bygameid/",
    'REWARDDETAIL': "/reward/hj/",
    'FUNDEDPROJECT': "/fundingByNickname/",

    // game community
    'QNA': "/qna/readByGameID/",
    'NOSECRETQNA': "/qna/readByGameIDNoSecret/",
    'CREATEQNA': "/qna/create",
    'UPDATEQNA': "/qna/update",
    'DELETEQNA': "/qna/delete/",
    'ANSWER': "/qna/updateAnswer",

    // kakaopay
    'KAKAOPAY': "/kakaoPay",

    // like
    'LIKE': "/game/like",
    'ISLIKE': "/game/islike",
    'LIKEBYUSER' : "/game/like/gamelist/",
    'LIKEBYGAME' : "/game/like/userlist/",
    'LIKEDPROJECT': "/game/like/gamelist",

    // community
    'COMMUNITY': '/community/',
    'COMMUNITYLIST': '/communitylist/',
    'COMMUNITYCOMMENT': '/community/comment',
    
    // search
    'SEARCH': "/user/search?keyword=",
}


// "http://i3a105.p.ssafy.io:8080", 
// "http://localhost:8080",

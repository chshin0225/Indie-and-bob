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
    'GAMEREGISTER' : "/game/registergame",
    'GAMECOMMUNITY' : "/game/comment",

    // fund
    'ALLREWARD': "/reward/",
    'REWARDS' : "/rewards/bygameid/",
    'REWARDDETAIL': "/reward/hj/",

    // kakaopay
    'KAKAOPAY': "/kakaoPay",

    // like
    'LIKE': "/game/like",
    'ISLIKE': "/game/islike",
    'LIKEBYUSER' : "/game/like/gamelist/",
    'LIKEBYGAME' : "/game/like/userlist/",
    'LIKEDPROJECT': "/game/like/gamelist",
    
    // search
    'SEARCH': "/user/search?keyword=",
}


// "http://i3a105.p.ssafy.io:8080", 
// "http://localhost:8080",

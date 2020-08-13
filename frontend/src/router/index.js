import Vue from 'vue'
import VueRouter from 'vue-router'
import cookies from 'vue-cookies'

// home
import Home from '../views/Home.vue'

// admin
import NewProjectRequest from '../views/admin/NewProjectRequest.vue'

// user
import Login from '../views/user/Login.vue'
import Signup from '../views/user/Signup.vue'
import PasswordChange from '../views/user/PasswordChange.vue'
import MyPage from '../views/user/MyPage.vue'
import Edit from '../views/user/Edit.vue'

// project making
import CreateProject from '../views/project/CreateProject.vue'
import CreateProjectDone from '../views/project/CreateProjectDone.vue'
import EditProject from '../views/project/EditProject.vue'
import ProjectDetail from '../views/project/ProjectDetail.vue'

// project + funding
import GameMain from '../views/fund/GameMain.vue'
import CreateFund from '../views/fund/CreateFund.vue'
import PurchaseSuccess from '../views/fund/PurchaseSuccess.vue'
import GameDetail from '../views/fund/GameDetail.vue'


// community
import CommunityMain from '../views/community/CommunityMain.vue'
import CommunityForm from '../views/community/CommunityForm.vue'
import CommunityArticle from '../views/community/CommunityArticle.vue'
import CommunityEdit from '../views/community/CommunityEdit.vue'

// search
import SearchResult from '../views/project/SearchResult.vue'

// error
import PageNotFound from '../views/error/PageNotFound.vue'
import ErrorPage from '../views/error/ErrorPage.vue'
import AuthorizationError from '../views/error/AuthorizationError.vue'


Vue.use(VueRouter)

const routes = [
  // home
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {
        title: 'Indie and Bob'
      },
    },

  // admin
  {
    path: '/newrequest',
    name: 'NewProjectRequest',
    component: NewProjectRequest,
    meta: {
      title: '프로젝트 심사'
    },
  },

  // user
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: {
      title: '로그인'
    },
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
    meta: {
      title: '회원가입'
    },
  },
  {
    path: '/user/password',
    name: 'PasswordChange',
    component: PasswordChange,
    meta: {
      title: '비밀번호 변경'
    },
  },
  {
    path: '/user/mypage/:username',
    name: 'MyPage',
    component: MyPage,
    meta: {
      title: '마이 페이지'
    },
  },
  {
    path: '/user/edit/:username',
    name: 'Edit',
    component: Edit,
    meta: {
      title: '내 정보 수정',
    },
  },

  //project(개발자 사이드) 관련
  {
    path : '/newproject',
    name : 'CreateProject',
    component: CreateProject,
    meta: {
      title: '새 프로젝트'
    },
  },
  {
    path: '/project/:id',
    name: 'ProjectDetail',
    component: ProjectDetail,
    meta: {
      title: '새 프로젝트'
    },
  },
  {
    path: '/completed',
    name: 'CreateProjectDone',
    component: CreateProjectDone,
    meta: {
      title: '프로젝트 등록 완료'
    },
  }, 
  {
    path:'/pjt/edit/:id',
    name: 'EditProject',
    component : EditProject,
    meta: {
      title: '프로젝트 수정'
    }
  },

  //game(고객 사이드) 관련
  {
    path: '/game/:id',
    name: 'GameDetail',
    component: GameDetail,
    meta: {
      title: '프로젝트 상세보기'
    },
  },
  {
    path: '/fund/:id',
    name: 'CreateFund',
    component: CreateFund,
    meta: {
      title: '펀딩하기',
    },
  },
  {
    path: '/purchase/:rewardId',
    name: 'PurchaseSuccess',
    component: PurchaseSuccess,
    meta: {
      title: '결제 완료'
    }
  },
  {
    path: '/projects',
    name: 'GameMain',
    component: GameMain,
    meta: {
      title: '모든 프로젝트'
    },
  },

   // community
   {
    path: '/community',
    name: 'CommunityMain',
    component: CommunityMain,
    meta: {
      title: '커뮤니티'
    },
  },
  {
    path: '/community/new',
    name: 'CommunityForm',
    component: CommunityForm,
    meta: {
      title: '새 글 쓰기',
    },
  },
  {
    path: '/community/:articleId',
    name: 'CommunityArticle',
    component: CommunityArticle,
    meta: {
      title: '커뮤니티'
    },
  },
  {
    path: '/community/edit/:articleId',
    name: 'CommunityEdit',
    component: CommunityEdit,
    meta: {
      title: '글 수정',
    }
  },

  // search
  {
    path: '/search/:keyword',
    name: 'SearchResult',
    component: SearchResult,
    meta: {
      title: '검색 결과',
    }
  },

  // error
  {
    path: '/404',
    name: 'PageNotFound',
    component: PageNotFound,
    meta: {
      title: '페이지 없음'
    },
  },
  {
    path: '/405',
    name: 'AuthorizationError',
    component: AuthorizationError,
    meta: {
      title: '권한 없음'
    }
  },
  {
    path: '/error',
    name: 'ErrorPage',
    component: ErrorPage,
    meta: {
      title: '에러 페이지'
    },
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // 로그인 필요없음
  const PublicPages = [
    'Home',
    'Login',
    'Signup',
    'GameMain',
    'GameDetail',
    'CommunityMain',
    'PageNotFound',
    'ErrorPage',
    'AuthorizationError',
  ]

  // 로그인되어 있으면 안됨
  const authPages = [
    'Login',
    'Signup',
  ]

  const adminPages = [
    'NewProjectRequest',
  ]

  console.log('to', to.name)

  const authRequired = !PublicPages.includes(to.name)
  const unauthRequired = authPages.includes(to.name)
  const adminRequired = adminPages.includes(to.name)

  const isLoggedIn = cookies.isKey('user')
  const isAdmin = localStorage.getItem('username') === 'admin'
  // console.log('admin', isAdmin)

  // tab 제목 바꾸기
  document.title = to.meta.title

  // 다음 페이지 갈 권한 있는지 확인 
  if (unauthRequired && isLoggedIn) {
    next({ name: 'Home'})
  }

  // admin 아닌 사람이 admin 권한 접근시
  if (adminRequired && !isAdmin) {
    next({ name: 'Home'})
  }

  if (authRequired && !isLoggedIn) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router
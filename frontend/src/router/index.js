import Vue from 'vue'
import VueRouter from 'vue-router'

// user
import Login from '../views/user/Login.vue'
import Join from '../views/user/Join.vue'
import PasswordChange from '../views/user/PasswordChange.vue'
import MyPage from '../views/user/MyPage.vue'


import FeedMain from '../views/IndexFeed.vue'

//project making
import CreateProject from '../views/project/CreateProject.vue'
import CreateProjectDone from '../views/project/CreateProjectDone.vue'
import ProjectDetail from '../views/project/ProjectDetail.vue'

//project + funding
import CreateFund from '../views/fund/CreateFund.vue'
import GameDetail from '../views/fund/GameDetail.vue'

// community
import CommunityMain from '../views/community/CommunityMain.vue'
import CommunityForm from '../views/community/CommunityForm.vue'
import CommunityArticle from '../views/community/CommunityArticle.vue'

// error
import PageNotFound from '../views/error/PageNotFound.vue'
import ErrorPage from '../views/error/ErrorPage.vue'

Vue.use(VueRouter)

const routes = [
  // user
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/user/join',
    name: 'Join',
    component: Join
  },
  {
    path: '/user/password',
    name: 'PasswordChange',
    component: PasswordChange
  },
  {
    path: '/user/mypage',
    name: 'MyPage',
    component: MyPage
  },

  // main
  {
    path: '/feed/main',
    name: 'FeedMain',
    component: FeedMain
  },

  // community
  {
    path: '/community',
    name: 'CommunityMain',
    component: CommunityMain,
  },
  {
    path: '/community/new',
    name: 'CommunityForm',
    component: CommunityForm,
  },
  {
    path: '/community/:articleId',
    name: 'CommunityArticle',
    component: CommunityArticle,
  },

  // error
  {
    path: '/404',
    name: 'PageNotFound',
    component: PageNotFound
  },
  {
    path: '/error',
    name: 'ErrorPage',
    component: ErrorPage
  },
  {
    path: '/user/mypage',
    name: 'MyPage',
    component: MyPage
  },

  //project(개발자 사이드) 관련
  {
    path : '/project/new',
    name : 'CreateProject',
    component: CreateProject
  },
  {
    path: '/project/:id',
    name: 'ProjectDetail',
    component: ProjectDetail 
  },
  {
    path: '/project/done',
    name: 'CreateProjectDone',
    component: CreateProjectDone
  },

  //game(고객 사이드) 관련
  {
    path: '/game/:id',
    name: 'GameDetail',
    component: GameDetail
  },
  {
    path: '/fund/:id',
    name: 'CreateFund',
    component: CreateFund
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

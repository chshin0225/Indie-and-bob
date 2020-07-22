import Vue from 'vue'
import VueRouter from 'vue-router'

// user
import Login from '../views/user/Login.vue'
import Join from '../views/user/Join.vue'
import PasswordChange from '../views/user/PasswordChange.vue'
import MyPage from '../views/user/MyPage.vue'


import FeedMain from '../views/IndexFeed.vue'

import CreateProject from '../views/project/CreateProject.vue'
import CreateProjectDone from '../views/project/CreateProjectDone.vue'
import ProjectDetail from '../views/project/ProjectDetail.vue'
import ProjectMain from '../views/project/ProjectMain.vue'

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

  //project 관련
  {
    path : '/project/new',
    name : 'CreateProject',
    component: CreateProject
  },
  {
    path: '/project',
    name: 'ProjectDetail',
    component: ProjectDetail 
  },
  {
    path: '/project/done',
    name: 'CreateProjectDone',
    component: CreateProjectDone
  },
  {
    path: '/project/all',
    name: 'ProjectMain',
    component: ProjectMain,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

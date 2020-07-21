import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../views/user/Login.vue'
import Join from '../views/user/Join.vue'
import PasswordChange from '../views/user/PasswordChange.vue'
import MyPage from '../views/user/MyPage.vue'


import FeedMain from '../views/IndexFeed.vue'

import CreateProject from '../views/project/CreateProject.vue'
import CreateReward from '../views/project/CreateReward.vue'
import CreateProjectDone from '../views/project/CreateProjectDone.vue'

import PageNotFound from '../views/error/PageNotFound.vue'
import ErrorPage from '../views/error/ErrorPage.vue'

Vue.use(VueRouter)

const routes = [
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
    path: '/feed/main',
    name: 'FeedMain',
    component: FeedMain
  },
  {
    path: '/user/password',
    name: 'PasswordChange',
    component: PasswordChange
  },
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
  {
    path : '/project/new',
    name : 'CreateProject',
    component: CreateProject
  },
  {
    path: '/project/rewards',
    name: 'CreateReward',
    component: CreateReward
  },
  {
    path: '/project/done',
    name: 'CreateProjectDone',
    component: CreateProjectDone
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

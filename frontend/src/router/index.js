import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../views/Login.vue'
import Join from '../views/Join.vue'
import PasswordChange from '../views/PasswordChange.vue'

import FeedMain from '../views/IndexFeed.vue'

import PageNotFound from '../views/PageNotFound.vue'
import ErrorPage from '../views/ErrorPage.vue'

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
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

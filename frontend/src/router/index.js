import Vue from 'vue'
import VueRouter from 'vue-router'

// user
import Login from '../views/user/Login.vue'
import Join from '../views/user/Join.vue'
import PasswordChange from '../views/user/PasswordChange.vue'
import MyPage from '../views/user/MyPage.vue'
import Edit from '../views/user/Edit.vue'

import FeedMain from '../views/IndexFeed.vue'

//project making
import CreateProject from '../views/project/CreateProject.vue'
import CreateProjectDone from '../views/project/CreateProjectDone.vue'
import EditProject from '../views/project/EditProject.vue'
import ProjectDetail from '../views/project/ProjectDetail.vue'
import SearchResult from '../views/project/SearchResult.vue'
import ProjectSettings from '../views/project/ProjectSettings.vue'

//project + funding
import GameMain from '../views/fund/GameMain.vue'
import CreateFund from '../views/fund/CreateFund.vue'
import CreateFundDone from '../views/fund/CreateFundDone.vue'
import GameDetail from '../views/fund/GameDetail.vue'

// community
import CommunityMain from '../views/community/CommunityMain.vue'
import CommunityForm from '../views/community/CommunityForm.vue'
import CommunityArticle from '../views/community/CommunityArticle.vue'

// error
import PageNotFound from '../views/error/PageNotFound.vue'
import ErrorPage from '../views/error/ErrorPage.vue'
import AuthorizationError from '../views/error/AuthorizationError.vue'

// admin
import NewProjectRequest from '../views/admin/NewProjectRequest.vue'

Vue.use(VueRouter)

const routes = [
  // admin
  {
    path: '/newrequest',
    name: 'NewProjectRequest',
    component: NewProjectRequest,
    meta: {
      title: 'New Project Request'
    },
  },

  // user
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: {
      title: 'Login'
    },
  },
  {
    path: '/user/join',
    name: 'Join',
    component: Join,
    meta: {
      title: 'Signup'
    },
  },
  {
    path: '/user/password',
    name: 'PasswordChange',
    component: PasswordChange,
    meta: {
      title: 'Change Password'
    },
  },
  {
    path: '/user/mypage/:username',
    name: 'MyPage',
    component: MyPage,
    meta: {
      title: 'My page'
    },
  },
  {
    path: '/user/edit',
    name: 'Edit',
    component: Edit,
    meta: {
      title: 'Edit Profile',
    },
  },

  // main
  {
    path: '/feed/main',
    name: 'FeedMain',
    component: FeedMain,
    meta: {
      title: 'Home'
    },
  },

  // community
  {
    path: '/community',
    name: 'CommunityMain',
    component: CommunityMain,
    meta: {
      title: 'Community'
    },
  },
  {
    path: '/community-new',
    name: 'CommunityForm',
    component: CommunityForm,
    meta: {
      title: 'New Article',
    },
  },
  {
    path: '/community/:articleId',
    name: 'CommunityArticle',
    component: CommunityArticle,
    meta: {
      title: 'Article Detail'
    },
  },

  // error
  {
    path: '/404',
    name: 'PageNotFound',
    component: PageNotFound,
    meta: {
      title: 'Page Not Found'
    },
  },
  {
    path: '/405',
    name: 'AuthorizationError',
    component: AuthorizationError,
    meta: {
      title: 'Not Authorized'
    }
  },
  {
    path: '/error',
    name: 'ErrorPage',
    component: ErrorPage,
    meta: {
      title: 'Error'
    },
  },

  //project(개발자 사이드) 관련
  {
    path : '/newproject',
    name : 'CreateProject',
    component: CreateProject,
    meta: {
      title: 'Create Project'
    },
  },
  {
    path: '/project/:id',
    name: 'ProjectDetail',
    component: ProjectDetail,
    meta: {
      title: 'Create Rewards'
    },
  },
  { 
    path: '/project-settings/:id',
    name: 'ProjectSettings',
    component: ProjectSettings,
    meta: {
      title: 'Project Settings'
    }
  },
  {
    path: '/completed',
    name: 'CreateProjectDone',
    component: CreateProjectDone,
    meta: {
      title: 'Create Project'
    },
  }, 
  {
    path:'/pjt/edit/:id',
    name: 'EditProject',
    component : EditProject,
    meta: {
      title: 'Edit Project'
    }
  },

  //game(고객 사이드) 관련
  {
    path: '/game/:id',
    name: 'GameDetail',
    component: GameDetail,
    meta: {
      title: 'Project Detail'
    },
  },
  {
    path: '/fund/:id',
    name: 'CreateFund',
    component: CreateFund,
    meta: {
      title: 'Fund Project',
    },
  },
  {
    path: '/complete/:id',
    name: 'CreateFundDone',
    component: CreateFundDone,
    meta: {
      title: 'Purchase Complete'
    }
  },
  {
    path: '/projects',
    name: 'GameMain',
    component: GameMain,
    meta: {
      title: 'Browse Projects'
    },
  },
  {
    path: '/search/:keyword',
    name: 'SearchResult',
    component: SearchResult,
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // tab 제목 바꾸기
  document.title = to.meta.title
  next()
})

export default router
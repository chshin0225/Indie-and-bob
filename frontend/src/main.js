import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueSimpleAlert from "vue-simple-alert";
import VueMoment from 'vue-moment'
import Clipboard from 'v-clipboard'
import firebase from 'firebase'

Vue.config.productionTip = false

var firebaseConfig = {
  apiKey: "AIzaSyACWrxR9heY92esve4NY-psUsxn1gNpMxI",
  authDomain: "indie-and-bob-47b93.firebaseapp.com",
  databaseURL: "https://indie-and-bob-47b93.firebaseio.com",
  projectId: "indie-and-bob-47b93",
  storageBucket: "indie-and-bob-47b93.appspot.com",
  messagingSenderId: "1094373037550",
  appId: "1:1094373037550:web:55fedc824a7627130faaff",
  measurementId: "G-4E1QQMS2GD"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

Vue.use(VueSimpleAlert);
Vue.use(VueMoment);
Vue.use(Clipboard);
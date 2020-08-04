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
  apiKey: "AIzaSyDJz4DN61FoYsQI0RcoxdeMfkn_SA0AwNI",
  authDomain: "indie-and-bob.firebaseapp.com",
  databaseURL: "https://indie-and-bob.firebaseio.com",
  projectId: "indie-and-bob",
  storageBucket: "indie-and-bob.appspot.com",
  messagingSenderId: "120944517686",
  appId: "1:120944517686:web:e5f5df23ad868b92326e97",
  measurementId: "G-1PNFCL0KCM"
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